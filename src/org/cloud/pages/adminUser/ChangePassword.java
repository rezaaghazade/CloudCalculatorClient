package org.cloud.pages.adminUser;

import org.cloud.connectToServer.ConnectToServer;
import org.cloud.encryption.MD5;
import org.cloud.writeHistory.WriteHistory;
import se.datadosen.component.RiverLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by reza on 8/7/14.
 */

public class ChangePassword extends ConnectToServer {
    public JFrame changePasswdFrame;
    public JButton changePasswdBtn;

    JTextField userName=new JTextField(20);
    JTextField passWord=new JTextField(20);
    JTextField passWordConfirm=new JTextField(20);

    private Pattern pattern;
    private Matcher matcher;

    private static final String USERNAME_PATTERN = "^[A-Za-z0-9_-]{3,15}$";
    private static final String PASSWORD_PATTERN = "^[0-9]{4,18}$";

    public ChangePassword()
    {
        System.gc();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        //changePasswdFrame
        changePasswdFrame = new JFrame("Change Password");
        changePasswdFrame.setBounds(width/2-172, 150, 500, 300);
        changePasswdBtn=new JButton("Change Password");

        changePasswdFrame.getContentPane().setLayout(new RiverLayout());
        changePasswdFrame.setDefaultCloseOperation(changePasswdFrame.DISPOSE_ON_CLOSE);
        changePasswdFrame.setVisible(true);
        changePasswdFrame.setResizable(false);

        userName.setToolTipText("^[A-Za-z0-9_-]{3,16}$ ... (3-16)");
        passWord.setToolTipText("^[a-z0-9_-]{6,18}$ ... (6-18)");

        changePasswdFrame.getContentPane().add("p center",new JLabel("UserName :"));
        changePasswdFrame.getContentPane().add("",userName);
        changePasswdFrame.getContentPane().add("p center",new JLabel("Password  :"));
        changePasswdFrame.getContentPane().add("",passWord);
        changePasswdFrame.getContentPane().add("p center",new JLabel("Confirm  :    "));
        changePasswdFrame.getContentPane().add("",passWordConfirm);
        changePasswdFrame.getContentPane().add("p center",new JLabel(""));
        changePasswdFrame.getContentPane().add("p center",changePasswdBtn);
        changePasswdFrame.pack();

        changePasswdBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int flag=validateInput();
                String message;
                switch (flag)
                {
                    case 1:
                        message = "UserName Is Not in Correct Style";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
                        message="";
                        break;
                    case 2:
                        message = "Password Is Not in Correct Style";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
                        message="";
                        break;
                    case 3:
                        message = "Password And Confirm Is Not The Same";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
                        message="";
                        break;
                    case 0:
                        boolean checkFlag=checkForUniqueUser();
                        if (checkFlag)
                        {
                            changePassword();
                            WriteHistory wh=new WriteHistory();
                            wh.write("Change Password",userName.getText());
                        }

                        else {
                            message = "UserName is Not Exist";
                            JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
                            message="";
                        }
                        break;
                }
            }
        });
    }
    public boolean checkForUniqueUser()
    {
        try {

            getServerApplication().newInstance("org.cloud.database.CheckForUniqueUser");
            MD5 md5=new MD5();
            Boolean flag= (Boolean) getServerApplication().invokeMethod("userUniqueCheck",new Object[]{new String(userName.getText())});

            if (!flag)
                return true;

        }catch (Exception e)
        {

            System.out.println(e.getMessage());
        }
        return false;
    }
    public void changePassword()
    {
        try {

            getServerApplication().newInstance("org.cloud.database.ChangePassword");
            MD5 md5=new MD5();
            Boolean flag= (Boolean) getServerApplication().invokeMethod("changePasswd",new Object[]{
                    new String(userName.getText()),
                    new String(md5.md5(passWord.getText())),
                    });

            String message = userName.getText()+" Password Changed";
            JOptionPane.showMessageDialog(new JFrame(), message, "Information", JOptionPane.INFORMATION_MESSAGE);

        }catch (Exception e)
        {

        }
    }
    public int validateInput()
    {
        userNameValidator();
        matcher = pattern.matcher(userName.getText());
        if (!matcher.matches())
            return 1;

        passWordValidator();
        matcher = pattern.matcher(passWord.getText());
        if (!matcher.matches())
            return 2;

        //**************equal
        /*if (passWord.getText().equals(passWordConfirm.getText()))
            return 3;*/

        return 0;
    }

    public void userNameValidator()
    {
        pattern = Pattern.compile(USERNAME_PATTERN);
    }
    public void passWordValidator()
    {
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }
    public static void main(String[] args) {
        ChangePassword cp=new ChangePassword();
    }

}
