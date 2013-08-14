package org.iocaste.workbench;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;

public class Request {
    private static final byte SEL_PACKAGES = 0;
    private static final byte SEL_SOURCES = 1;
    private static final byte SEL_SRCCODE = 2;
    private static final byte DEL_SRCCODE = 3;
    private static final byte DEL_SOURCES = 4;
    private static final byte DEL_PACKAGES = 5;
    private static final String[] QUERIES = {
        "from ICSTPRJ_PACKAGES where PROJECT = ?",
        "from ICSTPRJ_SOURCES where PACKAGE = ?",
        "from ICSTPRJ_SRCCODE where SOURCE = ?",
        "delete from ICSTPRJ_SRCCODE where PROJECT = ?",
        "delete from ICSTPRJ_SOURCES where PROJECT = ?",
        "delete from ICSTPRJ_PACKAGES where PROJECT = ?"
    };

    public static final void addscreen(Context context) {
        context.view.redirect("screeneditor");
    }
    
    public static final void createproject(Context context) {
        ExtendedObject header;
        ProjectPackage projectpackage;
        String packagename;
        InputComponent input = ((DataForm)context.view.getElement("project")).
                get("NAME");
        Documents documents = new Documents(context.function);
        
        context.project.name = input.get();
        header = loadProjectHeader(context.project.name, documents);
        if (header != null) {
            context.view.message(Const.ERROR, "project.exists");
            return;
        }
        
        context.project.header.setValue("NAME", context.project.name);
        
        packagename = new StringBuilder("org.").append(context.project.name).
                toString();
        context.project.header.setValue("PACKAGE", packagename);
        context.project.header.setValue("CLASS", "Main.java");
        context.project.entryclass = packagename.concat(".Main");
        context.project.packages.clear();
        
        projectpackage = new ProjectPackage();
        projectpackage.sources.put("Main.java", new Source());
        context.project.packages.put(packagename, projectpackage);
        
        context.view.redirect("editor");
        context.mode = Context.CREATE;
    }
    
    public static final void editscreen(Context context) {
//        project.viewname = ((Parameter)view.getElement("screenname")).get();
//        view.redirect("screeneditor");
    }
    
//    public static final void editsource(Context context) {
//        TextArea area;
//        String sourcename = ((Parameter)context.view.
//                getElement("sourcename")).get();
//        String packagename = ((Parameter)context.view.
//                getElement("packagename")).get();
//        ProjectPackage projectpackage = context.project.packages.
//                get(packagename);
//        DataForm form = context.view.getElement("project");
//        
//        form.get("PACKAGE").set(packagename);
//        form.get("CLASS").set(sourcename);
//        
//        loadSource(sourcename, packagename, projectpackage,
//                new Documents(context.function));
//        
//        area = context.view.getElement("editor");
//        area.set(projectpackage.sources.get(sourcename).code);
//    }
    
    /**
     * 
     * @param packages
     * @param context
     * @param documents
     */
    private static final void loadPackages(ExtendedObject[] packages,
            Context context, Documents documents) {
        ProjectPackage package_;
        String packagename;
        long packageid = 0;
        
        context.project.packages.clear();
        for (ExtendedObject object : packages) {
            package_ = new ProjectPackage();
            packagename = object.getValue("NAME");
            if (packageid == 0) {
                context.project.header.setValue("PACKAGE", packagename);
                context.project.entryclass = packagename.concat(".Main");
            }
            
            packageid = object.getl("ID");
            context.project.packages.put(packagename, package_);
            
            loadSource(packageid, documents, context, package_);
        }
    }
    
    /**
     * 
     * @param context
     */
    public static final void loadproject(Context context) {
        ExtendedObject header;
        ExtendedObject[] packages;
        Documents documents = new Documents(context.function);
        DataForm form = context.view.getElement("project");
        
        context.project.name = form.get("NAME").get();
        header = loadProjectHeader(context.project.name, documents);
        if (header == null) {
            context.view.message(Const.ERROR, "invalid.project");
            return;
        }
        
        context.project.header.setValue("NAME", context.project.name);
        context.project.id = header.getl("ID");
        packages = documents.select(QUERIES[SEL_PACKAGES], context.project.id);
        if (packages != null)
            loadPackages(packages, context, documents);
        
        context.view.redirect("editor");
        context.mode = Context.LOAD;
    }
    
    private static ExtendedObject loadProjectHeader(String project,
            Documents documents) {
        ExtendedObject header = documents.getObject("ICSTPRJ_PROJECT_NAMES",
                project);
        
        return (header == null)? null : header;
    }
    
    /**
     * 
     * @param packageid
     * @param documents
     * @param context
     * @param package_
     */
    private static final void loadSource(long packageid, Documents documents,
            Context context, ProjectPackage package_) {
        ExtendedObject[] sources, srccode;
        String sourcename;
        StringBuilder code;
        Source source;
        boolean paragraph;
        long sourceid = 0;
        
        sources = documents.select(QUERIES[SEL_SOURCES], packageid);
        if (sources == null)
            return;
        
        for (ExtendedObject object_ : sources) {
            sourcename = object_.getValue("NAME");
            if (sourceid == 0)
                context.project.header.setValue("CLASS", sourcename);
            
            sourceid = object_.getl("ID");
            source = new Source();
            package_.sources.put(sourcename, source);
            
            srccode = documents.select(QUERIES[SEL_SRCCODE], sourceid);
            if (srccode == null)
                continue;
            
            code = new StringBuilder();
            for (ExtendedObject linecode : srccode) {
                paragraph = linecode.getValue("PARAGRAPH");
                if (code.length() > 0 && paragraph == true)
                    code.append("\r\n");
                    
                code.append(linecode.getValue("LINE"));
            }

            source.code = code.toString();
        }
    }
    
    private static final void registerLineCode(CodeLineHelper helper, int pos) {
        ExtendedObject object;
        String codeline;
        int linepos = pos * 80;
        
        if (helper.line.length() < 80)
            codeline = helper.line.substring(linepos);
        else
            codeline = helper.line.substring(linepos, linepos + 80);
        
        helper.i++;
        object = new ExtendedObject(helper.context.srccodemodel);
        object.setValue("ID", helper.i);
        object.setValue("SOURCE", helper.sourceid);
        object.setValue("PARAGRAPH", helper.paragraph);
        object.setValue("PACKAGE", helper.packageid);
        object.setValue("LINE", codeline);
        object.setValue("PROJECT", helper.context.project.id);
        helper.documents.save(object);
    }
    
    /**
     * 
     * @param context
     * @param documents
     * @param projectid
     */
    private static final void registerPackages(Context context,
            Documents documents) {
        ProjectPackage package_;
        ExtendedObject object;
        long packageid;

        packageid = (context.project.id * 1000);
        for (String packagename : context.project.packages.keySet()) {
            packageid++;
            object = new ExtendedObject(context.packagemodel);
            object.setValue("ID", packageid);
            object.setValue("PROJECT", context.project.id);
            object.setValue("NAME", packagename);
            documents.save(object);

            package_ = context.project.packages.get(packagename);
            registerSources(context, documents, package_, packageid);
        }
            
    }
    
    /**
     * 
     * @param context
     * @param documents
     * @param package_
     * @param packageid
     */
    private static final void registerSources(Context context,
            Documents documents, ProjectPackage package_, long packageid) {
        ExtendedObject object;
        String code;
        String[] codelines;
        int lines;
        long sourceid = packageid * 1000;
        CodeLineHelper codelinehelper = new CodeLineHelper();
        
        codelinehelper.context = context;
        codelinehelper.documents = documents;
        
        for (String sourcename : package_.sources.keySet()) {
            sourceid++;
            
            object = new ExtendedObject(context.sourcemodel);
            object.setValue("ID", sourceid);
            object.setValue("PACKAGE", packageid);
            object.setValue("NAME", sourcename);
            object.setValue("PROJECT", context.project.id);
            documents.save(object);
            
            code = package_.sources.get(sourcename).code;
            if (code == null)
                return;
            
            codelinehelper.i = sourceid * 10000;
            codelinehelper.sourceid = sourceid;
            codelinehelper.packageid = packageid;
            
            codelines = code.split("\r\n");
            for (String codeline : codelines) {
                codelinehelper.paragraph = true;
                codelinehelper.line = codeline;

                lines = codeline.length() / 80;
                if (lines == 0) {
                    registerLineCode(codelinehelper, lines);
                    continue;
                }
                
                for (int l = 0; l < lines; l++) {
                    registerLineCode(codelinehelper, l);
                    codelinehelper.paragraph = false;
                }
            }
        }
    }
    
    public static final void save(Context context) throws Exception {
        ExtendedObject project, projectname;
        Documents documents = new Documents(context.function);
        
        switch (context.mode) {
        case Context.CREATE:
            project = new ExtendedObject(context.projectmodel);
            context.project.id = documents.getNextNumber("IP_PRJID");
            project.setValue("ID", context.project.id);
            documents.save(project);
            
            projectname = new ExtendedObject(context.projectnamemodel);
            projectname.setValue("NAME", context.project.name);
            projectname.setValue("ID", context.project.id);
            documents.save(projectname);
            
            context.mode = Context.LOAD;
            break;
        default:
            unregisterPackages(context, documents);
            break;
        }
        
        registerPackages(context, documents);
    }
    
    /**
     * 
     * @param context
     * @param documents
     */
    private static final void unregisterPackages(Context context,
            Documents documents) {
        documents.update(QUERIES[DEL_SRCCODE], context.project.id);
        documents.update(QUERIES[DEL_SOURCES], context.project.id);
        documents.update(QUERIES[DEL_PACKAGES], context.project.id);
    }
}

class CodeLineHelper {
    public boolean paragraph;
    public String line;
    public long sourceid, packageid, i;
    public Context context;
    public Documents documents;
}