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
     * @param group
     * @param username
     */
    public final void assignTaskGroup(String group, String username) {
        Message message = new Message();
        
        message.setId("assign_task_group");
        message.add("group", group);
        message.add("username", username);
        call(message);
    }
    
    public final InstallData dataFromFile(String file) {
        Message message = new Message();
        
        message.setId("data_from_file");
        message.add("file", file);
        return call(message);
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
    public final InstallData getInstallData(String name) {
        String viewname = new StringBuilder("/").
                append(name).
                append("/view.html").toString();
        GenericService service = new GenericService(function, viewname);
        Message message = new Message();
        
        message.setId("install");
        message.add("name", name);
        
        return service.invoke(message);
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
    
    public final int install(InstallData data, String appname) {
        Message message = new Message();
        
        message.setId("install");
        message.add("data", data);
        message.add("name", appname);
        return call(message);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final int install(String name) {
        InstallData data = getInstallData(name);
        return install(data, name);
    }

    /**
     * 
     * @param name
     * @return
     */
    public final boolean isInstalled(String name) {
        Message message = new Message();
        
        message.setId("is_installed");
        message.add("package", name);
        return call(message);
    }
    
    /**
     * 
     * @param name
     */
    public final void uninstall(String name) {
        Message message = new Message();
        
        message.setId("uninstall");
        message.add("package", name);
        call(message);
    }
}
