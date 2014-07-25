package org.cloud.pages;

import org.cloud.connectToServer.ServerConn;
import org.cloud.dto.FieldTypeDTO;
import se.datadosen.component.RiverLayout;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by reza on 7/23/14.
 */
public class HomePage extends ServerConn {

    public ArrayList functionsInfo=new ArrayList();
    //public ArrayList<SectionType> functionsInfo=new ArrayList<SectionType>();
    public JFrame introFrame;
    public JLabel informationlable;
    public JComboBox calcTypeComboBox;
    public ArrayList<String>  fieldTypesArrayList=new ArrayList<String>();
    public ArrayList<FieldTypeDTO>  fieldFuncsArrayList=new ArrayList<FieldTypeDTO>();

    private final JLabel time = new JLabel();
    private final SimpleDateFormat sdf  = new SimpleDateFormat("hh:mm");
    private int   currentSecond;
    private Calendar calendar;

    public HomePage() {

        calcTypeComboBox=new JComboBox();

        ArrayList personInfo=new ArrayList();
        personInfo.add("1");
        personInfo.add("reza");
        personInfo.add("aghazade");
        informationlable=new JLabel();
        informationlable.setText("Welcome "+(String)personInfo.get(1)+" "+(String)personInfo.get(2)+" !");
        informationlable.setVisible(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        introFrame=new JFrame("Cloud Calculator");
        introFrame.setBounds((width/2)-300,120,500,400);
        introFrame.getContentPane().setLayout(new RiverLayout());
        introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        introFrame.setVisible(true);
        introFrame.setResizable(false);

        start();
        introFrame.getContentPane().add("p", informationlable);
        introFrame.getContentPane().add("",new JLabel("                                                   "));
        introFrame.getContentPane().add("   ",time);
        introFrame.getContentPane().add("p",new JLabel("Calculation Type    :     "));
        introFrame.getContentPane().add("",new JLabel("              "));
        introFrame.getContentPane().add(" ",calcTypeComboBox);

        try {
            /*getServerApplication().newInstance("org.cloud.database.GetFunctions");
            functionsInfo= (ArrayList) getServerApplication().invokeMethod("GetFunctionsDetail");*/
            System.out.println();

        }catch (Exception e)
        {
            System.out.println("hei man "+e.getStackTrace());
        }

        FieldTypeDTO sectionTypeDTO=new FieldTypeDTO();

        sectionTypeDTO.setFuncName("Sum");
        sectionTypeDTO.setFuncrototype("Public Sum(Integer a,Integer b)");
        sectionTypeDTO.setArgNumber(2);
        sectionTypeDTO.setSectionType("MATH");
        sectionTypeDTO.setDescription("a+b");
        functionsInfo.add(sectionTypeDTO);

        sectionTypeDTO=new FieldTypeDTO();
        sectionTypeDTO.setFuncName("CircleArea");
        sectionTypeDTO.setFuncrototype("Public CircleArea(Double a)");
        sectionTypeDTO.setArgNumber(1);
        sectionTypeDTO.setSectionType("PHYSIC");
        sectionTypeDTO.setDescription("MATH.PI*(a*a)");
        functionsInfo.add(sectionTypeDTO);

        sectionTypeDTO=new FieldTypeDTO();
        sectionTypeDTO.setFuncName("Minus");
        sectionTypeDTO.setFuncrototype("Public Minus(Integer a,Integer b)");
        sectionTypeDTO.setArgNumber(2);
        sectionTypeDTO.setSectionType("MATH");
        sectionTypeDTO.setDescription("a-b");
        functionsInfo.add(sectionTypeDTO);

        defineSectionType();
        calcTypeComboBox.addItem("                                                    ");
        int i=0;
        while (i<fieldTypesArrayList.size()){
            calcTypeComboBox.addItem(fieldTypesArrayList.get(i));
            i++;
        }

    }
    public void defineFuncForEachField()
    {

    }

    public void defineSectionType()
    {
        FieldTypeDTO sectionTypeDTO=new FieldTypeDTO();
        int i=0;
        String tmp;
        while (i<functionsInfo.size()){
            sectionTypeDTO= (FieldTypeDTO) functionsInfo.get(i);
            tmp=sectionTypeDTO.getSectionType();
            if (!fieldTypesArrayList.contains(tmp))
                fieldTypesArrayList.add(tmp);
            i++;
        }
    }
    public static void main(String[] args) {
        HomePage homePage=new HomePage();
    }
    private void reset() {
        calendar = Calendar.getInstance();
        currentSecond = calendar.get(Calendar.SECOND);
    }
    public void start() {
        reset();
        java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate( new TimerTask(){
            public void run(){
                if( currentSecond == 60 ) {
                    reset();
                }
                time.setText( String.format("%s:%02d", sdf.format(calendar.getTime()), currentSecond ));
                currentSecond++;
            }
        }, 0, 1000 );
    }

}
