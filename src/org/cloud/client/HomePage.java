package org.cloud.client;

import java.util.ArrayList;

/**
 * Created by reza on 7/23/14.
 */
public class HomePage extends ServerConn {

    public ArrayList personalInfo=new ArrayList();

    public HomePage(ArrayList personalArray) {
        this.personalInfo=personalArray;

        System.out.println(personalInfo.get(0));
        System.out.println(personalInfo.get(1));
        System.out.println(personalInfo.get(2));
    }

}
