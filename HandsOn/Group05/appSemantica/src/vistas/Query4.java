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

public class Query4 extends JDialog {

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
	public Query4() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			infoLabel = new JLabel("Te muestra las URI de los municipios.");
			infoLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
			infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		JLabel lblNewLabel = new JLabel("Selecciona un municipio: ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("Orusco de Tajuña");
		comboBox.addItem("Puerto de Cotos");
		comboBox.addItem("Rivas-Vaciamadrid");
		comboBox.addItem("Aranjuez");
		comboBox.addItem("San Martin de Valdeiglesias");
		comboBox.addItem("Arganda del Rey");
		comboBox.addItem("Torrejon de Ardoz");
		comboBox.addItem("El Atazar");
		comboBox.addItem("Villa del Prado");
		comboBox.addItem("Villarejo de Salvanes");
		comboBox.addItem("Colmenar Viejo");
		comboBox.addItem("Collado Villalba");
		comboBox.addItem("Coslada");
		comboBox.addItem("Alcala de Henares");
		comboBox.addItem("Fuenlabrada");
		comboBox.addItem("Alcobendas");
		comboBox.addItem("Getafe");
		comboBox.addItem("Guadalix de la Sierra");
		comboBox.addItem("Alcorcon");
		comboBox.addItem("Leganes");
		comboBox.addItem("Majadahonda");
		comboBox.addItem("Algete");
		comboBox.addItem("Mostoles");
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(infoLabel, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
				.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
					.addGap(34))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(infoLabel, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
						Querys query4 = new Querys();
						ArrayList<String> estaciones = query4.getMunicipio_TieneUri(comboBox.getSelectedItem().toString());
						
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
				        		+ "SELECT DISTINCT ?uri\r\n"
								+ "WHERE{\r\n"
								+ "    <http://www.calidadAire.com/refMunicipio/+municipio+> <http://www.calidadAire.com/urim#tieneURIMuni> ?uri.\r\n"
								+ " }");
					}
				});
				buttonPane.add(verButton);
			}
		}
	}
}
