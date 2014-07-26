package org.cloud.pages;

import org.cloud.connectToServer.ServerConn;
import org.cloud.dto.FieldTypeDTO;
import se.datadosen.component.RiverLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimerTask;

/**
 * Created by reza on 7/23/14.
 */
public class HomePage extends ServerConn {

    //public ArrayList<SectionType> functionsInfo=new ArrayList<SectionType>();
    public JFrame introFrame;
    public JLabel informationlable;
    public JComboBox calcTypeComboBox;

    public ArrayList functionsInfo = new ArrayList();
    public ArrayList<String> fieldTypesArrayList = new ArrayList<String>();
    public static HashMap<String,ArrayList> funcGroupedByFieldsArrayList =new HashMap<String, ArrayList>();

    private final JLabel time = new JLabel();
    private final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
    private int currentSecond;
    private Calendar calendar;

    public HomePage() {

        calcTypeComboBox = new JComboBox();

        ArrayList personInfo = new ArrayList();
        personInfo.add("1");
        personInfo.add("reza");
        personInfo.add("aghazade");
        informationlable = new JLabel();
        informationlable.setText("Welcome " + (String) personInfo.get(1) + " " + (String) personInfo.get(2) + " !");
        informationlable.setVisible(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        introFrame = new JFrame("Cloud Calculator");
        introFrame.setBounds((width / 2) - 300, 120, 500, 400);
        introFrame.getContentPane().setLayout(new RiverLayout());
        introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        introFrame.setVisible(true);
        introFrame.setResizable(false);

        start();
        introFrame.getContentPane().add("p", informationlable);
        introFrame.getContentPane().add("", new JLabel("                                                   "));
        introFrame.getContentPane().add("   ", time);
        introFrame.getContentPane().add("p", new JLabel("Calculation Type    :     "));
        introFrame.getContentPane().add("", new JLabel("              "));
        introFrame.getContentPane().add(" ", calcTypeComboBox);

        try {
            /*getServerApplication().newInstance("org.cloud.database.GetFunctions");
            functionsInfo= (ArrayList) getServerApplication().invokeMethod("GetFunctionsDetail");*/
            System.out.println();

        } catch (Exception e) {
            System.out.println("hei man " + e.getStackTrace());
        }

        FieldTypeDTO sectionTypeDTO = new FieldTypeDTO();

        sectionTypeDTO.setFuncName("Sum");
        sectionTypeDTO.setFuncPrototype("Public Sum(Integer a,Integer b)");
        sectionTypeDTO.setArgNum(2);
        sectionTypeDTO.setFieldType("MATH");
        sectionTypeDTO.setDescription("a+b");
        functionsInfo.add(sectionTypeDTO);

        sectionTypeDTO = new FieldTypeDTO();
        sectionTypeDTO.setFuncName("CircleArea");
        sectionTypeDTO.setFuncPrototype("Public CircleArea(Double a)");
        sectionTypeDTO.setArgNum(1);
        sectionTypeDTO.setFieldType("PHYSIC");
        sectionTypeDTO.setDescription("MATH.PI*(a*a)");
        functionsInfo.add(sectionTypeDTO);

        sectionTypeDTO = new FieldTypeDTO();
        sectionTypeDTO.setFuncName("Minus");
        sectionTypeDTO.setFuncPrototype("Public Minus(Integer a,Integer b)");
        sectionTypeDTO.setArgNum(2);
        sectionTypeDTO.setFieldType("MATH");
        sectionTypeDTO.setDescription("a-b");
        functionsInfo.add(sectionTypeDTO);

        //fill Field comboBox up
        defineSectionType();
        calcTypeComboBox.addItem("                                                    ");
        int i = 0;
        while (i < fieldTypesArrayList.size()) {
            calcTypeComboBox.addItem(fieldTypesArrayList.get(i));
            i++;
        }
        defineFuncForEachField();

        JRadioButton birdButton = new JRadioButton("Bird");
        //birdButton.setSelected(true);
        JRadioButton catButton = new JRadioButton("CAt");
        JRadioButton dogButton = new JRadioButton("dog");
        JRadioButton rabbitButton = new JRadioButton();
        JRadioButton pigButton = new JRadioButton();



        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(birdButton);
        group.add(catButton);
        group.add(dogButton);
        group.add(rabbitButton);
        group.add(pigButton);

        JPanel jPanel = new JPanel();
        jPanel.add(birdButton);
        jPanel.add(catButton);
        jPanel.add(dogButton);
        introFrame.getContentPane().add("p p",jPanel);



        calcTypeComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String item = (String)e.getItem();

            }
        });

    }

    public void defineFuncForEachField() {

        int i = 0;
        int j=0;
        String tmp;
        ArrayList<FieldTypeDTO> tempArrayList=new ArrayList<FieldTypeDTO>();
        String fieldName;
            while (i<fieldTypesArrayList.size())
            {
                fieldName=fieldTypesArrayList.get(i);
                //System.out.println(fieldName);
                while (j<functionsInfo.size())
                {
                    if (((FieldTypeDTO) functionsInfo.get(j)).getFieldType()==fieldName)
                    {
                        System.out.println("equal : "+i+j);
                        tempArrayList.add((FieldTypeDTO) functionsInfo.get(j));
                    }
                    j++;
                }
                j=0;
                funcGroupedByFieldsArrayList.put(fieldName,tempArrayList);
                fieldName=null;
                tempArrayList.clear();
                i++;
            }

    }
    public void show()
    {
        int index=0;
        ArrayList arr=new ArrayList();
        while (index<funcGroupedByFieldsArrayList.size())
        {
            System.out.println(index);
            arr=funcGroupedByFieldsArrayList.get(fieldTypesArrayList.get(index));
            System.out.println(arr.size());
            index++;
        }
    }

    public void defineSectionType() {
        FieldTypeDTO sectionTypeDTO = new FieldTypeDTO();
        int i = 0;
        String tmp;
        while (i < functionsInfo.size()) {
            sectionTypeDTO = (FieldTypeDTO) functionsInfo.get(i);
            tmp = sectionTypeDTO.getFieldType();
            if (!fieldTypesArrayList.contains(tmp))
            {
                fieldTypesArrayList.add(tmp);
            }
            i++;
        }
        sectionTypeDTO = null;
        tmp = null;
    }

    public static void main(String[] args) {
        HomePage homePage = new HomePage();
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
