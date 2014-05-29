package org.iocaste.workbench.common;

import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

public class Workbench extends AbstractServiceInterface {
    public static final String SERVICENAME = "/iocaste-workbench/services.html";
    
    public Workbench(Function function) {
        initService(function, SERVICENAME);
    }
    
    public final void compile(Project project) {
        Message message = new Message("compile");
        message.add("project", project);
        call(message);
    }
    
    public final String create(String project) {
        Message message = new Message("create_project");
        message.add("name", project);
        return call(message);
    }
    
    public final String create(String pkgname, String project) {
        Message message = new Message("create_package");
        message.add("package", pkgname);
        message.add("project", project);
        return call(message);
    }
    
    public final Project load(String name) {
        Message message = new Message("load");
        message.add("name", name);
        return call(message);
    }
    
    public final String save(Source source) {
        Message message = new Message("save");
        message.add("source", source);
        return call(message);
    }
}
