package vistas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controladores.Querys;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import java.awt.Font;

public class Query1 extends JDialog {

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
	public Query1() {
		setBounds(100, 100, 586, 286);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			infoLabel = new JLabel("Te muestra todos los municipios de los que se han tomado mediciones.");
			infoLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
			infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(infoLabel, GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(10)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(infoLabel, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JList<String> list = new JList<String>();
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
						Querys query1 = new Querys();
						ArrayList<String> municipios = query1.getTieneMunicipio();
						
						DefaultListModel<String> modelo = new DefaultListModel<String>();
						for (int i=0;i<municipios.size();i++) {
						      
							modelo.addElement(municipios.get(i));
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
				        		+ "SELECT DISTINCT ?municipio\r\n"
								+ "WHERE{\r\n"
								+ "    ?provincia <http://www.calidadAire.com/refProvincia/provincia#tieneMunicipio> ?municipio .\r\n"
								+ " }");
					}
				});
				buttonPane.add(verButton);
			}
		}
	}
}
