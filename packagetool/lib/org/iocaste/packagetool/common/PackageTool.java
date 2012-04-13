package org.iocaste.packagetool.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
     * @return
     */
    public static final String[] getAvailablePackages() {
        String home = System.getProperty("catalina.home");
        File dir = new File(new StringBuilder(home).
                append(System.getProperty("file.separator")).
                append("webapps").toString());
        File[] files = dir.listFiles();
        List<String> list = new ArrayList<String>();
        
        for (File file : files)
            if (file.isDirectory())
                list.add(file.getName());
        
        return list.toArray(new String[0]);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public static final boolean hasPackage(String name) {
        String[] files = getAvailablePackages();
        
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
    public final Integer install(String name) throws Exception {
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
        message.add("name", name);
        
        return call(message);
    }

    /**
     * 
     * @param name
     * @return
     * @throws Exception
     */
    public final boolean isInstalled(String name) throws Exception {
        Message message = new Message();
        
        message.setId("is_installed");
        message.add("package", name);
        
        return call(message);
    }
    
    /**
     * 
     * @param name
     * @throws Exception
     */
    public final void uninstall(String name) throws Exception {
        Message message = new Message();
        
        message.setId("uninstall");
        message.add("package", name);
        
        call(message);
    }
}
