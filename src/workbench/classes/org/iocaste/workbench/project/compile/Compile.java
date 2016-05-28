package org.iocaste.workbench.project.compile;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.files.Directory;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class Compile extends AbstractCommand {

    public Compile() {
        required("project");
    }
    
    private final void deployApplication(CompileData data)
            throws Exception {
        Iocaste iocaste;
        Directory war;
        
        war = new Directory(data.project.concat(".war"));
        war.addDir("WEB-INF", "classes");
        war.addDir("WEB-INF", "lib");
        iocaste = new Iocaste(data.context.function);
        iocaste.write("WEBAPPS", war, Iocaste.JAR);
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        CompileData data = new CompileData();
        
        data.project = parameters.get("project");
        data.extcontext = getExtendedContext();
        data.context = context;
        deployApplication(data);
    }

}

class CompileData {
    public String project;
    public Context extcontext;
    public PageBuilderContext context;
}