package org.iocaste.workbench;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.shell.common.Const;

public class ProjectOpen extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
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
        
        extcontext.project = getdfst("head", "PROJECT_ID");
        extcontext.projectdir = new StringBuilder(extcontext.repository).
                append(File.separator).
                append(extcontext.project).toString();
        
        contextfile = new StringBuilder(extcontext.projectdir).
                append(File.separator).append("bin").
                append(File.separator).append("META-INF").
                append(File.separator).append("context.txt").toString();
        
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
        for (String line : lines) {
            args = line.split(":");
            index = Integer.parseInt(args[1]);
            
            if (index == -1)
                continue;
            
            if (index >= 200)
                continue;
            
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
        
        redirect(Main.PRJC);
    }
}
