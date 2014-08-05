package org.cloud.pages;

import org.cloud.connectToServer.ServerConn;
import org.cloud.encryption.MD5;
import se.datadosen.component.RiverLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by reza on 8/4/14.
 */
public class AddUser extends ServerConn{
    public JFrame addUserFrame;
    public JButton createUserBtn;

    JTextField userName=new JTextField(20);
    JTextField passWord=new JTextField(20);
    JComboBox typeComboBox=new JComboBox();
    JTextField name=new JTextField(20);
    JTextField family=new JTextField(20);

    private Pattern pattern;
    private Matcher matcher;

    private static final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";
    private static final String PASSWORD_PATTERN = "^[a-z0-9_-]{4,18}$";

    public AddUser()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        //addUserFrame
        addUserFrame = new JFrame("Add User");
        addUserFrame.setBounds(width/2-250, 210, 500, 300);

        addUserFrame.getContentPane().setLayout(new RiverLayout());
        addUserFrame.setDefaultCloseOperation(addUserFrame.DISPOSE_ON_CLOSE);
        addUserFrame.setVisible(true);
        addUserFrame.setResizable(false);


        typeComboBox.addItem("");
        typeComboBox.addItem("ADMIN");
        typeComboBox.addItem("REGULAR");



        userName.setToolTipText("^[a-z0-9_-]{3,16}$ ... (3-16)");
        passWord.setToolTipText("^[a-z0-9_-]{6,18}$ ... (6-18)");

        addUserFrame.getContentPane().add("p center",new JLabel("UserName :"));
        addUserFrame.getContentPane().add("",userName);
        addUserFrame.getContentPane().add("p center",new JLabel("Password : "));
        addUserFrame.getContentPane().add("",passWord);
        addUserFrame.getContentPane().add("p center",new JLabel("Type : "));
        addUserFrame.getContentPane().add("",typeComboBox);
        addUserFrame.getContentPane().add("p center",new JLabel("Name :  "));
        addUserFrame.getContentPane().add("",name);
        addUserFrame.getContentPane().add("p center",new JLabel("Family : "));
        addUserFrame.getContentPane().add("",family);

        createUserBtn=new JButton("Add User");

        addUserFrame.getContentPane().add("p center",new JLabel(""));
        addUserFrame.getContentPane().add("p",createUserBtn);

        createUserBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int flag=validateInput();
                String message;
                switch (flag)
                {
                    case 1:
                        message = "UserName Is Not in Correct Style";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    case 2:
                        message = "Password Is Not in Correct Style";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    case 3:
                        message = "Select Type";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    case 4:
                        message = "Name is Empty";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    case 5:
                        message = "Family is Empty";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    case 0:
                        addUser();
                        break;
                }

            }
        });

    }

    public void addUser()
    {
        try {

            getServerApplication().newInstance("org.cloud.database.AddUser");
            MD5 md5=new MD5();
            Boolean flag= (Boolean) getServerApplication().invokeMethod("PushUser",new Object[]{
                    new String(userName.getText()),
                    new String(md5.md5(passWord.getText())),
                    new Integer((typeComboBox.getSelectedIndex()-1)),
                    new String(name.getText()),
                    new String(family.getText())});

            String message = "User "+userName.getText()+" Added.";
            JOptionPane.showMessageDialog(new JFrame(), message, "Information", JOptionPane.INFORMATION_MESSAGE);
        }catch (Exception e)
        {
            System.out.println("Add User : "+e.getMessage());
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

        System.out.println(typeComboBox.getSelectedIndex());
        if (typeComboBox.getSelectedIndex()==0)
            return 3;

        if (name.getText().length()==0)
            return 4;

        if (family.getText().length()==0)
            return 5;

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

}
