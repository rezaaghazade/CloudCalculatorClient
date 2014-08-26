package org.cloud.pages.adminUser;

import domain.Users;
import org.cloud.connectToServer.ConnectToServer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by reza on 8/10/14.
 */
public class UsersList extends ConnectToServer {
    String[] columnNames = {"ID",
            "Type",
            "Name",
            "Family"};
    public ArrayList<Users> userListArray = new ArrayList<Users>();

    public UsersList()
    {

        try {
            getServerApplication().newInstance("org.cloud.database.GetUsersList");
            userListArray= (ArrayList<Users>) getServerApplication().invokeMethod("UsersList");
            System.out.println();

        } catch (Exception e) {
            System.out.println("hei man " + e.getMessage());
        }

        Iterator<Users> iterator = userListArray.iterator();
        Object[][] data =new Object[userListArray.size()][4];
        Users us=new Users();
        int index=0;
        int type=0;
        while (iterator.hasNext()) {
            us=iterator.next();
            data[index][0]=us.getID();
            type=us.getType();
            if (type==0)
                data[index][1]="ADMIN";
            if (type==1)
                data[index][1]="REGULAR";

            data[index][2]=us.getName();
            data[index][3]=us.getFamily();
            index++;
            us=null;
        }

        JFrame showHistoyFrame = new JFrame("System Users ");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        showHistoyFrame.setBounds(width/2-237, 150, 400, 350);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        //JTable table = new JTable();
        JTable table = new JTable(data, columnNames);
        JScrollPane tableContainer = new JScrollPane(table);

        panel.add(tableContainer, BorderLayout.CENTER);
        showHistoyFrame.getContentPane().add(panel);

        showHistoyFrame.pack();
        showHistoyFrame.setVisible(true);

    }
}
