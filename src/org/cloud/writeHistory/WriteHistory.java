package org.cloud.writeHistory;

import org.cloud.connectToServer.ConnectToServer;

/**
 * Created by reza on 8/6/14.
 */
public class WriteHistory extends ConnectToServer {
    public void write(String opName,String id)
    {
        try {
            getServerApplication().newInstance("org.cloud.database.WriteHistory");

            boolean functionDetailFlag=(Boolean)getServerApplication().invokeMethod("PushOperationHistory",
                    new Object[]{
                            new String(opName),
                            new String(id),

                    });
        }catch (Exception e)
        {
            System.out.println("Write History Error : "+e.getMessage());
        }

    }
}
