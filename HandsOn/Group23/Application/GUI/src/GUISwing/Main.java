package GUISwing;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;


public class Main implements ActionListener {

    JFrame frame;

    JLabel labelTop;
    JLabel labelImg1;
    JLabel labelImg2;

    JPanel panelTop;
    JPanel panelBottom;
    JPanel panelMid;

    JButton biciStand;
    JButton biciMad;

    ImageIcon logo;

    BiciMad bicimad;
    BiciStand bicistand;

    Image background= Toolkit.getDefaultToolkit().createImage("Images/BiciMAD_1.jpg");
    Image escalate = background.getScaledInstance(1920, 1080, Image.SCALE_DEFAULT);

    // IMAGES
    ArrayList<File> imgList = new ArrayList<File>();;
    File folderImg = new File("C:/Users/Alvin/eclipse-workspace/GUI/src/Fotos");

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main window = new Main();
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
    public Main() {
        initialize();
//		Imglist(folderImg);
//		changeImg();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        // INITIALZIATION
        frame = new JFrame();
        panelTop = new JPanel();
        panelBottom = new JPanel();
        panelMid = new JPanel();
        labelTop = new JLabel();
        labelTop.setBounds(33, 29, 250, 36);
        labelImg1 = new JLabel();
        labelImg2 = new JLabel();
        logo = new ImageIcon(); // ADD LOGO

/////////////////////////////////////////////////////FRAME///////////////////////////////////////////////////////////////////

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setVisible(true);

///////////////////////////////////////////////////LABELS///////////////////////////////////////////////////////////////////

        // LABELTOP
        labelTop.setText("BICIWHERE-MAD");
        labelTop.setFont(new Font("Arial", Font.BOLD, 30));

        ImageIcon x = new ImageIcon(escalate);
        labelImg1.setIcon(x);
        labelImg1.setBounds(0,0, 1920,1080);

        labelImg2.setBounds(1010, 50, 600, 600);

/////////////////////////////////////////////////////PANELS///////////////////////////////////////////////////////////////////

        // PANELTOP
        panelTop.setBackground(new Color(224, 224, 224));
        panelTop.setPreferredSize(new Dimension(100, 100));
        panelTop.setLayout(null);
        panelTop.add(labelTop);

        // PANELCENTER (IMAGES)
        panelMid.setPreferredSize(new Dimension(100, 100));
        panelMid.setLayout(null);


        // PANELBOTTOM
        panelBottom.setBackground(new Color(224, 224, 224));
        panelBottom.setPreferredSize(new Dimension(100, 100));

        ///////////////////////////////////////////////// BUTTONS///////////////////////////////////////////////////////////////////

        biciMad = new JButton("BiciMad");
        biciMad.setBounds(1200, 532, 227, 77);
        biciMad.addActionListener(this);
        biciMad.setFont(new Font("Arial", Font.BOLD, 30));


        // BUTTON
        biciStand = new JButton("Bicistand");
        biciStand.setBounds(456, 532, 227, 77);
        biciStand.addActionListener(this);
        biciStand.setFont(new Font("Arial", Font.BOLD, 30));

        ///////////////////////////////////////////////// ADDS///////////////////////////////////////////////////////////////////

        panelMid.add(biciStand);
        panelMid.add(biciMad);
        panelMid.add(labelImg1);
        frame.getContentPane().add(panelTop, BorderLayout.NORTH);
        frame.getContentPane().add(panelMid, BorderLayout.CENTER);
        frame.getContentPane().add(panelBottom, BorderLayout.SOUTH);
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == biciStand) {
            frame.dispose();
            bicistand= new BiciStand();
        } else if (e.getSource() == biciMad) {
            frame.dispose();
            bicimad = new BiciMad();
        }
    }




}

