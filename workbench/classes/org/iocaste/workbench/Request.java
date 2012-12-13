package org.iocaste.workbench;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.TextArea;
import org.iocaste.shell.common.View;

public class Request {
    
    public static final void activate(View view, Project project)
            throws Exception {
        String[] errortext;
        CompilationTask task;
        Writer writer;
        StandardJavaFileManager fmngr;
        File[] files;
        Iterable<? extends JavaFileObject> cunits;
        JavaCompiler compiler;
        
        if (!project.created) {
            view.message(Const.ERROR, "project.not.created");
            return;
        }

        compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            view.message(Const.ERROR, "compiler.unavailable");
            return;
        }

        fmngr = compiler.getStandardFileManager(null, view.getLocale(), null);
        files = new File[1];
        files[0] = new File(project.classfile);
        cunits = fmngr.getJavaFileObjects(files);
        
        writer = new StringWriter();
        task = compiler.getTask(writer, fmngr, null, null, null, cunits);
        if (task.call())
            view.message(Const.STATUS, "compiling.successful");
        else {
            errortext = writer.toString().split("[\n]");
            view.message(Const.ERROR, errortext[0]);
        }
        
        fmngr.close();
    }

    public static final void addscreen(View view) {
        view.redirect("screeneditor");
    }
    
    public static final Project createproject(View view, Function function) {
        View pview;
        Source source;
        String package_;
        Project project = new Project();
        InputComponent input = ((DataForm)view.getElement("project")).
                get("name");
        
        project.name = input.get();
        pview = new View(project.name, "main");
        project.views.put("main", pview);
        project.model = new Documents(function).getModel("PROJECT_HEADER");
        package_ = new StringBuilder("org.").append(project.name).toString();
        project.source = new StringBuilder(package_).append(".Main").toString();
        
        source = new Source();
        source.header = new ExtendedObject(project.model);
        source.header.setValue("NAME", project.name);
        source.header.setValue("PACKAGE", package_);
        source.header.setValue("CLASS", "Main");
        project.sources.put(project.source, source);
        
        view.redirect("editor");
        
        return project;
    }
    
    public static final void editscreen(View view, Project project) {
        project.viewname = ((Parameter)view.getElement("screenname")).get();
        view.redirect("screeneditor");
    }
    
    public static final void save(View view, String repository, Project project)
            throws Exception {
        File file;
        OutputStream os;
        String packagedir, package_, class_;
        DataForm form = view.getElement("project");
        String text = ((TextArea)view.getElement("editor")).get();
        ExtendedObject source = form.getObject();
        
        if (!project.created) {
            project.name = form.get("project").get();
            package_ = source.getValue("PACKAGE");
            class_ = source.getValue("CLASS");
            project.dir = new StringBuilder(repository).
                    append(File.separator).
                    append(project.name).toString();
            
            new File(project.dir).mkdir();
            packagedir = package_.replaceAll("[\\.]", File.separator);
            packagedir = new StringBuilder(project.dir).
                    append(File.separator).append(packagedir).toString();
            new File(packagedir).mkdirs();
            
            project.classfile = new StringBuilder(packagedir).
                    append(File.separator).append(class_).
                    append(".java").toString();
            
            project.created = true;
        }
        
        file = new File(project.classfile);
        file.createNewFile();
        os = new FileOutputStream(file, false);
        os.write(text.getBytes());
        os.flush();
        os.close();
    }
}
