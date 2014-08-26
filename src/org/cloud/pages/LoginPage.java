package org.cloud.pages;

import org.cloud.connectToServer.ConnectToServer;
import org.cloud.encryption.MD5;
import org.cloud.pages.adminUser.AdminUserHomePage;
import org.cloud.pages.usualUser.UsualUserHomePage;
import org.cloud.writeHistory.WriteHistory;
import se.datadosen.component.RiverLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 10/27/12
 * Time: 8:02 PM
 * To change this template use File | Settings | File Templates.
 */

public class LoginPage extends ConnectToServer {

    public JFrame loginFrame;
    public JTextField userNameTextField;
    public JPasswordField passwordTextField;
    public JButton cardNumClearButton;
    public JButton passwordClearButton;
    public JButton enterButton;
    public String userNameStr = "";
    public String passWdStr = "";
    public JLabel informationLable;
    public ArrayList personalArray;
    public JButton exitButton;

    private Pattern pattern;
    private Matcher matcher;

    private static final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";
    private static final String PASSWORD_PATTERN = "^[a-z0-9_-]{4,18}$";

    public LoginPage()
    {

        System.gc();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        loginFrame = new JFrame("Cloud Calculator");
        loginFrame.setBounds(width/2-250, 110, 500, 550);
        loginFrame.getContentPane().setLayout(new RiverLayout());
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);

        JLabel pic = new JLabel();
        //add(pic);
        ImageIcon test = new ImageIcon("cloud-calculator.jpg");
        pic.setIcon(test);
        //pic
        loginFrame.getContentPane().add("",pic);

    }
    public void ShowLoginPagePage()
    {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        userNameTextField = new JTextField(16);
        userNameTextField.setFocusable(true);
        userNameTextField.setToolTipText("User Name is 8 digits");
        passwordTextField = new JPasswordField(16);
        passwordTextField.setToolTipText("Password is 10 digits");
        cardNumClearButton = new JButton("Clear");
        cardNumClearButton.setToolTipText("Clear Card Number Field");
        passwordClearButton = new JButton("Clear");
        passwordClearButton.setToolTipText("Clear Password Field");
        enterButton = new JButton("Enter");
        enterButton.setToolTipText("Enter Account Page");
        informationLable = new JLabel();
        exitButton=new JButton("Exit");

        loginFrame.getContentPane().add("p", new JLabel("User Name :"));
        loginFrame.getContentPane().add("center", userNameTextField);
        loginFrame.getContentPane().add("       ", cardNumClearButton);
        loginFrame.getContentPane().add("p", new JLabel("Password   :"));
        loginFrame.getContentPane().add("center ", passwordTextField);
        loginFrame.getContentPane().add("       ", passwordClearButton);
        loginFrame.getContentPane().add("p center  ", enterButton);
        loginFrame.getContentPane().add("br center", informationLable);
        JLabel tempLable = new JLabel();
        loginFrame.getContentPane().add("p center", tempLable);
        loginFrame.getContentPane().add("center",exitButton);
        loginFrame.pack();

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
                passWdStr = "";
                passwordTextField.setText("");
                informationLable.setText("");
            }
        });

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                userNameStr = userNameTextField.getText();
                passWdStr = String.valueOf(passwordTextField.getPassword());

                try {
                    getServerApplication().newInstance("org.cloud.database.Authenticity");
                } catch (Exception ex) {
                    String message = "Connection is Closed";
                    JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
                }

                if (validateInput(userNameStr, passWdStr)) {
                    MD5 md5Obj = new MD5();
                    passWdStr = md5Obj.md5(passWdStr);
                    try {
                        personalArray = (ArrayList) getServerApplication().invokeMethod("Authenticity",
                                new Object[]{new String(userNameStr), new String(passWdStr)});

                        if (personalArray.size() == 0) {
                            String message = "CardNumber Or Password is InCorrect";
                            JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            loginFrame.dispose();
                            WriteHistory wh=new WriteHistory();
                            if ((Integer)personalArray.get(0)==0)
                            {
                                //System.out.println("Call Admin");
                                wh.write("Login",userNameStr.toUpperCase());
                                AdminUserHomePage homePage=new AdminUserHomePage(personalArray);

                            }else {
                                //System.out.println("Call Usual");
                                wh.write("Login",userNameStr);
                                UsualUserHomePage homePage = new UsualUserHomePage(personalArray);
                            }


                        }

                    } catch (Exception ex) {
                        String message = "Connection is Closed";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginFrame.dispose();
            }
        });
    }
    public static void main(String[] args)
    {
        try {
            LoginPage introPage = new LoginPage();
            introPage.ShowLoginPagePage();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    public boolean validateInput(String userNameStr,String passWdStr)
    {
        userNameValidator();
        matcher = pattern.matcher(userNameStr);
        //System.out.println(matcher.matches());
        if (!matcher.matches())
            return false;

        passWordValidator();
        matcher = pattern.matcher(passWdStr);
        //System.out.println(matcher.matches());
        if (!matcher.matches())
            return false;

        return true;
    }
    public void userNameValidator()
    {
        pattern = Pattern.compile(USERNAME_PATTERN);
    }
    public void passWordValidator()
    {
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }

}

