package org.iocaste.packagetool.common;

import java.io.File;

import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Message;

public class PackageTool extends AbstractServiceInterface {
    private static final String NAME = "/iocaste-packagetool/services.html";
    private Function function;
    
    /**
     * 
     * @param function
     */
    public PackageTool(Function function) {
        initService(function, NAME);
        this.function = function;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public static final boolean hasPackage(String name) {
        String home = System.getProperty("catalina.home");
        File file = new File(new StringBuilder(home).
                append(System.getProperty("file.separator")).
                append("webapps").toString());
        String[] files = file.list();
        
        for (String filename : files)
            if (filename.equals(name))
                return true;
        
        return false;
    }
    
    /**
     * 
     * @param name
     * @return
     * @throws Exception
     */
    public final byte install(String name) throws Exception {
        InstallData data;
        String viewname = new StringBuilder("/").
                append(name).
                append("/view.html").toString();
        GenericService service = new GenericService(function, viewname);
        Message message = new Message();
        
        message.setId("install");
        message.add("name", name);
        
        data = (InstallData)service.invoke(message);
        
        message.setId("install");
        message.clear();
        message.add("data", data);
        
        return (Byte)call(message);
    }

}
