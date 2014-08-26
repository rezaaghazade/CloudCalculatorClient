package org.cloud.pages.adminUser;

import org.cloud.connectToServer.ConnectToServer;
import org.cloud.pages.usualUser.UsualUserHomePage;
import org.cloud.writeHistory.WriteHistory;
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
public class AdminUserHomePage extends ConnectToServer {

    public AddUser addUser;
    //public AddFunc addFunc;

    public JFrame adminPageFrame;
    public JFrame addFuncFrame;

    public JButton addUserButton;
    public JButton addFuncButton;
    public JButton calculatorButton;
    public JButton userListButton;
    public JButton changePasswdButton;
    public JButton historyButton;
    public JButton exitButton;
    private static final String IMG_PATH = "./cloud-calculator.jpg";

    int index=0;
    //Clock
    private final JLabel time = new JLabel();
    private final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
    private int currentSecond;
    private Calendar calendar;

    public AdminUserHomePage(final ArrayList personInfo) {

        System.gc();
        
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
        userListButton=new JButton("Users List");
        changePasswdButton=new JButton("Change Passoword");
        historyButton=new JButton("History");
        exitButton=new JButton("Exit");

        adminPageFrame.getContentPane().add("p", new JLabel(""));
        adminPageFrame.getContentPane().add("p",addUserButton);
        adminPageFrame.getContentPane().add("p",historyButton);
        adminPageFrame.getContentPane().add("p",userListButton);
        adminPageFrame.getContentPane().add("p",addFuncButton);
        adminPageFrame.getContentPane().add("p",changePasswdButton);
        adminPageFrame.getContentPane().add("p",calculatorButton);
        adminPageFrame.getContentPane().add("p",exitButton);


        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser=new AddUser(personInfo);
            }
        });

        addFuncButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddFunc addFunc=new AddFunc(personInfo);

            }
        });

        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HistoryList hl=new HistoryList();
            }
        });

        userListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsersList usersList=new UsersList();
            }
        });
        changePasswdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangePassword chP=new ChangePassword();
            }
        });

        calculatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsualUserHomePage home=new UsualUserHomePage(personInfo);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminPageFrame.dispose();
                WriteHistory wh=new WriteHistory();
                wh.write("Exit", (String) personInfo.get(1));
                System.exit(0);
                //LoginPage loginPage=new LoginPage();
            }
        });

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

