package org.iocaste.workbench;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;
import org.iocaste.workbench.common.Project;
import org.iocaste.workbench.compiler.Compile;
import org.iocaste.workbench.shell.Package;

public class Services extends AbstractFunction {

    public Services() {
        export("compile", "compile");
        export("create_package", "createPackage");
        export("create_project", "createProject");
        export("load", "load");
        export("save", "save");
    }
    
    public final void compile(Message message) throws Exception {
        Project project = message.get("project");
        Context context = new Context();
        
        Compile.execute(project.getName(), context);
    }
    
    public final String createPackage(Message message) {
        String pkgname = message.get("package");
        Context context = new Context();
        
        context.function = this;
        context.projectname = message.get("project");
        return Package.create(pkgname, context);
    }
    
    public final String createProject(Message message) {
        String name = message.get("name");
        Context context = new Context();
        
        context.function = this;
        return org.iocaste.workbench.shell.Project.create(name, context);
    }
    
    public final Project load(Message message) {
        return new Project();
    }
    
    public final void save(Message message) {
//        Save.execute();
    }
}
