package org.iocaste.workbench;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.TextArea;

public class Editor {
    
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
    
    public static final void render(Context context) {
        DataForm form;
        Source source;
        InputComponent input;
        TextArea editor;
        String packagename, sourcename;
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        
        pagecontrol.add("home");
        pagecontrol.add("back");
        
        form = new DataForm(container, "project");
        form.importModel(context.editorhdrmodel);
        form.setObject(context.project.header);
        
        input = form.get("PACKAGE");
        input.setVisibleLength(60);
        for (Element element : form.getElements())
            element.setEnabled(false);
        
        pagecontrol.add("save", PageControl.REQUEST);
        pagecontrol.add("activate", PageControl.REQUEST);
        
        packagename = context.project.header.getValue("PACKAGE");
        sourcename = context.project.header.getValue("CLASS");
        source = context.project.packages.get(packagename).
                sources.get(sourcename);
        editor = new TextArea(container, "editor");
        editor.setSize(80, 20);
        editor.set(source.code);
        context.view.setFocus(editor);
        
        new TextArea(container, "output").setEnabled(false);
        
//        context.view.setTitle(Context.TITLES[context.mode]);
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
            documents.update(Common.QUERIES[Common.DEL_SRCCODE],
                    context.project.id);
            documents.update(Common.QUERIES[Common.DEL_SOURCES],
                    context.project.id);
            documents.update(Common.QUERIES[Common.DEL_PACKAGES],
                    context.project.id);
            break;
        }
        
        registerPackages(context, documents);
    }
}
