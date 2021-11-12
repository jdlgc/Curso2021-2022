import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;

import com.opencsv.exceptions.CsvValidationException;

public class MySwing2 extends JFrame implements ActionListener {
	MySwing ms;
	int DEFAULT_WIDTH = 600;
	int DEFAULT_HEIGHT = 400;
	Boolean km;
	int ope;
	String result;

	public MySwing2(final MySwing ms) throws CsvValidationException, IOException {
		System.out.println("abriendo archivo " + ms.getArchivo());
		setTitle("Resultado");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setAlwaysOnTop(true);
		getContentPane().setLayout(null);
		JButton button = new JButton("Volver");
		button.setBounds(234, 299, 77, 29);
		button.addActionListener(this);

		JTextPane txtpnSsss = new JTextPane();

		km = ms.getOperacion().equals("Basado en Superficie") ? true : false;
		switch (ms.getArchivo()) {
		case "Bicicleta":
			ope = 1;
			break;
		case "Aparcamiento de bicicleta":
			ope = 2;
			break;
		default:
			ope = 3;
		}
		result = App.test(km, ope, ms.getDistrito());
//		result="eeee";
		txtpnSsss.setText(result);
		txtpnSsss.setBounds(145, 100, 257, 250);
		getContentPane().add(txtpnSsss);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}