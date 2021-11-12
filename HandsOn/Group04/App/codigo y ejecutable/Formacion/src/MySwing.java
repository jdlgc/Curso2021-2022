import java.awt.Dialog.ModalExclusionType;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;

import com.opencsv.exceptions.CsvValidationException;

import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JSeparator;
import javax.swing.JPanel;

public class MySwing extends JFrame implements ActionListener {
	JButton jb = new JButton();
	int DEFAULT_WIDTH = 800;
	int DEFAULT_HEIGHT = 600;

	public JComboBox<String> getFaceCombo() {
		return faceCombo;
	}

	public void setFaceCombo(JComboBox<String> faceCombo) {
		this.faceCombo = faceCombo;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public String getDistrito() {
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	private JComboBox<String> faceCombo;
	private JComboBox<String> distric_pc;
	private JComboBox<String> operacionSelec;
	private JComboBox<String> papelera_calle;
	private JComboBox<String> distric_bm;
	private JComboBox<String> distric_ap;
	private String archivo;
	private String distrito;
	private String operacion;
	private String calle;

	private JTextField txtWebSemantica;
	private JTextPane txtpnDistritoOCalle;
	private JTextPane txtpnSeleccionaLaOperacion;

	public MySwing() {
		getContentPane().setBackground(new Color(255, 255, 255));
		setTitle("WEB SEMANTICA");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		getContentPane().setLayout(null);
		jb.setBounds(328, 483, 82, 45);
		jb.setText("OK");
		getContentPane().add(jb);
		ItemListener archivoListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				if (ItemEvent.SELECTED == arg0.getStateChange()) {
					String selectedItem = arg0.getItem().toString();
					archivo = selectedItem;
					if (archivo.equals("Papelera solar")) {
						papelera_calle.setVisible(true);
						distric_pc.setVisible(false);
						distric_bm.setVisible(false);
						distric_ap.setVisible(false);
						

					} else if (archivo.equals("Punto de cargar para coche electrico")) {
						distric_pc.setVisible(true);
						distric_bm.setVisible(false);
						distric_ap.setVisible(false);
						papelera_calle.setVisible(false);
						
					} else if (archivo.equals("Aparcamiento de bicicleta")) {
						distric_pc.setVisible(false);
						distric_bm.setVisible(false);
						distric_ap.setVisible(true);
						papelera_calle.setVisible(false);
					

					} else if (archivo.equals("Bicicleta")) {
						distric_pc.setVisible(false);
						distric_bm.setVisible(true);
						distric_ap.setVisible(false);
						papelera_calle.setVisible(false);
						

					} else {
						distric_pc.setVisible(false);
						distric_bm.setVisible(false);
						distric_ap.setVisible(false);
						papelera_calle.setVisible(false);
						
					}

					System.out.println("Archivo seleccionado: " + archivo);

				}

			}
		};

		ItemListener distritoListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				if (ItemEvent.SELECTED == arg0.getStateChange()) {
					String selectedItem = arg0.getItem().toString();
					String papel = "Papelera solar";
					if (!papel.equals(archivo)) {
						distrito = selectedItem;
						calle = null;
					} else {
						calle = selectedItem;
						distrito = null;
					}
					
					System.out.println("Distrito seleccionado: " + distrito);

				}

			}
		};

		ItemListener calleListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				if (ItemEvent.SELECTED == arg0.getStateChange()) {
					String selectedItem = arg0.getItem().toString();
					calle = selectedItem;
					System.out.println("calle seleccionado: " + operacion);

				}

			}
		};

		ItemListener operacionListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				if (ItemEvent.SELECTED == arg0.getStateChange()) {
					String selectedItem = arg0.getItem().toString();
					operacion = selectedItem;
					System.out.println("Operacion seleccionado: " + operacion);
					
				}

			}
		};

		distric_pc = new JComboBox<String>();
		distric_pc.setBounds(316, 294, 443, 42);
		distric_pc.setEditable(true);
		distric_pc.addItemListener(distritoListener);
		distric_pc.setEnabled(true);
		String dis_pc[] = { "-", "Arganzuela","Barajas","Carabanchel","Centro","Chamartín","Chamberí","Ciudad_Lineal","Fuencarral","Hortaleza","Latina","Moncloa","Moratalaz","Puente_de_Vallecas","Retiro","Salamanca","San_Blas","Tetuán","Usera","Vicálvaro","Villa_de_Vallecas","Villaverde" };
		for (String s : dis_pc) {
			distric_pc.addItem(s);
		}
		getContentPane().add(distric_pc);
		distric_pc.setVisible(false);
		
		distric_bm = new JComboBox<String>();
		distric_bm.setBounds(316, 294, 443, 42);
		distric_bm.setEditable(true);
		distric_bm.addItemListener(distritoListener);
		distric_bm.setEnabled(true);
		String dis_bm[] = { "-", "Arganzuela","Carabanchel", "Centro", "Chamartín", "Chamberí", "Latina",
				"Moncloa-Aravaca", "Moratalaz", "Retiro", "Salamanca", "Tetuán", "Usera",
				"Villa_de_Vallecas" };
		for (String s : dis_bm) {
			distric_bm.addItem(s);
		}
		getContentPane().add(distric_bm);
		distric_bm.setVisible(false);

		distric_ap = new JComboBox<String>();
		distric_ap.setBounds(316, 294, 443, 42);
		distric_ap.setEditable(true);
		distric_ap.addItemListener(distritoListener);
		distric_ap.setEnabled(true);
		String dis_ap[] = { "-", "Arganzuela","Barajas","Carabanchel","Centro","Chamartín","Chamberí",
		"Ciudad-Lineal","Fuencarral-El-Pardo","Hortaleza","Latina","Moncloa-Aravaca","Moratalaz","Puente-de-Vallecas","Retiro","Salamanca",
		"San-Blas","Tetuán","Usera","Vicálvaro","Villa-de-Vallecas","Villaverde" };
		for (String s : dis_ap) {
			distric_ap.addItem(s);
		}
		getContentPane().add(distric_ap);
		distric_ap.setVisible(false);

		operacionSelec = new JComboBox<String>();
		operacionSelec.setBounds(316, 384, 443, 42);
		operacionSelec.setEditable(true);
		operacionSelec.setEnabled(true);
		operacionSelec.addItemListener(operacionListener);
		operacionSelec.addItem("-");
		operacionSelec.addItem("Basado en Poblacion");
		operacionSelec.addItem("Basado en Superficie");
		getContentPane().add(operacionSelec);

		papelera_calle = new JComboBox<String>();
		papelera_calle.setEnabled(true);
		papelera_calle.setEditable(true);
		papelera_calle.setBounds(316, 294, 443, 42);
		operacionSelec.addItemListener(calleListener);
		String no[]={"San-Cipriano","Avenida-de-Moratalaz","Gran-Vía","Costa-Brava",
				"Paseo-del-Prado","Paseo-de-Recoletos","Avenida-del-Cardenal-Herrera-Oria",
				"Calle-de-Juan-Bravo-Madrid","Calle-Camino-de-los-Vinateros",
				"Paseo-de-la-Reina-Cristina-Madrid","Avenida-del-Marqués-de-Corbera-Madrid",
				"Calle-de-José-del-Hierro-Madrid","Avenida-de-Monforte-de-Lemos-Madrid","Mayor-street",
				"Avenida-de-Europa-El-Puerto-de-Santa-María","Calle-del-Doctor-Esquerdo-Madrid","Paseo-de-Extremadura"};
		for (String s : no) {
			papelera_calle.addItem(s);
		}
		
		getContentPane().add(papelera_calle);
		papelera_calle.setVisible(false);

		faceCombo = new JComboBox<String>();
		faceCombo.setBackground(new Color(255, 255, 255));
		faceCombo.setBounds(316, 207, 443, 42);
		faceCombo.setEditable(true);
		// faceCombo.addActionListener(actionListener);
		// faceCombo.addPopupMenuListener(popupMenuListener);
		faceCombo.addItemListener(archivoListener);
		faceCombo.setEnabled(true);
		faceCombo.setEditable(true);
		faceCombo.addItem("-");
		faceCombo.addItem("Bicicleta");
		faceCombo.addItem("Aparcamiento de bicicleta");
		faceCombo.addItem("Punto de cargar para coche electrico");
		faceCombo.addItem("Papelera solar");
		getContentPane().add(faceCombo);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0));
		panel.setBounds(0, 60, 786, 64);
		getContentPane().add(panel);
		panel.setLayout(null);
		
				txtWebSemantica = new JTextField();
				txtWebSemantica.setHorizontalAlignment(SwingConstants.CENTER);
				txtWebSemantica.setBounds(262, 0, 225, 64);
				panel.add(txtWebSemantica);
				txtWebSemantica.setBackground(new Color(255, 255, 255));
				txtWebSemantica.setForeground(new Color(0, 0, 0));
				txtWebSemantica.setEditable(false);
				txtWebSemantica.setFont(new Font("Teko SemiBold", Font.PLAIN, 16));
				txtWebSemantica.setText("Web Semantica");
				txtWebSemantica.setColumns(10);

		JTextPane txtpnSeleccineFuenteDe = new JTextPane();
		txtpnSeleccineFuenteDe.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtpnSeleccineFuenteDe.setBackground(new Color(255, 255, 255));
		txtpnSeleccineFuenteDe.setText("\u00BFQue elemento queres consultar?");
		txtpnSeleccineFuenteDe.setBounds(43, 207, 242, 26);
		getContentPane().add(txtpnSeleccineFuenteDe);

		txtpnDistritoOCalle = new JTextPane();
		txtpnDistritoOCalle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtpnDistritoOCalle.setBackground(new Color(255, 255, 255));
		txtpnDistritoOCalle.setText("Distrito o Calle que situa:");
		txtpnDistritoOCalle.setBounds(43, 294, 185, 26);
		getContentPane().add(txtpnDistritoOCalle);

		txtpnSeleccionaLaOperacion = new JTextPane();
		txtpnSeleccionaLaOperacion.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtpnSeleccionaLaOperacion.setBackground(new Color(255, 255, 255));
		txtpnSeleccionaLaOperacion.setText("Selecciona la operacion:");
		txtpnSeleccionaLaOperacion.setBounds(43, 384, 208, 26);
		getContentPane().add(txtpnSeleccionaLaOperacion);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 60, 766, 2);
		getContentPane().add(separator_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 122, 766, 2);
		getContentPane().add(separator);

		this.setVisible(true);
		jb.addActionListener(this);
	

	}

	public void actionPerformed(ActionEvent e) {
		String aux="-";
		if (!aux.equals(archivo) && !aux.equals(operacion)  && !aux.equals(calle)  && !aux.equals(distrito) ) {
			MySwing2 frame;
			try {
				frame = new MySwing2(this);
				frame.setVisible(true);
			} catch (CsvValidationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public static void main(String args[]) {
		MySwing s = new MySwing();
	}
}