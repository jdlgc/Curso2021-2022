package GUISwing;

import SPARQL.SPARQL;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class LastStand extends JFrame implements ActionListener {

    JFrame frame;

    JButton menu;
    JButton back;

    JLabel labelImg;
    JLabel labelTop;
    JLabel labelSumm;
    JLabel labelSummDis;
    JLabel labelSummNeigh;

    JPanel panelLeft;
    JPanel panelTop;
    JPanel panelRight;

    JList listStreet;
    JScrollPane scStreet;

    SPARQL queries = new SPARQL();
    String[] streets;
    String district;
    String neigh;

    public LastStand(String district, String neigh) {
        this.district = district;
        this.neigh = neigh;
        initialize();

    }

    private void initialize() {

        ///////////////////////////////////////////////// IMAGES///////////////////////////////////////////////////////////////////
        streets = queries.CallesBarriosBicimadStation(neigh).toArray((new String[0]));
        listStreet = new JList(streets);
        listStreet.setFont(new Font("Arial", Font.PLAIN, 12));
        listStreet.setBorder(null);


        scStreet = new JScrollPane();
        scStreet.setBounds(960, 60, 930, 350);
        scStreet.setBorder(null);
        scStreet.setBackground(null);
        scStreet.setViewportView(listStreet);

        ///////////////////////////////////////////////// BUTTONS///////////////////////////////////////////////////////////////////

        menu = new JButton("Menu");
        menu.setFocusable(false);
        menu.setBounds(1302, 833, 104, 36);
        menu.addActionListener(this);

        back = new JButton("Back");
        back.setFocusable(false);
        back.setBounds(444, 833, 104, 36);
        back.addActionListener(this);

        ///////////////////////////////////////////////// FRAME///////////////////////////////////////////////////////////////////
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setVisible(true);

        /////////////////////////////////////////////// LABELS///////////////////////////////////////////////////////////////////

        labelImg = new JLabel(new ImageIcon(getImage(neigh)));
        labelImg.setVerticalAlignment(SwingConstants.BOTTOM);
        labelImg.setBounds(50, 60, 900, 700);

        labelSumm = new JLabel("Summary");
        labelSumm.setFont(new Font("Arial", Font.BOLD, 30));
        labelSumm.setBounds(983, 431, 250, 36);

        labelSummDis = new JLabel("District:" + district);
        labelSummDis.setFont(new Font("Arial", Font.BOLD, 30));
        labelSummDis.setBounds(983, 492, 488, 36);

        labelSummNeigh = new JLabel("Neighborhood:" + neigh);
        labelSummNeigh.setFont(new Font("Arial", Font.BOLD, 30));
        labelSummNeigh.setBounds(983, 571, 423, 36);

        // LABELTOP
        labelTop = new JLabel("BiciMad");
        labelTop.setFont(new Font("Arial", Font.BOLD, 30));
        labelTop.setBounds(33, 29, 250, 36);

        ///////////////////////////////////////////////// PANELS///////////////////////////////////////////////////////////////////

        panelLeft = new JPanel();
        panelLeft.setBounds(0, 100, 960, 980);
        panelLeft.setLayout(null);
        panelLeft.add(labelImg);
        panelLeft.add(back);

        panelRight = new JPanel();
        panelRight.setBounds(960, 100, 960, 980);
        panelRight.setLayout(null);
        panelRight.add(menu);
        panelRight.add(scStreet);
        panelRight.add(labelSumm);
        panelRight.add(labelSummDis);
        panelRight.add(labelSummNeigh);

        panelTop = new JPanel();
        panelTop.setBackground(new Color(224, 224, 224));
        panelTop.setPreferredSize(new Dimension(100, 100));
        panelTop.setLayout(null);
        panelTop.add(labelTop);

        panelLeft.add(labelImg);

        frame.getContentPane().add(panelLeft);
        frame.getContentPane().add(panelRight);

        frame.getContentPane().add(panelTop, BorderLayout.NORTH);
    }

    public Image getImage(String neigh) {
        Image image = null;
        Image escalated = null;
        String url2 = null;
        try {
            url2 = saveImage(neigh);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            URL url = new URL(url2);
            image = ImageIO.read(url);
            escalated = image.getScaledInstance(800, 700, Image.SCALE_DEFAULT);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    public String saveImage(String neigh) throws IOException {
        URL url = new URL(queries.Foto(queries.SameAs(neigh)));
        InputStreamReader isr = new InputStreamReader(url.openStream());
        BufferedReader buffer = null;
        buffer = new BufferedReader(isr);
        StringBuilder builder = new StringBuilder();
        int byteRead;
        while ((byteRead = buffer.read()) != -1) {
            builder.append((char) byteRead);
        }
        buffer.close();
        isr.close();

        String contenido = builder.toString();
        String partes[] = contenido.split("<meta property=\"og:image\" content=\"");
        String urlbueno[] = partes[1].split("\"/>");
        return urlbueno[0];
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            frame.dispose();
            BarrioStand barrioMad = new BarrioStand(district);
        } else if (e.getSource() == menu) {
            frame.dispose();
            Main main = new Main();

        }
    }

}
