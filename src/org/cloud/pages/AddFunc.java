package org.cloud.pages;

import org.cloud.connectToServer.ServerConn;
import org.cloud.encryption.MD5;
import se.datadosen.component.RiverLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by reza on 8/4/14.
 */
public class AddFunc extends ServerConn{

    public JFrame addFuncFrame;
    public JTextField funcName=new JTextField(20);
    public JTextField funcProtoType=new JTextField(20);
    public JComboBox argNum=new JComboBox();
    public JComboBox fieldTypeComboBox=new JComboBox();
    public JTextArea description=new JTextArea();
    public JTextArea funcCode=new JTextArea();
    public ArrayList fieldTypesArray;

    private Pattern pattern;
    private Matcher matcher;

    private static final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";
    private static final String PASSWORD_PATTERN = "^[a-z0-9_-]{4,18}$";

    public AddFunc()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        try {
            getServerApplication().newInstance("org.cloud.database.GetFieldTypes");
            ArrayList tmp=new ArrayList();
            fieldTypesArray=new ArrayList();
            tmp= (ArrayList) getServerApplication().invokeMethod("getFieldTypes");
            int index=0;
            while (index<tmp.size())
            {
                if (!fieldTypesArray.contains(tmp.get(index)))
                {
                    fieldTypesArray.add(tmp.get(index));
                }
                index++;
            }
            index=0;
            while (index<fieldTypesArray.size())
            {
                fieldTypeComboBox.addItem(fieldTypesArray.get(index));
                index++;
            }

        }catch (Exception e)
        {
            System.out.println("asd : "+e.getMessage());

        }
        //addFuncFrame
        addFuncFrame = new JFrame("Add Function");
        addFuncFrame.setBounds(width/2-250, 210, 500, 500);

        argNum.addItem("");
        argNum.addItem("1");
        argNum.addItem("2");
        argNum.addItem("3");

        //description JTextArea
        description.setSize(400,80);
        description.setPreferredSize(new Dimension(400, 80));
        description.setEditable(true);
        description.setLineWrap(true);
        description.setWrapStyleWord(false);

        //Code JTextArea
        funcCode.setSize(400,120);
        funcCode.setPreferredSize(new Dimension(400, 170));
        funcCode.setEditable(true);
        funcCode.setLineWrap(true);
        funcCode.setWrapStyleWord(false);

        JScrollPane scroll = new JScrollPane (funcCode,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scroll.setSize(400,500);

        funcCode.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println(funcCode.getLineCount());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });


/*        DefaultCaret caret = (DefaultCaret)funcCode.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);*/

        addFuncFrame.getContentPane().setLayout(new RiverLayout());
        addFuncFrame.setDefaultCloseOperation(addFuncFrame.DISPOSE_ON_CLOSE);
        addFuncFrame.setVisible(true);
        //addFuncFrame.setResizable(false);

        addFuncFrame.getContentPane().add("p center",new JLabel("Func Name :        "));
        addFuncFrame.getContentPane().add("",funcName);
        addFuncFrame.getContentPane().add("p center",new JLabel("Func ProtoType : "));
        addFuncFrame.getContentPane().add("",funcProtoType);
        addFuncFrame.getContentPane().add("p center",new JLabel("Arg Number : "));
        addFuncFrame.getContentPane().add("",argNum);
        addFuncFrame.getContentPane().add("p center",new JLabel("Field Type :  "));
        addFuncFrame.getContentPane().add("",fieldTypeComboBox);
        addFuncFrame.getContentPane().add("p center",new JLabel("Description : "));
        addFuncFrame.getContentPane().add("p",description);
        addFuncFrame.getContentPane().add("p center",new JLabel("Function Code : "));
        addFuncFrame.getContentPane().add("p",scroll);

    }

    public static void main(String[] args) {
        AddFunc add=new AddFunc();
    }

  /*  public void addUser()
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
    }*/
    public void userNameValidator()
    {
        pattern = Pattern.compile(USERNAME_PATTERN);
    }
    public void passWordValidator()
    {
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }

}
