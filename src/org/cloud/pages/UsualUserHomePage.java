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
 * Created by reza on 7/23/14.
 */

public class UsualUserHomePage extends ServerConn {

    public JFrame introFrame;
    public JLabel informationlable;
    public JLabel descriptionlable=new JLabel("Description : ");
    public JTextArea descriptionJtextArea=new JTextArea();
    public JComboBox calcTypeComboBox;
    public JPanel jTextFieldPanel=new JPanel();
    public JTextField jTextField[];
    public JButton calcBtn=new JButton("Calculate !");
    public FieldTypeDTO currentFieldTypeObj=new FieldTypeDTO();
    public ArrayList functionsInfo = new ArrayList();
    public ArrayList<String> fieldTypesArrayList = new ArrayList<String>();
    public HashMap<String,ArrayList> funcGroupedByFieldsArrayList =new HashMap<String, ArrayList>();
    public JPanel jRadioButtonsPanel=new JPanel();

    private static final String IMG_PATH = "./cloud-calculator.jpg";

    int index=0;
    //Clock
    private final JLabel time = new JLabel();
    private final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
    private int currentSecond;
    private Calendar calendar;

    public UsualUserHomePage(ArrayList personInfo) {

        calcTypeComboBox = new JComboBox();

        descriptionlable.setVisible(false);

        descriptionJtextArea.setSize(500,120);
        descriptionJtextArea.setPreferredSize(new Dimension(480, 100));
        descriptionJtextArea.setVisible(false);
        descriptionJtextArea.setLineWrap(true);
        descriptionJtextArea.setWrapStyleWord(false);


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        introFrame = new JFrame("Cloud Calculator");
        introFrame.setBounds(width/2-250, 110, 500, 600);
        introFrame.getContentPane().setLayout(new RiverLayout());
        introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        introFrame.setVisible(true);
        introFrame.setResizable(false);

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
        introFrame.getContentPane().add("p", new JLabel("Calculation Type    :     "));
        introFrame.getContentPane().add("", new JLabel("              "));
        introFrame.getContentPane().add(" ", calcTypeComboBox);
        introFrame.getContentPane().add("p p",jRadioButtonsPanel);

        introFrame.getContentPane().add("p",descriptionlable);
        introFrame.getContentPane().add("p",descriptionJtextArea);
        introFrame.getContentPane().add("p p",jTextFieldPanel);

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
        sectionTypeDTO.setFieldType("MATHEMATIC");
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
        sectionTypeDTO.setFieldType("MATHEMATIC");
        sectionTypeDTO.setDescription("a-b");
        functionsInfo.add(sectionTypeDTO);

        sectionTypeDTO = new FieldTypeDTO();
        sectionTypeDTO.setFuncName("Density");
        sectionTypeDTO.setFuncPrototype("Public Density(Double mass,Double Volume)");
        sectionTypeDTO.setArgNum(2);
        sectionTypeDTO.setFieldType("PHYSIC");
        sectionTypeDTO.setDescription("Mass/Volume");
        functionsInfo.add(sectionTypeDTO);


        defineSectionType();
        defineFuncForEachField();

        //fill Field comboBox up
        calcTypeComboBox.addItem("                                                    ");
        int i = 0;
        while (i < fieldTypesArrayList.size()) {
            calcTypeComboBox.addItem(fieldTypesArrayList.get(i));
            i++;
        }

        calcTypeComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.gc();
                /*System.out.println("Meg used="+(Runtime.getRuntime().totalMemory();
                Runtime.getRuntime().freeMemory())/(1000*1000)+"M");*/

                jRadioButtonsPanel.removeAll();
                jTextFieldPanel.removeAll();
                final String item = (String)e.getItem();
                descriptionJtextArea.setVisible(false);
                descriptionlable.setVisible(false);

                if (!(calcTypeComboBox.getSelectedIndex() == 0))
                {
                    calcBtn.setVisible(false);
                    try {
                        ArrayList<String> funcNames=new ArrayList<String>();
                        funcNames=getFuncName(funcGroupedByFieldsArrayList.get(item));
                        int index=0;
                        ButtonGroup bg = new ButtonGroup();
                        while (index<funcNames.size())
                        {
                            final JRadioButton rd=new JRadioButton(funcNames.get(index));
                            rd.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {

                                    descriptionJtextArea.setText("");
                                    descriptionJtextArea.setEditable(false);
                                    descriptionJtextArea.setVisible(true);
                                    descriptionlable.setVisible(true);
                                    FieldTypeDTO ftd=new FieldTypeDTO();
                                    ftd=findFieldType(item,rd.getText());
                                    currentFieldTypeObj=ftd;
                                    descriptionJtextArea.setText(ftd.getFuncPrototype()+"\n"+ftd.getDescription());
                                    createArgTextField(ftd.getArgNum());
                                    introFrame.getContentPane().add("p p",jTextFieldPanel);
                                    calcBtn.setVisible(true);
                                    introFrame.getContentPane().add("p p",calcBtn);
                                }
                            });
                            jRadioButtonsPanel.add(rd);
                            bg.add(rd);
                            index++;
                        }
                    }catch (Exception elx)
                    {
                        System.out.println("hi there : "+elx.getMessage());
                    }
                }
                calcBtn.setVisible(false);
                jTextFieldPanel.removeAll();
            }
        });

        calcBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    getServerApplication().newInstance("org.cloud.sections."+currentFieldTypeObj.getFieldType());
                    Double value=new Double(0);

                    if (currentFieldTypeObj.getArgNum()==1)
                    {
                            value= (Double) getServerApplication().invokeMethod(
                                currentFieldTypeObj.getFuncName(),new Object[]{new Double(jTextField[0].getText())});
                    }
                    if (currentFieldTypeObj.getArgNum()==2)
                    {
                            value= (Double) getServerApplication().invokeMethod(
                                currentFieldTypeObj.getFuncName(),new Object[]{new Double(jTextField[0].getText()),new Double(jTextField[1].getText())});
                    }
                    String message="Result is : "+value.toString().toUpperCase();
                    JOptionPane.showMessageDialog(new JFrame(),message, "Info", JOptionPane.INFORMATION_MESSAGE);
                }catch (Exception elx)
                {
                    String message = elx.getMessage();

                    JOptionPane.showMessageDialog(new JFrame(),message.toUpperCase(), "Info", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void createArgTextField(Integer argNum) {
        jTextFieldPanel.removeAll();
        int columnCounter=1;
        jTextField=new JTextField[argNum];
        index=0;
        while (index<argNum)
        {
            columnCounter+=index;
            jTextField[index]=new JTextField();
            jTextField[index].setColumns(10);
            jTextField[index].setText("Arg " + columnCounter);

            final int finalI = index;
            jTextField[index].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    jTextField[finalI].setText("");
                }
            });
            jTextFieldPanel.add(jTextField[index]);
            index++;
        }
    }

    public void jtextFieldInputChecker(int lenght) {
        if (lenght==0)
            System.out.println("disable");
            calcBtn.setEnabled(false);
    }

    public FieldTypeDTO findFieldType(String itemSelected,String funName) {
        int index=0;
        ArrayList arrayList=new ArrayList();
        FieldTypeDTO fieldTypeDTO=new FieldTypeDTO();
        while (index<funcGroupedByFieldsArrayList.get(itemSelected).size())
        {
            if (((FieldTypeDTO)funcGroupedByFieldsArrayList.get(itemSelected).get(index)).getFuncName()==funName)
            {
                fieldTypeDTO=((FieldTypeDTO)funcGroupedByFieldsArrayList.get(itemSelected).get(index));
                System.out.println(fieldTypeDTO.getFuncPrototype());
            }
            index++;
        }
        return fieldTypeDTO;
    }

    public ArrayList<String> getFuncName(ArrayList arr) {
        ArrayList<String> tmp=new ArrayList<String>();
        int index=0;
        while (index<arr.size())
        {
            tmp.add(((FieldTypeDTO)arr.get(index)).getFuncName());
            index++;
        }
        return tmp;
    }

    public void defineFuncForEachField() {

        int i = 0;
        int j=0;
        String tmp;
        String fieldName="";
            while (i<fieldTypesArrayList.size())
            {
                ArrayList tempArrayList=new ArrayList();
                fieldName=fieldTypesArrayList.get(i);
                //System.out.println(fieldName);
                while (j<functionsInfo.size())
                {
                    if (((FieldTypeDTO) functionsInfo.get(j)).getFieldType()==fieldName)
                    {
                        tempArrayList.add(functionsInfo.get(j));
                    }
                    j++;
                }
                j=0;
                funcGroupedByFieldsArrayList.put(fieldName,tempArrayList);
                tempArrayList=null;
                i++;
            }
    }

    public void show() {
        int index=0;
        ArrayList arr=new ArrayList();
        while (index<funcGroupedByFieldsArrayList.size())
        {
            arr=funcGroupedByFieldsArrayList.get(fieldTypesArrayList.get(index));
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
        //UsualUserHomePage homePage = new UsualUserHomePage();
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
