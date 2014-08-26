package org.cloud.pages;

import javax.swing.*;
import java.awt.*;

/**
 * Created by reza on 7/27/14.
 */
public class Picture extends Canvas{

    public JPanel jPanel=new JPanel();
    public Picture()
    {
        jPanel.setSize(500,500);
        jPanel.setVisible(true);
    }

    public void paint(Graphics g) {

        Toolkit t=Toolkit.getDefaultToolkit();
        Image i=t.getImage("cloud-calculator.jpg");
        g.drawImage(i,0,150,this);
    }

    public JPanel getjPanel() {
        return jPanel;
    }

    public void setjPanel(JPanel jPanel) {
        this.jPanel = jPanel;
    }

}
