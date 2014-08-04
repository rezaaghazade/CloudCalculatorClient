package org.cloud.pages;

import org.cloud.connectToServer.ServerConn;
import org.cloud.dto.FieldTypeDTO;
import se.datadosen.component.RiverLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimerTask;

/**
 * Created by reza on 8/4/14.
 */
public class AdminUserHomePage extends ServerConn{

    public AddUser addUser;
    public AddFunc addFunc;

    public JFrame introFrame;
    public JFrame addFuncFrame;

    public JButton addUserButton;
    public JButton addFuncButton;
    public JButton calculatorButton;



    public JLabel informationlable;

    public ArrayList functionsInfo = new ArrayList();
    private static final String IMG_PATH = "./cloud-calculator.jpg";

    int index=0;
    //Clock
    private final JLabel time = new JLabel();
    private final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
    private int currentSecond;
    private Calendar calendar;

    public AdminUserHomePage() {


        ArrayList personInfo = new ArrayList();
        personInfo.add("1");
        personInfo.add("reza");
        personInfo.add("aghazade");
        //informationlable = new JLabel();
        //informationlable.setText();
        //informationlable.setVisible(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        //Intro Frame
        introFrame = new JFrame("Cloud Calculator");
        introFrame.setBounds(width/2-250, 110, 500, 600);
        introFrame.getContentPane().setLayout(new RiverLayout());
        introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        introFrame.setVisible(true);
        introFrame.setResizable(false);




        //addFuncFrame
        addFuncFrame = new JFrame("Add Function To Calculator");
        addFuncFrame.setBounds(width/2-250, 110, 500, 600);


        JLabel pic = new JLabel();
        //add(pic);
        ImageIcon test = new ImageIcon("small.png");
        pic.setIcon(test);
        pic.setBounds(118,0,263,149);
        //pic
        introFrame.getContentPane().add("center",pic);


        //Clock start
        start();

        introFrame.getContentPane().add("p", new JLabel("Welcome " + (String) personInfo.get(1) + " " + (String) personInfo.get(2) + " !"));
        introFrame.getContentPane().add("", new JLabel("                                                   "));
        introFrame.getContentPane().add("   ", time);

        addUserButton=new JButton("Add User");
        addFuncButton=new JButton("Add Function");
        calculatorButton=new JButton("Calculator");

        introFrame.getContentPane().add("p", new JLabel(""));
        introFrame.getContentPane().add("p",addUserButton);
        introFrame.getContentPane().add("p",addFuncButton);
        introFrame.getContentPane().add("p",calculatorButton);


        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if (addUser.addUserFrame.isShowing())
                    {
                        String message = "Page is Open";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Information", JOptionPane.INFORMATION_MESSAGE);
                    }

                }catch (Exception elx)
                {
                    addUser=new AddUser();
                }
                /*if (!addUserFrame.isShowing())
                {
                    addUserFrame.getContentPane().setLayout(new RiverLayout());
                    addUserFrame.setDefaultCloseOperation(addUserFrame.DISPOSE_ON_CLOSE);
                    addUserFrame.setVisible(true);
                    addUserFrame.setResizable(false);

                    JTextField userName=new JTextField(20);
                    JPasswordField passWord=new JPasswordField(20);

                    JComboBox typeComboBox=new JComboBox();
                    typeComboBox.addItem("ADMIN");
                    typeComboBox.addItem("REGULAR");

                    JTextField name=new JTextField(20);
                    JTextField family=new JTextField(20);

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

                }*/
            }
        });

        addFuncButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!addFuncFrame.isShowing())
                {
                    addFuncFrame.getContentPane().setLayout(new RiverLayout());
                    addFuncFrame.setDefaultCloseOperation(addFuncFrame.DISPOSE_ON_CLOSE);
                    addFuncFrame.setVisible(true);
                    addFuncFrame.setResizable(false);
                }

            }
        });

        calculatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        try {
            /*getServerApplication().newInstance("org.cloud.database.GetFunctions");
            functionsInfo= (ArrayList) getServerApplication().invokeMethod("GetFunctionsDetail");*/
            System.out.println();

        } catch (Exception e) {
            System.out.println("hei man " + e.getStackTrace());
        }


    }

    public static void main(String[] args) {
        AdminUserHomePage homePage = new AdminUserHomePage();
    }

    private void reset() {
        calendar = Calendar.getInstance();
        currentSecond = calendar.get(Calendar.SECOND);
    }

    public void start() {
        reset();
        java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (currentSecond == 60) {
                    reset();
                }
                time.setText(String.format("%s:%02d", sdf.format(calendar.getTime()), currentSecond));
                currentSecond++;
            }
        }, 0, 1000);
    }
}

