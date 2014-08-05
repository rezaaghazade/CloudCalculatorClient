package org.cloud.pages;

import org.cloud.connectToServer.ServerConn;
import se.datadosen.component.RiverLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimerTask;

/**
 * Created by reza on 8/4/14.
 */
public class AdminUserHomePage extends ServerConn{

    public AddUser addUser;
    public AddFunc addFunc;

    public JFrame adminPageFrame;
    public JFrame addFuncFrame;

    public JButton addUserButton;
    public JButton addFuncButton;
    public JButton calculatorButton;
    public JButton exitButton;

    public final ArrayList personInfo2;
    public JLabel informationlable;

    public ArrayList functionsInfo = new ArrayList();
    private static final String IMG_PATH = "./cloud-calculator.jpg";

    int index=0;
    //Clock
    private final JLabel time = new JLabel();
    private final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
    private int currentSecond;
    private Calendar calendar;

    public AdminUserHomePage(ArrayList personInfo) {

        System.gc();

        personInfo2=new ArrayList(personInfo);
        //personInfo2=personInfo;
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        //Intro Frame
        adminPageFrame = new JFrame("Cloud Calculator");
        adminPageFrame.setBounds(width/2-250, 110, 500, 600);
        adminPageFrame.getContentPane().setLayout(new RiverLayout());
        adminPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminPageFrame.setVisible(true);
        adminPageFrame.setResizable(false);


        //addFuncFrame
        addFuncFrame = new JFrame("Add Function To Calculator");
        addFuncFrame.setBounds(width/2-250, 110, 500, 600);


        JLabel pic = new JLabel();
        //add(pic);
        ImageIcon test = new ImageIcon("small.png");
        pic.setIcon(test);
        pic.setBounds(118,0,263,149);
        //pic
        adminPageFrame.getContentPane().add("center",pic);


        //Clock start
        start();

        adminPageFrame.getContentPane().add("p", new JLabel("Welcome " + (String) personInfo.get(1) + " " + (String) personInfo.get(2) + " !"));
        adminPageFrame.getContentPane().add("", new JLabel("                                                   "));
        adminPageFrame.getContentPane().add("   ", time);

        addUserButton=new JButton("Add User");
        addFuncButton=new JButton("Add Function");
        calculatorButton=new JButton("Calculator");
        exitButton=new JButton("Exit");


        adminPageFrame.getContentPane().add("p", new JLabel(""));
        adminPageFrame.getContentPane().add("p",addUserButton);
        adminPageFrame.getContentPane().add("p",addFuncButton);
        adminPageFrame.getContentPane().add("p",calculatorButton);
        adminPageFrame.getContentPane().add("p",exitButton);


        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser=new AddUser();
                try {
                   /* if (addUser.addUserFrame.isShowing())
                    {
                        String message = "Page is Open";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Information", JOptionPane.INFORMATION_MESSAGE);
                    }*/
                }catch (Exception elx)
                {

                }
            }
        });

        addFuncButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddFunc addFunc=new AddFunc();

            }
        });

        calculatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsualUserHomePage home=new UsualUserHomePage(personInfo2);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminPageFrame.dispose();
                LoginPage loginPage=new LoginPage();
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
        //AdminUserHomePage homePage = new AdminUserHomePage();
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

