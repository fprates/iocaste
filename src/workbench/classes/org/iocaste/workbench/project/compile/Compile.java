package org.iocaste.workbench.project.compile;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.files.Directory;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class Compile extends AbstractCommand {
    private CompileData data;

    public Compile() {
        required("project");
        data = new CompileData();
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
        ComplexDocument document;
        
        data.project = parameters.get("project");
        document = getDocument("project", data.project);
        if (document == null) {
            message(Const.ERROR, "invalid.project");
            return;
        }
        
        data.extcontext = getExtendedContext();
        data.context = context;
        deployApplication(data);
        message(Const.STATUS, "project.compiled");
    }

}

class CompileData {
    public String project;
    public Context extcontext;
    public PageBuilderContext context;
}