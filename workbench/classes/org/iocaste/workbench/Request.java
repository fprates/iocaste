package org.iocaste.workbench;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import org.iocaste.documents.common.DocumentModel;
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
    private static final boolean NO_CODE = false;
    private static final boolean WITH_CODE = true;
    private static final byte SEL_PACKAGES = 0;
    private static final byte SEL_SOURCES = 1;
    private static final String[] QUERIES = {
        "from ICSTPRJ_PACKAGES where PROJECT = ?",
        "from ICSTPRJ_SOURCES where PACKAGE = ?"
    };
    
    /**
     * 
     * @param context
     * @throws Exception
     */
    public static final void activate(Context context) throws Exception {
//        String[] errortext;
//        CompilationTask task;
//        Writer writer;
//        StandardJavaFileManager fmngr;
//        File[] files;
//        Iterable<? extends JavaFileObject> cunits;
//        JavaCompiler compiler;
//        
//        if (!project.created) {
//            view.message(Const.ERROR, "project.not.created");
//            return;
//        }
//
//        compiler = ToolProvider.getSystemJavaCompiler();
//        if (compiler == null) {
//            view.message(Const.ERROR, "compiler.unavailable");
//            return;
//        }
//
//        fmngr = compiler.getStandardFileManager(null, view.getLocale(), null);
//        files = new File[1];
//        files[0] = new File(project.classfile);
//        cunits = fmngr.getJavaFileObjects(files);
//        
//        writer = new StringWriter();
//        task = compiler.getTask(writer, fmngr, null, null, null, cunits);
//        if (task.call())
//            view.message(Const.STATUS, "compiling.successful");
//        else {
//            errortext = writer.toString().split("[\n]");
//            view.message(Const.ERROR, errortext[0]);
//        }
//        
//        fmngr.close();
    }

    public static final void addscreen(Context context) {
        context.view.redirect("screeneditor");
    }
    
    public static final void createproject(Context context) {
        ProjectPackage projectpackage;
        String projectname, packagename;
        InputComponent input = ((DataForm)context.view.getElement("project")).
                get("NAME");
        
        context.project = new Project();
        context.project.header = new ExtendedObject(context.editorhdrmodel);
        
        projectname = input.get();
        context.project.header.setValue("NAME", projectname);
        
        packagename = new StringBuilder("org.").append(projectname).toString();
        context.project.header.setValue("PACKAGE", packagename);
        context.project.header.setValue("CLASS", "Main");
        
        projectpackage = new ProjectPackage();
        projectpackage.sources.put("Main", new Source());
        projectpackage.views.put("main", new View(projectname, "main"));
        context.project.packages.put(packagename, projectpackage);
        
        context.view.redirect("editor");
        context.mode = Context.CREATE;
    }
    
    public static final void editscreen(Context context) {
//        project.viewname = ((Parameter)view.getElement("screenname")).get();
//        view.redirect("screeneditor");
    }
    
    public static final void editsource(Context context) {
        String sourcename = ((Parameter)context.view.
                getElement("sourcename")).get();
        String packagename = ((Parameter)context.view.
                getElement("packagename")).get();
        ProjectPackage projectpackage = context.project.packages.
                get(packagename);
        DataForm form = context.view.getElement("project");
        
        form.get("PACKAGE").set(packagename);
        form.get("CLASS").set(sourcename);
        
        loadSource(sourcename, projectpackage, WITH_CODE);
    }
    
    private static final void loadPackages(ExtendedObject[] packages,
            Context context, Documents documents) {
        ExtendedObject[] sources;
        ProjectPackage projectpackage;
        String packagename, sourcename;
        
        for (ExtendedObject object : packages) {
            projectpackage = new ProjectPackage();
            packagename = object.getValue("NAME");
            context.project.packages.put(packagename, projectpackage);
            
            sources = documents.select(QUERIES[SEL_SOURCES], packagename);
            if (sources == null)
                continue;
            
            for (ExtendedObject object_ : sources) {
                sourcename = object_.getValue("NAME");
                loadSource(sourcename, projectpackage, NO_CODE);
            }
        }
    }
    
    public static final void loadproject(Context context) {
        ExtendedObject header;
        ExtendedObject[] packages;
        Documents documents = new Documents(context.function);
        DataForm form = (DataForm)context.view.getElement("project");
        String name = form.get("NAME").get();
        
        header = documents.getObject("ICSTPRJ_HEADER", name);
        if (header == null) {
            context.view.message(Const.ERROR, "invalid.project");
            return;
        }
        
        context.project = new Project();
        context.project.header = new ExtendedObject(context.editorhdrmodel);
        context.project.header.setValue("NAME", name);
        
        packages = documents.select(QUERIES[SEL_PACKAGES], name);
        if (packages != null)
            loadPackages(packages, context, documents);
        
        context.view.redirect("editor");
        context.mode = Context.LOAD;
    }
    
    private static final void loadSource(String sourcename,
            ProjectPackage projectpackage, boolean sourcecode) {
        Source source = new Source();
        
        projectpackage.sources.put(sourcename, source);
        if (!sourcecode)
            return;
    }
    
    private static final ExtendedObject packageObject(String packagename,
            Context context) {
        ExtendedObject object = new ExtendedObject(context.packagemodel);
        
        object.setValue("NAME", packagename);
        object.setValue("PROJECT", context.project.header.getValue("NAME"));
        
        return object;
    }
    
    private static final void registerCodeLine(String codeline, int i,
            String sourceid, NumberFormat formatter, Context context,
            Documents documents) {
        String srccodeid = new StringBuilder(formatter.format(i)).
                append(sourceid).toString();
        ExtendedObject object = new ExtendedObject(context.srccodemodel);
        
        object.setValue("IDENT", srccodeid);
        object.setValue("SOURCE", sourceid);
        object.setValue("LINE", codeline);
        documents.save(object);
    }
    
    private static final void registerPackage(String name, Context context,
            Documents documents) {
        ExtendedObject object;
        int sourceid;
        Map<String, ProjectPackage> packages = context.project.packages;
        
        object = packageObject(name, context);
        documents.save(object);
        
        sourceid = 0;
        for (String sourcename : packages.get(name).sources.keySet())
            registerSource(sourcename, sourceid++, name, context, documents);
            
    }
    
    private static final void registerSource(String name, int i,
            String packagename, Context context, Documents documents) {
        String[] codelines;
        int lines, codelinepos, codelineindex;
        String sourceid, code, partline;
        ExtendedObject object;
        ProjectPackage projectpackage;
        NumberFormat formatter = DecimalFormat.getInstance();
        
        formatter.setMinimumIntegerDigits(3);
        sourceid = new StringBuilder(formatter.format(i)).append(packagename).
                toString();
        
        object = new ExtendedObject(context.sourcemodel);
        object.setValue("IDENT", sourceid);
        object.setValue("PACKAGE", packagename);
        object.setValue("NAME", name);
        documents.save(object);
        
        projectpackage = context.project.packages.get(packagename);
        code = projectpackage.sources.get(name).code;
        if (code == null)
            return;

        formatter.setMinimumIntegerDigits(3);
        codelines = code.split("[\r\n]");
        codelineindex = 0;
        for (String codeline : codelines) {
            lines = codeline.length() / 80;
            for (int l = 0; l < lines; l++) {
                codelineindex++;
                codelinepos = l * 80;
                partline = codeline.substring(codelinepos, codelinepos + 80);
                registerCodeLine(partline, codelineindex, sourceid, formatter,
                        context, documents);
            }
            
            if ((codeline.length() % 80) > 0) {
                codelineindex++;
                registerCodeLine(codeline, codelineindex, sourceid,
                        formatter, context, documents);
            }
        }
    }
    
    public static final void save(Context context) throws Exception {
//        DocumentModel model;
//        File file;
//        OutputStream os;
//        String packagedir, package_, class_;
        String package_, source_, projectname;
        Documents documents;
        DataForm projecthdr = context.view.getElement("project");
        String text = ((TextArea)context.view.getElement("editor")).get();
        ExtendedObject project;
        
        documents = new Documents(context.function);
        projectname = projecthdr.get("NAME").get();
        project = new ExtendedObject(context.projectmodel);
        project.setValue("NAME", projectname);
        
        if (context.mode == Context.LOAD)
            unregisterProject(projectname, context, documents);
            
//            project.name = form.get("NAME").get();
//            package_ = source.getValue("PACKAGE");
//            class_ = source.getValue("CLASS");
//            project.dir = new StringBuilder(repository).
//                    append(File.separator).
//                    append(project.name).toString();
//            
//            new File(project.dir).mkdir();
//            packagedir = package_.replaceAll("[\\.]", File.separator);
//            packagedir = new StringBuilder(project.dir).
//                    append(File.separator).append(packagedir).toString();
//            new File(packagedir).mkdirs();
//            
//            project.classfile = new StringBuilder(packagedir).
//                    append(File.separator).append(class_).
//                    append(".java").toString();
        
        package_ = projecthdr.get("PACKAGE").get();
        source_ = projecthdr.get("CLASS").get();
        context.project.packages.get(package_).sources.get(source_).code = text;
        
        documents.save(project);
        for (String packagename : context.project.packages.keySet())
            registerPackage(packagename, context, documents);
        
        context.mode = Context.LOAD;
//        
//        file = new File(project.classfile);
//        file.createNewFile();
//        os = new FileOutputStream(file, false);
//        os.write(text.getBytes());
//        os.flush();
//        os.close();
    }
    
    /**
     * 
     * @param projectname
     * @param context
     * @param documents
     */
    private static final void unregisterProject(String projectname,
            Context context, Documents documents) {
        ProjectPackage projectpackage;
        ExtendedObject object;
        
        for (String packagename : context.project.packages.keySet()) {
            projectpackage = context.project.packages.get(packagename);
            for (String sourcename : projectpackage.sources.keySet())
                documents.update(
                        "delete from ICSTPRJ_SRCCODE where SOURCE = ?",
                        sourcename);
            
            documents.update(
                    "delete from ICSTPRJ_SOURCES where PACKAGE = ?",
                    packagename);
            object = packageObject(packagename, context);
            documents.delete(object);
        }
        
        documents.update("delete from ICSTPRJ_PACKAGES where PROJECT = ?",
                projectname);
    }
}
