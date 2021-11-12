package GUISwing;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import SPARQL.*;

import javax.swing.*;

public class BarrioMad implements ActionListener {

    JFrame frame;
    JButton submit;
    JButton back;

    JLabel labelNeigh;
    JLabel labelTop;

    JPanel panelMain;
    JPanel panelTop;

    JComboBox comboNeigh;

    String district;

    SPARQL queries = new SPARQL();

    public BarrioMad(String district) {
        this.district=district;
        initialize();
    }

    private void initialize() {

        ArrayList listNeighs = queries.BarriosInDistricts(this.district);

///////////////////////////////////////////////Desplegables///////////////////////////////////////////////////////////////////
        comboNeigh = new JComboBox(listNeighs.toArray(new String[0]));
        comboNeigh.setBounds(732, 301, 700, 30);

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

        labelNeigh = new JLabel("Neighborhood");
        labelNeigh.setBounds(480, 301, 212, 36);
        labelNeigh.setFont(new Font("Arial", Font.BOLD, 30));


        labelTop = new JLabel("BiciMad");
        labelTop.setFont(new Font("Arial", Font.BOLD, 30));
        labelTop.setBounds(33, 29, 250, 36);

/////////////////////////////////////////////////PANELS///////////////////////////////////////////////////////////////////

        panelMain = new JPanel();
        panelMain.setLayout(null);
        panelMain.setPreferredSize(new Dimension(100, 100));

        panelMain.add(labelNeigh);
        panelMain.add(comboNeigh);
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


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            frame.dispose();
            LastMad window2 = new LastMad(this.district, comboNeigh.getSelectedItem().toString());

        } else if (e.getSource() == back) {
            frame.dispose();
            BiciMad back = new BiciMad();

        }

    }
}
