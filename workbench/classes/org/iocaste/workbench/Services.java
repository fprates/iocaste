package org.iocaste.workbench;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;
import org.iocaste.texteditor.common.TextEditorTool;
import org.iocaste.workbench.common.Project;
import org.iocaste.workbench.common.Source;
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
        return Common.createProject(name, context);
    }
    
    public final Project load(Message message) {
        return new Project();
    }
    
    public final String save(Message message) {
        long packageid, sourceid;
        int linesize;
        ExtendedObject object;
        String sourceobj;
        Source source = message.get("source");
        Context context = new Context();
        
        context.function = this;
        context.projectname = source.getProject();
        object = Common.getProject(context.projectname, context);
        if (object == null)
            return "invalid.project";
        
        packageid = object.getl("PROJECT_ID");
        sourceid = object.getl("SOURCE_ID");
        sourceobj = object.get("SOURCE_OBJ");

        context.projectdefsource = (source.isDefault())? "true" : null;
        context.projectfullsourcename = source.getName();
        context.projectsources = Common.getSources(packageid, context);
        if (!context.projectsources.containsKey(context.projectfullsourcename)) {
            object = Common.getSourceInstance(context);
            context.editormode = Context.NEW;
            context.projectsources.put(context.projectfullsourcename, object);
            sourceid++;
        } else {
            context.editormode = Context.EDIT;
            object = context.projectsources.get(context.projectfullsourcename);
            sourceid = object.getl("SOURCE_ID");
        }
        
        Common.register(context);
        linesize = message.geti("line_size");
        context.tetool = new TextEditorTool(context);
        context.tetool.update(sourceobj, sourceid, source.getCode(), linesize);
        return null;
    }
}
