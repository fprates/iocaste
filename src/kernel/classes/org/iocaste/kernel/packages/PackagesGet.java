package org.iocaste.kernel.packages;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class PackagesGet extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String home = System.getProperty("catalina.home");
        File dir = new File(new StringBuilder(home).
                append(System.getProperty("file.separator")).
                append("webapps").toString());
        File[] files = dir.listFiles();
        List<String> list = new ArrayList<>();
        
        for (File file : files)
            if (file.isDirectory())
                list.add(file.getName());
        
        return list.toArray(new String[0]);
    }
}
