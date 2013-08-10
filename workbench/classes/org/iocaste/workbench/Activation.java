package org.iocaste.workbench;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.View;

public class Activation {
    
    private static final void buildWebArchive() {
        
    }
    
    private static final void compileProject(Context context) throws Exception {
        String prefix;
        StringBuilder cp;
        ProjectPackage package_;
        Source source;
        CompilationTask task;
        Writer writer;
        StandardJavaFileManager fmngr;
        List<File> files;
        List<String> options;
        Iterable<? extends JavaFileObject> cunits;
        JavaCompiler compiler;
        InputComponent input;
        File file;
        View view = context.view;
//      
//      if (!project.created) {
//          view.message(Const.ERROR, "project.not.created");
//          return;
//      }
//
        compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            view.message(Const.ERROR, "compiler.unavailable");
            return;
        }
        
        files = new ArrayList<>();
        for (String packagename : context.project.packages.keySet()) {
            package_ = context.project.packages.get(packagename);
            for (String sourcename : package_.sources.keySet()) {
                source = package_.sources.get(sourcename);
                files.add(new File(source.filename));
            }
        }

        fmngr = compiler.getStandardFileManager(null, view.getLocale(), null);
        cunits = fmngr.getJavaFileObjects(files.toArray(new File[0])); 
        prefix = new StringBuilder(context.path).append("/WEB-INF/lib/").
                toString();
        file = new File(prefix);
        cp = new StringBuilder();
        for (String filename : file.list(new JarFilter())) {
            if (cp.length() > 0)
                cp.append(":");
        
            cp.append(prefix).append(filename);
        }
        
        options = new ArrayList<>();
        options.addAll(Arrays.asList("-cp", cp.toString()));
        input = context.view.getElement("output");     
        writer = new StringWriter();
        task = compiler.getTask(writer, fmngr, null, options, null, cunits);
        if (task.call()) {
            input.set(null);
            view.message(Const.STATUS, "compiling.successful");
        } else {
            input.set(writer.toString());
            view.message(Const.ERROR, "compiling.error");
        }
      
        fmngr.close();
    }
    
    private static final void createProjectFiles(Context context)
            throws IOException {
        OutputStream os;
        File file;
        String dir;
        ProjectPackage package_;
        Source source;
        DataForm form = context.view.getElement("project");
        String projectname = form.get("NAME").get();
        
        if (context.project.dir == null)
            context.project.dir = new StringBuilder(context.repository).
                  append(File.separator).append(projectname).toString();
        
        new File(context.project.dir).mkdir();
        
        for (String packagename : context.project.packages.keySet()) {
            dir = packagename.replaceAll("[\\.]", File.separator);
            dir = new StringBuilder(context.project.dir).
                    append(File.separator).append(dir).toString();
            new File(dir).mkdirs();
            
            package_ = context.project.packages.get(packagename);
            for (String sourcename : package_.sources.keySet()) {
                source = package_.sources.get(sourcename);                
                source.filename = new StringBuilder(dir).
                        append(File.separator).append(sourcename).toString();
                
                file = new File(source.filename);
                file.createNewFile();
                os = new FileOutputStream(file, false);
                os.write(source.code.getBytes());
                os.flush();
                os.close();
            }
        }
    }
    
    private static final void deployApplication() {
        
    }

    public static final void start(Context context) throws Exception {
        updateCurrentSource(context);
        createProjectFiles(context);
        compileProject(context);
        buildWebArchive();
        deployApplication();        
    }
    
    private static final void updateCurrentSource(Context context) {
        ExtendedObject header = context.project.header;
        InputComponent input = context.view.getElement("editor");
        ProjectPackage package_ = context.project.packages.get(
                header.getValue("PACKAGE"));
        Source source = package_.sources.get(header.getValue("CLASS"));
        
        source.code = input.get(); 
    }
}

class JarFilter implements FilenameFilter {

    @Override
    public boolean accept(File arg0, String name) {
        return name.toLowerCase().endsWith(".jar");
    }
    
}