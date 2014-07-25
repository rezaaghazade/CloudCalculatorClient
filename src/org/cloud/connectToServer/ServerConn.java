package org.cloud.connectToServer;

import org.j2os.shine.util.reflect.Application;
import org.j2os.shine.util.reflect.ServerApplication;

/**
 * Created by reza on 7/23/14.
 */

public class ServerConn {

    private static ServerApplication serverApplication=null;

    static {
        try {

            serverApplication=new Application().receive("localhost","cloudCalculator");

        }catch (Exception e)
        {
            System.out.println("Error in Static Block : "+e.getMessage());
        }
    }
    public ServerApplication getServerApplication()
    {
        return serverApplication;
    }
}
