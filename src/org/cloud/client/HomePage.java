package org.cloud.client;

import se.datadosen.component.RiverLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by reza on 7/23/14.
 */
public class HomePage extends ServerConn {

    public ArrayList personalInfo=new ArrayList();
    public JFrame introFrame;

    public JLabel informationlable;
    public JComboBox calcTypeComboBox;

    public HomePage() {

        ArrayList tmp=new ArrayList();
        tmp.add(1);
        tmp.add("reza");
        tmp.add("aghazade");

        informationlable=new JLabel();
        informationlable.setText("Welcome "+tmp.get(1)+" "+tmp.get(2)+" !");
        informationlable.setVisible(true);

        System.out.println(tmp.toString());

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        introFrame=new JFrame("HOME PAGE");
        introFrame.setBounds((width/2)-300,120,600,500);
        introFrame.getContentPane().setLayout(new RiverLayout());
        introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        introFrame.setVisible(true);
        introFrame.setResizable(false);

        introFrame.getContentPane().add("p", informationlable);

        introFrame.getContentPane().add("p",new JLabel("Calculation Type :    "));

        //getServerApplication().newInstance("")

    }

    public static void main(String[] args) {
        HomePage homePage=new HomePage();
    }

}
