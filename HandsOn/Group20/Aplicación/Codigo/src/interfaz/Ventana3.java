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

import static main.java.ejejena.metodo;

public class Ventana3 extends JFrame {

	//anadido
	public static String val="";
	
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
					Ventana3 frame = new Ventana3();
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
	public Ventana3() {
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
		
		JLabel lblNewLabel_1 = new JLabel("Busca la terraza de Madrid que mas se adapte a tus necesidades. Filtra");
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Garuda", Font.ITALIC, 16));
		lblNewLabel_1.setBackground(UIManager.getColor("Button.highlight"));
		lblNewLabel_1.setForeground(UIManager.getColor("Button.foreground"));
		
		JLabel lblPorAforosCercana = new JLabel(" por aforos, Cercania y amplitud de las terrazas.");
		panel.add(lblPorAforosCercana);
		lblPorAforosCercana.setFont(new Font("Garuda", Font.ITALIC, 16));
		
		JButton btnE = new JButton("E");
		btnE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				val = "E";
			}
		});
		btnE.setBounds(58, 244, 117, 25);
		contentPane.add(btnE);
		
		JButton btnF = new JButton("F");
		btnF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				val = "F";
			}
		});
		btnF.setBounds(58, 284, 117, 25);
		contentPane.add(btnF);
		
		JButton btnG = new JButton("G");
		btnG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				val = "G";
			}
		});
		btnG.setBounds(58, 324, 117, 25);
		contentPane.add(btnG);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(187, 244, 425, 25);
		contentPane.add(panel_1);
		
		JLabel lblGruposGrandesTerrazas = new JLabel("Muestra los links de 4 barrios en wikidata");
		panel_1.add(lblGruposGrandesTerrazas);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(187, 284, 425, 25);
		contentPane.add(panel_1_1);
		
		JLabel lblGrandesCiudades = new JLabel("Terrazas con menos de 10 sillas para grupos menores");
		panel_1_1.add(lblGrandesCiudades);
		
		JPanel panel_1_2 = new JPanel();
		panel_1_2.setBounds(187, 324, 425, 25);
		contentPane.add(panel_1_2);
		
		JLabel lblBarrioDeSalamanca = new JLabel("Muestra los links de tipos de via en wikidata");
		panel_1_2.add(lblBarrioDeSalamanca);
		
		JPanel panel_Gracias = new JPanel();
		panel_Gracias.setBounds(76, 414, 533, 54);
		contentPane.add(panel_Gracias);
		
		JLabel lblgracias = new JLabel("Gracias ");
		lblgracias.setFont(new Font("Garuda", Font.ITALIC, 24));
		panel_Gracias.add(lblgracias);
		
		
		JButton btnAnterior = new JButton("PAGINA ANTERIOR");
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ventana2 window2 = new Ventana2();
				window2.setVisible(true);
				dispose();
			}
		});
		btnAnterior.setBounds(453, 360, 160, 25);
		contentPane.add(btnAnterior);
		
		//final ejejena trabajo = new ejejena();
		
		JButton btnGo = new JButton("BUSCAR");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<QuerySolution> lista3 = new ArrayList<QuerySolution>();
				lista3 = ejejena.metodo();
				System.out.print(lista3);
			}
		});
		btnGo.setBounds(213, 360, 117, 25);
		contentPane.add(btnGo);

		JButton btnSalir = new JButton("SALIR");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnSalir.setBounds(470, 475, 117, 25);
		contentPane.add(btnSalir);
		
		
		
		
		
	}

}


