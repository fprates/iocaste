package org.iocaste.workbench;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.TextArea;
import org.iocaste.shell.common.View;

public class Request {
    private static final byte SEL_PACKAGES = 0;
    private static final byte SEL_SOURCES = 1;
    private static final String[] QUERIES = {
        "from ICSTPRJ_PACKAGES where PROJECT = ?",
        "from ICSTPRJ_SOURCES where PACKAGE = ?"
    };

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
        context.project.header.setValue("CLASS", "Main.java");
        
        projectpackage = new ProjectPackage();
        projectpackage.sources.put("Main.java", new Source());
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
        TextArea area;
        String sourcename = ((Parameter)context.view.
                getElement("sourcename")).get();
        String packagename = ((Parameter)context.view.
                getElement("packagename")).get();
        ProjectPackage projectpackage = context.project.packages.
                get(packagename);
        DataForm form = context.view.getElement("project");
        
        form.get("PACKAGE").set(packagename);
        form.get("CLASS").set(sourcename);
        
        loadSource(sourcename, packagename, projectpackage,
                new Documents(context.function));
        
        area = context.view.getElement("editor");
        area.set(projectpackage.sources.get(sourcename).code);
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
                projectpackage.sources.put(sourcename, new Source());
            }
        }
    }
    
    public static final void loadproject(Context context) {
        ExtendedObject header;
        ExtendedObject[] packages;
        Documents documents = new Documents(context.function);
        DataForm form = context.view.getElement("project");
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
    
    private static final void loadSource(String sourcename, String packagename,
            ProjectPackage projectpackage, Documents documents) {
        StringBuilder sb;
        Source source;
        ExtendedObject[] objects = documents.select(
                "from ICSTPRJ_SOURCES where PACKAGE = ? and " +
                "name = ?", packagename, sourcename);
        
        if (objects == null)
            return;
        
        objects = documents.select("from ICSTPRJ_SRCCODE where SOURCE = ?",
                objects[0].getValue("IDENT"));
        
        if (objects == null)
            return;
        
        source = projectpackage.sources.get(sourcename);
        sb = new StringBuilder();
        for (ExtendedObject object : objects) {
            if (sb.length() > 0 &&
                    (boolean)object.getValue("PARAGRAPH") == true)
                sb.append("\r\n");
                
            sb.append(object.getValue("LINE"));
        }
        
        source.code = sb.toString();
    }
    
    private static final ExtendedObject packageObject(String packagename,
            Context context) {
        ExtendedObject object = new ExtendedObject(context.packagemodel);
        
        object.setValue("NAME", packagename);
        object.setValue("PROJECT", context.project.header.getValue("NAME"));
        
        return object;
    }
    
    /**
     * 
     * @param helper
     */
    private static final void registerCodeLine(CodeLineHelper helper) {
        String srccodeid = new StringBuilder(helper.formatter.format(helper.i)).
                append(helper.sourceid).toString();
        ExtendedObject object = new ExtendedObject(helper.context.srccodemodel);
        
        object.setValue("IDENT", srccodeid);
        object.setValue("SOURCE", helper.sourceid);
        object.setValue("PARAGRAPH", helper.paragraph);
        object.setValue("PACKAGE", helper.packagename);
        object.setValue("LINE", helper.codeline);
        helper.documents.save(object);
    }
    
    /**
     * 
     * @param name
     * @param context
     * @param documents
     */
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
    
    /**
     * 
     * @param name
     * @param i
     * @param packagename
     * @param context
     * @param documents
     */
    private static final void registerSource(String name, int i,
            String packagename, Context context, Documents documents) {
        CodeLineHelper codelinehelper;
        String[] codelines;
        int lines, codelinepos;
        String sourceid, code;
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
        
        codelinehelper = new CodeLineHelper();
        codelinehelper.formatter = formatter;
        codelinehelper.context = context;
        codelinehelper.documents = documents;
        codelinehelper.i = 0;
        codelinehelper.sourceid = sourceid;
        codelinehelper.packagename = packagename;
        
        for (String codeline : codelines) {
            lines = codeline.length() / 80;
            codelinehelper.paragraph = true;
            for (int l = 0; l < lines; l++) {
                codelinehelper.i++;
                codelinepos = l * 80;
                
                codelinehelper.codeline = codeline.
                        substring(codelinepos, codelinepos + 80);
                registerCodeLine(codelinehelper);
                codelinehelper.paragraph = false;
            }
            
            if ((codeline.length() % 80) > 0) {
                codelinehelper.i++;
                codelinehelper.paragraph = true;
                codelinehelper.codeline = codeline;
                registerCodeLine(codelinehelper);
            }
        }
    }
    
    public static final void save(Context context) throws Exception {
        String package_, source_, projectname;
        Documents documents;
        DataForm projecthdr = context.view.getElement("project");
        String text = ((TextArea)context.view.getElement("editor")).get();
        ExtendedObject project;
        
        documents = new Documents(context.function);
        projectname = projecthdr.get("NAME").get();
        project = new ExtendedObject(context.projectmodel);
        project.setValue("NAME", projectname);
//        
//        if (context.mode == Context.LOAD)
//            unregisterProject(projectname, context, documents);
//        
        package_ = projecthdr.get("PACKAGE").get();
        source_ = projecthdr.get("CLASS").get();
        context.project.packages.get(package_).sources.get(source_).code = text;
        
        documents.save(project);
//        for (String packagename : context.project.packages.keySet())
//            registerPackage(packagename, context, documents);
//        
//        context.mode = Context.LOAD;
    }
    
    /**
     * 
     * @param projectname
     * @param context
     * @param documents
     */
    private static final void unregisterProject(String projectname,
            Context context, Documents documents) {
        for (String packagename : context.project.packages.keySet()) {
            documents.update("delete from ICSTPRJ_SRCCODE where PACKAGE = ?",
                    packagename);
            documents.update("delete from ICSTPRJ_SOURCES where PACKAGE = ?",
                    packagename);
        }
        
        documents.update("delete from ICSTPRJ_PACKAGES where PROJECT = ?",
                projectname);
    }
}

class CodeLineHelper {
    public boolean paragraph;
    public String codeline, sourceid, packagename;
    public int i;
    public NumberFormat formatter;
    public Context context;
    public Documents documents;
}