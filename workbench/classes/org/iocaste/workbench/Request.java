package org.iocaste.workbench;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;

public class Request {

    public static final void addscreen(Context context) {
        context.view.redirect("screeneditor");
    }
    
    public static final void createproject(Context context) {
        ExtendedObject header;
        ProjectPackage projectpackage;
        String defaultpackage;
        InputComponent input;
        Documents documents;
        Source source;
        
        switch (context.mode) {
        case Context.CREATE0:
            defaultpackage = ((DataForm)context.view.
                    getElement("defaultpackage")).get("NAME").get();
            context.project.defaultpackage = defaultpackage;
            context.project.header.setValue("NAME", context.project.name);
            context.project.header.setValue("PACKAGE", defaultpackage);
            context.project.header.setValue("CLASS", "Main.java");
            context.project.entryclass = defaultpackage.concat(".Main");
            context.project.packages.clear();
            
            source = new Source();
            source.code = getMainTemplate(context);
            
            projectpackage = new ProjectPackage();
            projectpackage.sources.put("Main.java", source);
            context.project.packages.put(defaultpackage, projectpackage);
            
            context.view.dontPushPage();
            context.view.redirect("editor");
            context.mode = Context.CREATE;
            break;
        default:
            input = ((DataForm)context.view.getElement("project")).get("NAME");
            context.project.name = input.get();
            
            documents = new Documents(context.function);
            header = loadProjectHeader(context.project.name, documents);
            if (header != null) {
                context.view.message(Const.ERROR, "project.exists");
                return;
            }
            
            context.view.redirect("defaultpackage");
            context.mode = Context.CREATE0;
            break;
        }
    }
    
    private static final String getMainTemplate(Context context) {
        String[] template = {
                "package " + context.project.defaultpackage + ";\n\n",
                "import org.iocaste.packagetool.common.InstallData;\n",
                "import org.iocaste.protocol.Message;\n",
                "import org.iocaste.shell.common.AbstractPage;\n",
                "import org.iocaste.shell.common.View;\n\n",
                "public class Main extends AbstractPage {\n",
                "   public Main() {\n",
                "      export(\"install\", \"install\");\n",
                "   }\n\n",
                "   public final InstallData main(Message message) {\n",
                "      return new InstallData();\n",
                "   }\n\n",
                "   public final void main(View view) {\n",
                "   }\n",
                "}"};
        StringBuilder sb = new StringBuilder();
        
        for (String line : template)
            sb.append(line);
        
        return sb.toString();
    }
    
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
        packages = documents.select(
                Common.QUERIES[Common.SEL_PACKAGES], context.project.id);
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
        
        sources = documents.select(
                Common.QUERIES[Common.SEL_SOURCES], packageid);
        if (sources == null)
            return;
        
        for (ExtendedObject object_ : sources) {
            sourcename = object_.getValue("NAME");
            if (sourceid == 0)
                context.project.header.setValue("CLASS", sourcename);
            
            sourceid = object_.getl("ID");
            source = new Source();
            package_.sources.put(sourcename, source);
            
            srccode = documents.select(
                    Common.QUERIES[Common.SEL_SRCCODE], sourceid);
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
}

class CodeLineHelper {
    public boolean paragraph;
    public String line;
    public long sourceid, packageid, i;
    public Context context;
    public Documents documents;
}