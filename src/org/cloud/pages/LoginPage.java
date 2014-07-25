package org.cloud.pages;

import org.cloud.encryption.MD5;
import org.cloud.connectToServer.ServerConn;
import se.datadosen.component.RiverLayout;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 10/27/12
 * Time: 8:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginPage extends ServerConn {
    public JFrame introFrame;
    public JTextField userNameTextField;
    public JPasswordField passwordTextField;
    public JButton cardNumClearButton;
    public JButton passwordClearButton;
    public JButton enterButton;
    public String userNameStr="";
    public String passWdStr="";
    public JLabel informationLable;

    public ArrayList personalArray;

    public LoginPage()throws Exception
    {
        /*Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();*/

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

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                userNameStr=userNameTextField.getText();
                passWdStr=String.valueOf(passwordTextField.getPassword());

                try {
                    getServerApplication().newInstance("org.cloud.database.Authenticity");
                }catch (Exception ex)
                {
                    String message = "Connection is Closed";
                    JOptionPane.showMessageDialog(new JFrame(), message, "Error",JOptionPane.ERROR_MESSAGE);
                }

                if(checkInput(userNameStr,passWdStr))
                {
                    MD5 md5Obj=new MD5();
                    passWdStr=md5Obj.md5(passWdStr);
                    try {
                        personalArray= (ArrayList) getServerApplication().invokeMethod("Authenticity",
                                                new Object[]{new String(userNameStr), new String(passWdStr)});

                        if (personalArray.size()==0)
                        {
                            informationLable.setText("CardNumber Or Password is InCorrect");
                        }else
                        {
                            introFrame.dispose();
                            HomePage homePage=new HomePage();
                        }

                    }catch (Exception ex)
                    {
                        String message = "Connection is Closed890";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
    public boolean checkInput(String cardNum,String passWd)
    {
        try{
            Integer tempNum1=new Integer(Integer.parseInt(cardNum));
            Integer tempNum2=new Integer(Integer.parseInt(passWd));
            return true;
        }
        catch (Exception e)
        {
            informationLable.setText("Type UserName Or Password In True Style");
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