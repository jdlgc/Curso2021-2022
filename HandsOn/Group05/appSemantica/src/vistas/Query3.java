package vistas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import controladores.Querys;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import java.awt.Font;

public class Query3 extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel infoLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Query1 dialog = new Query1();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Query3() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			infoLabel = new JLabel("Te muestra todas las mediciones de una estacion.");
			infoLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
			infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		JLabel lblNewLabel = new JLabel("Selecciona una estacion: ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("1");
		comboBox.addItem("2");
		comboBox.addItem("3");
		comboBox.addItem("4");
		comboBox.addItem("5");
		comboBox.addItem("7");
		comboBox.addItem("14");
		JScrollPane scrollPane = new JScrollPane();
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(infoLabel, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(74, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(infoLabel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(7)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
		);
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton ejecutarButton = new JButton("Ejecutar query");
				ejecutarButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Querys query3 = new Querys();
						ArrayList<String> estaciones = query3.getEstacion_TienePuntoMuestreo_TieneMagnitud(comboBox.getSelectedItem().toString());
						
						DefaultListModel<String> modelo = new DefaultListModel<String>();
						for (int i=0;i<estaciones.size();i++) {
						      
							modelo.addElement(estaciones.get(i));
						}
						
						list.setModel(modelo);
					}
				});
				buttonPane.add(ejecutarButton);
				getRootPane().setDefaultButton(ejecutarButton);
			}
			{
				JButton verButton = new JButton("Ver query");
				verButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JFrame jFrame = new JFrame();
				        JOptionPane.showMessageDialog(jFrame, "Esta query se hace cogiendo el output.nt \n"
				        		+ "SELECT DISTINCT ?puntoMuestreo ?magnitud\r\n"
								+ "WHERE{\r\n"
								+ "    <http://www.calidadAire.com/refEstacion/+estacion+> <http://www.calidadAire.com#tienePuntoMuestreo> ?puntoMuestreo.\r\n"
								+ "    <http://www.calidadAire.com/refEstacion/+estacion+> <http://www.calidadAire.com#mide> ?magnitud.\r\n"
								+ " }");
					}
				});
				buttonPane.add(verButton);
			}
		}
	}
}
