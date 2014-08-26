package org.cloud.pages.adminUser;

import domain.History;
import org.cloud.connectToServer.ConnectToServer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by reza on 8/10/14.
 */
public class HistoryList extends ConnectToServer {
    String[] columnNames = {"ID",
            "Operation Date",
            "Operation Type"};
    public ArrayList<History> historyArray = new ArrayList<History>();

    public HistoryList()
    {

        try {
            getServerApplication().newInstance("org.cloud.database.GetHistory");
            historyArray= (ArrayList<History>) getServerApplication().invokeMethod("GetHs");
            System.out.println();

        } catch (Exception e) {
            System.out.println("hei man " + e.getMessage());
        }

        Iterator<History> iterator = historyArray.iterator();
        Object[][] data =new Object[historyArray.size()][3];
        History hs=new History();
        int index=0;
        while (iterator.hasNext()) {
            hs=iterator.next();
            data[index][0]=hs.getID();
            data[index][1]=hs.getOpDate();
            data[index][2]=hs.getOpName();
            index++;
            hs=null;
        }

        JFrame showHistoyFrame = new JFrame("System History ");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        showHistoyFrame.setBounds(width/2-237, 150, 400, 450);

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
