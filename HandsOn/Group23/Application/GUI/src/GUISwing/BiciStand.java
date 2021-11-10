package GUISwing;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import SPARQL.*;

public class BiciStand extends JFrame implements ActionListener {

    JFrame frame;
    JButton submit;
    JButton back;

    JLabel labelDistrict;
    JLabel labelTop;

    JPanel panelMain;
    JPanel panelTop;

    JComboBox <String> comboDistrict;

    String getDistrict;

    SPARQL queries = new SPARQL();
    String[] arrayDistrict = queries.Districts().toArray(new String[0]);

    private void initialize(){

        /////////////////////////////////////////////////Desplegables///////////////////////////////////////////////////////////////////
        comboDistrict = new JComboBox <String> (arrayDistrict);
        comboDistrict.setBounds(732, 226, 700, 30);
        comboDistrict.addActionListener(this);

        getDistrict = comboDistrict.getSelectedItem().toString();

        /////////////////////////////////////////////////BUTTONS///////////////////////////////////////////////////////////////////
        submit = new JButton("Submit");
        submit.setFocusable(false);
        submit.setBounds(821, 394, 104, 36);
        submit.addActionListener(this);

        back = new JButton("Back");
        back.setFocusable(false);
        back.setBounds(1049, 394, 104, 36);
        back.addActionListener(this);

        /////////////////////////////////////////////////FRAME///////////////////////////////////////////////////////////////////
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setVisible(true);

        ///////////////////////////////////////////////LABELS///////////////////////////////////////////////////////////////////
        labelDistrict = new JLabel("District");
        labelDistrict.setBounds(546, 226, 104, 36);
        labelDistrict.setFont(new Font("Arial", Font.BOLD, 30));

        labelTop = new JLabel("BiciStand");
        labelTop.setFont(new Font("Arial", Font.BOLD, 30));
        labelTop.setBounds(33, 29, 250, 36);

        /////////////////////////////////////////////////PANELS///////////////////////////////////////////////////////////////////
        panelMain = new JPanel();
        panelMain.setLayout(null);
        panelMain.setPreferredSize(new Dimension(100, 100));

        panelMain.add(labelDistrict);
        panelMain.add(comboDistrict);
        panelMain.add(submit);
        panelMain.add(back);

        panelTop = new JPanel();
        panelTop.setBackground(new Color(224, 224, 224));
        panelTop.setPreferredSize(new Dimension(100, 100));
        panelTop.setLayout(null);

        panelTop.add(labelTop);

        frame.getContentPane().add(panelMain);
        frame.getContentPane().add(panelTop, BorderLayout.NORTH);
    }
    public BiciStand() {
        initialize();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == submit) {
            frame.dispose();
            getDistrict = comboDistrict.getSelectedItem().toString();
            frame.dispose();
            BarrioStand barrioMad = new BarrioStand(getDistrict);
        }
        else if (e.getSource() == back) {
            frame.dispose();
            Main main = new Main();

        }

    }
}

