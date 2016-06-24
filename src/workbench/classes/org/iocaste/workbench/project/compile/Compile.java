package org.iocaste.workbench.project.compile;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.files.Directory;
import org.iocaste.protocol.files.DirectoryInstance;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;

public class Compile extends AbstractCommand {
    private CompileData data;
    private List<ConfigFile> configs;
    
    public Compile(PageBuilderContext context) {
        data = new CompileData();
        configs = new ArrayList<>();
        configs.add(new WebConfigFile(context));
        configs.add(new InstallConfigFile(context));
        configs.add(new ViewConfigFile(context));
    }
    
    private final void deployApplication(CompileData data)
            throws Exception {
        Iocaste iocaste;
        Directory war;
        DirectoryInstance file;
        
        data.entryclass =
                "org.iocaste.workbench.common.engine.ApplicationEngine";
        
        war = new Directory(data.project.concat(".war"));
        war.addDir("META-INF");
        war.addDir("WEB-INF", "classes");
        war.addDir("WEB-INF", "lib");
        
        for (ConfigFile config : configs) {
            config.clear();
            config.run(data);
            config.save(war);
        }
        
        file = war.copy("WEB-INF", "lib", "iocaste-workbench.jar");
        file.source("WORKBENCH_LIBS", "iocaste-workbench.jar");
        file = war.copy("WEB-INF", "lib", "iocaste.jar");
        file.source("WORKBENCH_LIBS", "iocaste.jar");
        
        iocaste = new Iocaste(data.context.function);
        iocaste.write("WEBAPPS", war, Iocaste.JAR);
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        ComplexDocument document;

        data.extcontext = getExtendedContext();
        data.project = parameters.get("project");
        if ((data.project == null) && (data.extcontext.project == null)) {
            message(Const.ERROR, "undefined.project");
            return;
        }
        
        if (data.project == null)
            data.project = data.extcontext.project.getstKey();
        
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
