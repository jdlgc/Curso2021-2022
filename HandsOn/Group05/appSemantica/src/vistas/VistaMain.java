package vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JCheckBoxMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Panel;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;

public class VistaMain {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaMain window = new VistaMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VistaMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 568, 307);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JButton btnQuery1 = new JButton("Query1");
		btnQuery1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Query1 dialog = new Query1();
                dialog.setVisible(true);
			}
		});
		menuBar.add(btnQuery1);
		
		JButton btnQuery2 = new JButton("Query2");
		btnQuery2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Query2 dialog = new Query2();
                dialog.setVisible(true);
			}
		});
		menuBar.add(btnQuery2);
		
		JButton btnQuery3 = new JButton("Query3");
		btnQuery3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Query3 dialog = new Query3();
                dialog.setVisible(true);
			}
		});
		menuBar.add(btnQuery3);
		
		JButton btnQuery4 = new JButton("Query4");
		btnQuery4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Query4 dialog = new Query4();
                dialog.setVisible(true);
			}
		});
		menuBar.add(btnQuery4);
		
		JLabel lblDanielRodriguezRosana = new JLabel("Daniel Rodriguez, Rosana Sanchez, Javier de la Fuente, Ruben Palomo");
		lblDanielRodriguezRosana.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblCreadores = new JLabel("Creadores:");
		lblCreadores.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblBienvenida = new JLabel("Bienvenido a la aplicacion de informaciones sobre la calidad del aire");
		lblBienvenida.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblBienvenida, GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblCreadores, GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblDanielRodriguezRosana, GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblBienvenida, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 151, Short.MAX_VALUE)
					.addComponent(lblCreadores)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblDanielRodriguezRosana)
					.addGap(24))
		);
		frame.getContentPane().setLayout(groupLayout);
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
