package org.cloud.pages;

import com.sun.jndi.url.rmi.rmiURLContext;
import org.cloud.connectToServer.ServerConn;
import se.datadosen.component.RiverLayout;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    public JComboBox argNumComboBox=new JComboBox();
    public JComboBox fieldTypeComboBox=new JComboBox();
    public JTextArea description=new JTextArea();
    public JButton funcCode=new JButton("Browse");
    public ArrayList fieldTypesArray;
    public JFileChooser fileChooser = new JFileChooser();
    public String inputFileAddress = "";
    //public JLabel inputFileAddressLable=new JLabel();
    public JButton addFunBtn=new JButton("Add !");
    public JButton exitButton=new JButton("Exit");


    private Pattern pattern;
    private Matcher matcher;

    private static final String USERNAME_PATTERN = "^[A-Za-z_-]{1,15}$";
    private static final String PASSWORD_PATTERN = "^[a-z0-9_-]{4,18}$";

    private final int lineCounter=40;

    public AddFunc(final ArrayList personInfo)
    {
        System.gc();
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
            fieldTypeComboBox.addItem("");
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
        addFuncFrame.setBounds(width/2-215, 150, 500, 500);

        argNumComboBox.addItem("");
        argNumComboBox.addItem("1");
        argNumComboBox.addItem("2");
        argNumComboBox.addItem("3");

        //description JTextArea
        description.setSize(400,80);
        description.setPreferredSize(new Dimension(400, 80));
        description.setEditable(true);
        description.setLineWrap(true);
        description.setWrapStyleWord(false);

        //Code JTextArea
        funcCode.setSize(400,120);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileFilter filter = new FileNameExtensionFilter("Java Function", "java");
        FileFilter filter2 = new FileNameExtensionFilter("Regular Text File","txt");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.addChoosableFileFilter(filter2);
        //fileChooser.setCurrentDirectory(new File("/home/reza/Desktop"));

        funcCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileChooser.showOpenDialog(null) == JFileChooser.OPEN_DIALOG) {
                    inputFileAddress = fileChooser.getSelectedFile().getAbsolutePath();
                    String message = "File Selected :\n"+inputFileAddress;
                    JOptionPane.showMessageDialog(new JFrame(), message, "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        addFuncFrame.getContentPane().setLayout(new RiverLayout());
        addFuncFrame.setDefaultCloseOperation(addFuncFrame.DISPOSE_ON_CLOSE);
        addFuncFrame.setVisible(true);
        addFuncFrame.setResizable(false);

        addFuncFrame.getContentPane().add("p center",new JLabel("Func Name :       "));
        addFuncFrame.getContentPane().add("",funcName);
        addFuncFrame.getContentPane().add("p center",new JLabel("Func ProtoType :"));
        addFuncFrame.getContentPane().add("",funcProtoType);
        addFuncFrame.getContentPane().add("p center",new JLabel("Arg Number : "));
        addFuncFrame.getContentPane().add("",argNumComboBox);
        addFuncFrame.getContentPane().add("p center",new JLabel("Field Type :  "));
        addFuncFrame.getContentPane().add("",fieldTypeComboBox);
        addFuncFrame.getContentPane().add("p center",new JLabel("Description : "));
        addFuncFrame.getContentPane().add("p",description);
        addFuncFrame.getContentPane().add("p center",new JLabel("Function Code :                                 "));
        addFuncFrame.getContentPane().add("",funcCode);
        addFuncFrame.getContentPane().add("p",new JLabel(""));
        addFuncFrame.getContentPane().add("p",new JLabel(""));
        addFuncFrame.getContentPane().add("p",new JLabel(""));
        addFuncFrame.getContentPane().add("p",addFunBtn);
        addFuncFrame.getContentPane().add("p",exitButton);
        addFuncFrame.pack();

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFuncFrame.dispose();
            }
        });

        funcName.setToolTipText("Sum");
        funcProtoType.setToolTipText("Public Sum(Integer a,Integer b)");
        fieldTypeComboBox.setToolTipText("Function Field Type");
        description.setToolTipText("a+b - It Calculate The Sum of Input Argument (a & b)");
        funcCode.setToolTipText("Public Double Sum(Double a,Double b)\n" +
                "    {\n" +
                "        System.out.println(\"in sum\");\n" +
                "        return a+b;\n" +
                "    }");

        addFunBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int flag=validateInput();
                String message;
                switch (flag)
                {
                    case 1:
                        message = "Func Name is Incorrect";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    case 2:
                        message = "Arg Number is Not Selected";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    case 3:
                        message = "Field Type is Not Selected";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
                        break;

                    case 4:
                        message = "Description Text Is Empty";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
                        break;

                    case 5:
                        message = "Function File Did Not Selected";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
                        break;

                    case 0:
                        message = "Are You Sure To Add This Method ?";
                        int result = JOptionPane.showConfirmDialog((Component) null, message,"alert", JOptionPane.OK_CANCEL_OPTION);

                        //ok is zero
                        if (result==0)
                        {
                            //Add Function to Server
                            try {
                                if (checkForUniqueFunc())
                                {
                                    System.out.println((String) fieldTypeComboBox.getSelectedItem());
                                    getServerApplication().newInstance("org.cloud.compiler.ServerCompiler");
                                    Boolean returnFlag= (Boolean) getServerApplication().invokeMethod("addMethod",
                                            new Object[]{new String((String) fieldTypeComboBox.getSelectedItem()),
                                            new String("public Double Area(Double a,Double b){ return a*b; }")});
                                    System.out.println("return Flag "+returnFlag);
                                    if (returnFlag)
                                    {
                                        getServerApplication().newInstance("org.cloud.database.AddFunctionDetail");
                                        boolean functionDetailFlag=(Boolean)getServerApplication().invokeMethod("PushFunctionDetail",
                                                new Object[]{new String(funcName.getText().toUpperCase()),
                                                        new String(funcProtoType.getText().toUpperCase()),
                                                        new String((String)argNumComboBox.getSelectedItem()),
                                                        new String((String)fieldTypeComboBox.getSelectedItem()),
                                                        new String(description.getText())});

                                        System.out.println("functionDetailFlag "+functionDetailFlag);

                                        JOptionPane.showMessageDialog(new JFrame(), "Method Added To The System", "Info",
                                                JOptionPane.INFORMATION_MESSAGE);

                                        WriteHistory wh=new WriteHistory();
                                        try {
                                            wh.write("Add Function"+"."+funcName.getText(),(String) personInfo.get(1));
                                        }catch (Exception elx)
                                        {

                                        }
                                    }
                                }
                            }catch (Exception elx)
                            {

                                System.out.println("Add Method Error : "+elx.getCause());
                            }

                        }
                        break;
                }
            }
        });

    }
    public boolean checkForUniqueFunc()throws Exception
    {
        String funcionName=funcName.getText().toUpperCase();
        String field= ((String) fieldTypeComboBox.getSelectedItem()).toUpperCase();

        getServerApplication().newInstance("org.cloud.database.CheckForUniqueFunc");

        boolean tmp=(Boolean)getServerApplication().invokeMethod("checkFunc",new Object[]{new String(funcionName),new String(field)});

        System.out.println("Return : "+ tmp);
        if (tmp==false)
        {
            String message = "Method With The Same Name Is Exist In The Class";
            //int result = JOptionPane.showConfirmDialog((Component) null, message,"alert", JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }else {
            return true;
        }

    }
    public int validateInput()
    {
        userNameValidator();
        matcher = pattern.matcher(funcName.getText());
        if (!matcher.matches())
            return 1;

        if (argNumComboBox.getSelectedIndex()==0)
            return 2;
        if (fieldTypeComboBox.getSelectedIndex()==0)
            return 3;
        if (description.getLineCount()==0)
            return 4;
        if (inputFileAddress.length()==0)
            return 5;
        return 0;
    }

    public void userNameValidator()
    {
        pattern = Pattern.compile(USERNAME_PATTERN);
    }

    /*public static void main(String[] args) {
        AddFunc add=new AddFunc();
    }*/

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
 */

}
