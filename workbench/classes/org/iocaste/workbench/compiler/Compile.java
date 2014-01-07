package org.iocaste.workbench.compiler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.texteditor.common.TextEditorTool;
import org.iocaste.workbench.Common;
import org.iocaste.workbench.Context;

public class Compile {
    
    private static final void createProjectFiles(Context context)
            throws IOException {
        String[] tokens;
        OutputStream os;
        File file;
        String dir, code;
        long packageid, sourceid;
        ExtendedObject source;
        StringBuilder bindir;
        Map<Long, String> sources;
        ExtendedObject[] packages;
        TextEditorTool tetool = new TextEditorTool(context);
        
        file = new File(context.repository);
        if (!file.exists())
            file.mkdir();
        
        removeCompleteDir(context.projectdir);
        new File(context.projectdir).mkdir();
        bindir = new StringBuilder(context.projectdir);
        for (String dirname : new String[] {"bin", "WEB-INF", "classes"})
            new File(bindir.append(File.separator).
                    append(dirname).toString()).mkdir();
        
        packages = Common.getPackages(context.project, context);
        for (ExtendedObject package_ : packages) {
            dir = package_.getst("PACKAGE_NAME");
            dir = dir.replaceAll("[\\.]", File.separator);
            dir = Common.composeFileName(context.projectdir, "src", dir);
            new File(dir).mkdirs();
                
            packageid = package_.getl("PACKAGE_ID");
            context.sources = Common.getSources(packageid, context);
            for (String sourcename : context.sources.keySet()) {
                source = context.sources.get(sourcename);
                if (packageid != source.getl("PACKAGE_ID"))
                    continue;
                
                sourceid = source.getl("SOURCE_ID");
                sources = tetool.get(context.sourceobj, sourceid);
                code = sources.get(sourceid);
                
                tokens = sourcename.split("\\.");
                sourcename = tokens[tokens.length - 1];
                sourcename = Common.composeFileName(dir, sourcename);
                file = new File(sourcename.concat(".java"));
                file.createNewFile();
                os = new FileOutputStream(file, false);
                os.write(code.getBytes());
                os.flush();
                os.close();
            }
        }
    }

    public static final String execute(String project, Context context)
            throws Exception {
        ExtendedObject object;
        context.project = project;
        
        object = Common.getProject(project, context);
        if (object == null)
            return "invalid.project";
        
        context.sourceobj = object.get("SOURCE_OBJ");
        createProjectFiles(context);
        
        return "project.compiled";
    }
    
    private static final void removeCompleteDir(String dir) {
        File origin = new File(dir);
        File[] files = origin.listFiles();
        
        if (files != null)
            for (File file : files) {
                if (file.isDirectory())
                    removeCompleteDir(file.getAbsolutePath());
                file.delete();
            }
        
        origin.delete();
    }
}
