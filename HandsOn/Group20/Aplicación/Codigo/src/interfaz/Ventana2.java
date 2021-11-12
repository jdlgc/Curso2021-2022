package interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.FileManager;

import main.java.ejejena;

//import paquete.Main;

//import paquete.Main;

import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import main.java.*;

public class Ventana2 extends JFrame {

	//anadido
	public static String val ="";
	
	private JPanel contentPane;
	private ButtonGroup botones=new ButtonGroup();
	private JTextField num;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana2 frame = new Ventana2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		/*while(true) {
		if(val!=null) {
			//System.out.println("Dentro");
			//sparqlTest();
			System.out.println("EStamos dentro");
		}
		}*/
	}

	/**
	 * Create the frame.
	 */
	public Ventana2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 200, 684, 561);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("Button.focus"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Terrazas de Madrid");
		lblNewLabel.setFont(new Font("Garuda", Font.ITALIC, 28));
		lblNewLabel.setBounds(225, 50, 289, 77);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(58, 141, 551, 77);
		contentPane.add(panel);
		
		JLabel lblNewLabel_1 = new JLabel("Busca la terraza de Madrid que más se adapte a tus necesidades. Filtra");
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Garuda", Font.ITALIC, 16));
		lblNewLabel_1.setBackground(UIManager.getColor("Button.highlight"));
		lblNewLabel_1.setForeground(UIManager.getColor("Button.foreground"));
		
		JLabel lblPorAforosCercana = new JLabel(" por aforos, cercania y amplitud de las terrazas.");
		panel.add(lblPorAforosCercana);
		lblPorAforosCercana.setFont(new Font("Garuda", Font.ITALIC, 16));
		
		JButton btnA = new JButton("A");
		btnA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				val = "A";
			}
		});
		btnA.setBounds(58, 244, 117, 25);
		contentPane.add(btnA);
		
		JButton btnB = new JButton("B");
		btnB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				val = "B";
			}
		});
		btnB.setBounds(58, 284, 117, 25);
		contentPane.add(btnB);
		
		JButton btnC = new JButton("C");
		btnC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				val = "C";
			}
		});
		btnC.setBounds(58, 324, 117, 25);
		contentPane.add(btnC);
		
		JButton btnD = new JButton("D");
		btnD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				val = "D";
			}
		});
		btnD.setBounds(58, 364, 117, 25);
		contentPane.add(btnD);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(187, 244, 425, 25);
		contentPane.add(panel_1);
		
		JLabel lblGruposGrandesTerrazas = new JLabel("Numero de terrazas con mas de 20 sillas para grupos grandes");
		panel_1.add(lblGruposGrandesTerrazas);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(187, 284, 425, 25);
		contentPane.add(panel_1_1);
		
		JLabel lblGrandesCiudades = new JLabel(" Cinco locales que se encuentran en una avenida");
		panel_1_1.add(lblGrandesCiudades);
		
		JPanel panel_1_2 = new JPanel();
		panel_1_2.setBounds(187, 324, 425, 25);
		contentPane.add(panel_1_2);
		
		JLabel lblBarrioDeSalamanca = new JLabel("Bares que se encuentran en el barrio de Salamanca");
		panel_1_2.add(lblBarrioDeSalamanca);
		
		JPanel panel_1_3 = new JPanel();
		panel_1_3.setBounds(187, 364, 425, 25);
		contentPane.add(panel_1_3);
		
		JLabel lblIndecisosCuatroBares = new JLabel("Sugiere 4 bares aleatorios e indica su calle");
		panel_1_3.add(lblIndecisosCuatroBares);
		
		final Ventana3 window3 = new Ventana3();
		
		JButton btnPaginaSiguiente = new JButton("SIGUIENTE PAGINA");
		btnPaginaSiguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window3.setVisible(true);
				val = "";
				dispose();
			}
		});
		btnPaginaSiguiente.setBounds(453, 400, 160, 25);
		contentPane.add(btnPaginaSiguiente);
		
		//final ejejena trabajo = new ejejena();
		
		JButton btnGo = new JButton("BUSCAR");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<QuerySolution> lista2 = new ArrayList<QuerySolution>();
				lista2 = ejejena.metodo();
				System.out.print(lista2);
			}
		});
		btnGo.setBounds(213, 400, 117, 25);
		contentPane.add(btnGo);



		
	}

}


