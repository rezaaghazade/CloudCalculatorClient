package org.cloud.pages;

import org.cloud.connectToServer.ServerConn;

/**
 * Created by reza on 8/6/14.
 */
public class WriteHistory extends ServerConn {
    public void write(String opName,String id)throws Exception
    {
        getServerApplication().newInstance("org.cloud.database.WriteHistory");

        boolean functionDetailFlag=(Boolean)getServerApplication().invokeMethod("PushOperationHistory",
                new Object[]{
                        new String(opName),
                        new String(id),

                        });
    }
}
