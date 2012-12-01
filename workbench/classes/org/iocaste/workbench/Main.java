package org.iocaste.workbench;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.iocaste.globalconfig.common.GlobalConfig;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.TextArea;
import org.iocaste.shell.common.View;

/**
 * Template par módulo interno do iocaste.
 * @author francisco.prates
 *
 */
public class Main extends AbstractPage {
    private String repository;
    private boolean validrepo;
    private Project project;
    
    public Main() {
        project = new Project();
        export("install", "install");
    }
    
    public final void activate(View view) throws Exception {
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
    
    @Override
    public final void init(View view) {
        File file;
        
        repository = new GlobalConfig(this).get("repository");
        file = new File(repository);
        
        if (file.isDirectory()) {
            validrepo = true;
            return;
        }
        
        validrepo = file.mkdir();
    }
    
    /**
     * @param message
     * @return
     */
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    /**
     * @param view
     */
    public final void main(View view) {
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "project");
        pagecontrol.add("home");
        
        if (!validrepo) {
            view.message(Const.ERROR, "invalid.repository");
            return;
        }
        
        new DataItem(form, Const.TEXT_FIELD, "project").setObligatory(true);
        new DataItem(form, Const.TEXT_FIELD, "package").setObligatory(true);
        new DataItem(form, Const.TEXT_FIELD, "class").setObligatory(true);
        new TextArea(container, "editor");
        new Button(container, "save");
        new Button(container, "activate");
    }
    
    public final void save(View view) throws Exception {
        File file;
        OutputStream os;
        String packagedir;
        DataForm form = view.getElement("project");
        String text = ((TextArea)view.getElement("editor")).get();
        
        if (!project.created) {
            project.name = form.get("project").get();
            project.package_ = form.get("package").get();
            project.class_ = form.get("class").get();
            project.dir = new StringBuilder(repository).
                    append(File.separator).
                    append(project.name).toString();
            
            new File(project.dir).mkdir();
            packagedir = project.package_.replaceAll("[\\.]", File.separator);
            packagedir = new StringBuilder(project.dir).
                    append(File.separator).append(packagedir).toString();
            new File(packagedir).mkdirs();
            
            project.classfile = new StringBuilder(packagedir).
                    append(File.separator).append(project.class_).
                    append(".java").toString();
            
            project.created = true;
        }
        
        file = new File(project.classfile);
        file.createNewFile();
        os = new FileOutputStream(file, false);
        os.write(text.getBytes());
        os.flush();
        os.close();
        
        view.message(Const.STATUS, "project.saved");
    }
}

class Project {
    public String name, package_, class_, dir, classfile;
    public boolean created;
}
