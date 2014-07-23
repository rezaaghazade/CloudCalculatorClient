package org.cloud.client;

import se.datadosen.component.RiverLayout;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 10/27/12
 * Time: 8:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginPage extends ServerConn{
    int x;
    public JFrame introFrame;
    public JTextField userNameTextField;
    public JPasswordField passwordTextField;
    public JButton cardNumClearButton;
    public JButton passwordClearButton;
    public JButton enterButton;
    public String userNameStr="";
    public String passWdStr="";
    public JLabel informationLable;

    public LoginPage()throws Exception
    {
        introFrame=new JFrame("ATM");
        introFrame.setBounds(450,150,180,70);
        introFrame.getContentPane().setLayout(new RiverLayout());
        introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        introFrame.setVisible(true);
    }
    public void ShowLoginPagePage()
    {
        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");


        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        userNameTextField=new JTextField(16);
        userNameTextField.setToolTipText("Card Number is 16 digits");
        passwordTextField=new JPasswordField(16);
        passwordTextField.setToolTipText("Password is 10 digits");
        cardNumClearButton=new JButton("Clear");
        cardNumClearButton.setToolTipText("Clear Card Number Field");
        passwordClearButton=new JButton("Clear");
        passwordClearButton.setToolTipText("Clear Password Field");
        enterButton=new JButton("Enter");
        enterButton.setToolTipText("Enter Account Page");
        informationLable=new JLabel();

        introFrame.getContentPane().add("p", new JLabel("Card Number :"));
        introFrame.getContentPane().add("center",userNameTextField);
        introFrame.getContentPane().add("       ",cardNumClearButton);
        introFrame.getContentPane().add("p", new JLabel("Password :     "));
        introFrame.getContentPane().add("center ",passwordTextField);
        introFrame.getContentPane().add("       ",passwordClearButton);
        introFrame.getContentPane().add("p center",enterButton);
        introFrame.getContentPane().add("br center",informationLable);
        JLabel tempLable=new JLabel();
        introFrame.getContentPane().add("p center",tempLable);
        introFrame.pack();

        cardNumClearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userNameStr = "";
                userNameTextField.setText("");
                informationLable.setText("");
            }
        });
        passwordClearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passWdStr="";
                passwordTextField.setText("");
                informationLable.setText("");
            }
        });

        userNameStr=userNameTextField.getText();
        passWdStr=String.valueOf(passwordTextField.getPassword());

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ///*//******************Enter Button
                if(checkInput(userNameStr,passWdStr))
                {

                }
            }
        });
    }
    public boolean checkInput(String cardNum,String passWd)
    {
        try{
            Double tempNum1=new Double(Double.parseDouble(cardNum));
            Double tempNum2=new Double(Double.parseDouble(passWd));
            return true;
        }
        catch (Exception e)
        {
            informationLable.setText("CardNumber & Password is Not Correct");
            return false;
        }
    }
    public static void main(String[] args) {
        try{
            LoginPage introPage=new LoginPage();
            introPage.ShowLoginPagePage();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }
}