package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Ventana1 extends JFrame {

	private JPanel contentPane;
	public static JTextField textField;

	//anadido
	//static String rutaDir = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana1 frame = new Ventana1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Ventana1() {
		
		final Ventana2 window2 = new Ventana2();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 250, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("Button.focus"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnVamos = new JButton("VAMOS");
		btnVamos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					//JOptionPane.showMessageDialog(null, "Comienza a buscar");
					window2.setVisible(true);
					dispose();	
				}
				
			}
		);
		btnVamos.setBounds(80, 180, 117, 25);
		contentPane.add(btnVamos);
		
		JLabel lblNewLabel = new JLabel("Sales a tomar algo");
		lblNewLabel.setFont(new Font("Garuda", Font.ITALIC, 21));
		lblNewLabel.setBackground(new Color(238, 238, 238));
		lblNewLabel.setBounds(96, 55, 252, 42);
		contentPane.add(lblNewLabel);
		
		JLabel lblTerrazasDeMadrid = new JLabel("Terrazas de Madrid");
		lblTerrazasDeMadrid.setFont(new Font("Garuda", Font.ITALIC, 16));
		lblTerrazasDeMadrid.setBounds(150, 115, 157, 15);
		contentPane.add(lblTerrazasDeMadrid);	
		
		JButton btnSalir = new JButton("SALIR");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnSalir.setBounds(220, 180, 117, 25);
		contentPane.add(btnSalir);
	}
}
