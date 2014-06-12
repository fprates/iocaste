package org.iocaste.workbench;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;

public class ProjectOpen extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        List<ExtendedObject> links;
        ExtendedObject object;
        ProjectView projectview;
        ViewSpecItem.TYPES[] types;
        ViewSpecItem.TYPES type;
        int index;
        byte[] buffer;
        InputStream is;
        String contextfile, name, container;
        File file;
        String[] lines, args;
        Context extcontext = getExtendedContext();
        DocumentModel tasks = new Documents(context.function).getModel("TASKS");
        
        extcontext.views.clear();
        extcontext.project = getdfst("head", "PROJECT_ID");
        extcontext.projectdir = Common.composeFileName(
                extcontext.repository, extcontext.project);
        
        contextfile = Common.composeFileName(
                extcontext.projectdir, "bin", "META-INF", "context.txt");
        
        file = new File(contextfile);
        if (!file.exists()) {
            message(Const.ERROR, "project.doesnt.exist");
            return;
        }
        
        buffer = new byte[((Number)file.length()).intValue()];
        is = new FileInputStream(file);
        is.read(buffer);
        is.close();
        
        types = ViewSpecItem.TYPES.values();
        lines = new String(buffer).split("\n");
        projectview = null;
        links = new ArrayList<>();
        for (String line : lines) {
            args = line.split(":");
            index = Integer.parseInt(args[1]);
            
            if (index == -1)
                continue;
            
            if (index >= 200) {
                object = new ExtendedObject(tasks);
                object.set("NAME", args[0]);
                object.set("COMMAND", args[2]);
                links.add(object);
                continue;
            }
            
            type = types[index];
            switch (type) {
            case VIEW:
                projectview = ObjectProjectCreate.now(extcontext);
                break;
            default:
                args = args[0].split("\\.");
                name = args[args.length - 1];
                container = args[args.length - 2];                
                projectview.add(type.toString(), container, name);
                break;
            }
        }
        
        extcontext.view = null;
        extcontext.links = (links.size() == 0)?
                null : links.toArray(new ExtendedObject[0]);
        extcontext.hideAll();
        redirect(Main.PRJC);
    }
}
