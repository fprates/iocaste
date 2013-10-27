package org.iocaste.workbench.editor;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;
import org.iocaste.shell.common.TextArea;
import org.iocaste.texteditor.common.TextEditor;
import org.iocaste.texteditor.common.TextEditorTool;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.ProjectPackage;
import org.iocaste.workbench.Source;
import org.iocaste.workbench.project.Project;

public class Editor {
    
    /**
     * 
     * @param context
     * @param documents
     * @param projectid
     */
    private static final void registerPackages(Context context,
            Documents documents) {
//        ProjectPackage package_;
//        ExtendedObject object;
//        long packageid;
//
//        packageid = (context.project.id * 1000);
//        for (String packagename : context.project.packages.keySet()) {
//            packageid++;
//            object = new ExtendedObject(context.packagemodel);
//            object.set("ID", packageid);
//            object.set("PROJECT", context.project.id);
//            object.set("NAME", packagename);
//            documents.save(object);
//
//            package_ = context.project.packages.get(packagename);
//            registerSources(context, documents, package_, packageid);
//        }
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
//        ExtendedObject object;
//        String code;
//        String[] codelines;
//        int lines;
//        long sourceid = packageid * 1000;
//        CodeLineHelper codelinehelper = new CodeLineHelper();
//        
//        codelinehelper.context = context;
//        codelinehelper.documents = documents;
//        
//        for (String sourcename : package_.sources.keySet()) {
//            sourceid++;
//            
//            object = new ExtendedObject(context.sourcemodel);
//            object.set("ID", sourceid);
//            object.set("PACKAGE", packageid);
//            object.set("NAME", sourcename);
//            object.set("PROJECT", context.project.id);
//            documents.save(object);
//            
//            code = package_.sources.get(sourcename).code;
//            if (code == null)
//                return;
//            
//            codelinehelper.i = sourceid * 10000;
//            codelinehelper.sourceid = sourceid;
//            codelinehelper.packageid = packageid;
//            
//            codelines = code.split("\r\n");
//            for (String codeline : codelines) {
//                codelinehelper.paragraph = true;
//                codelinehelper.line = codeline;
//
//                lines = codeline.length() / 80;
//                if (lines == 0) {
//                    registerLineCode(codelinehelper, lines);
//                    continue;
//                }
//                
//                for (int l = 0; l < lines; l++) {
//                    registerLineCode(codelinehelper, l);
//                    codelinehelper.paragraph = false;
//                }
//            }
//        }
    }
    
    public static final void render(Context context) {
        ProjectPackage projectpackage;
        DataForm form;
        Source source;
        InputComponent input;
        TextEditor editor;
//        String packagename, sourcename;
        TabbedPane mainpane;
        Container editorcontainer, installcontainer;
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        
        pagecontrol.add("home");
        pagecontrol.add("back");
        pagecontrol.add("save", PageControl.REQUEST);
        pagecontrol.add("activate", PageControl.REQUEST);
        
//        packagename = context.project.header.get("PACKAGE");
//        sourcename = context.project.header.get("CLASS");
//        source = context.project.packages.get(packagename).
//                sources.get(sourcename);
        
        mainpane = new TabbedPane(container, "mainpane");
        editorcontainer = new StandardContainer(mainpane, "editorcontainer");
        form = new DataForm(editorcontainer, "project");
        form.importModel(context.editorhdrmodel);
        form.setObject(context.project.header);
        input = form.get("PACKAGE");
        input.setVisibleLength(60);
        for (Element element : form.getElements())
            element.setEnabled(false);
        
        context.editor = new TextEditorTool(context);
        editor = context.editor.instance(editorcontainer, "editor");
        switch (context.mode) {
        case Context.CREATE:
            context.project.header.set("CLASS", "Main.java");
            context.project.entryclass = context.project.defaultpackage.
                    concat(".Main");
            context.project.currentsource = context.project.entryclass;
            context.editor.set(editor, context.project.currentsource,
                    Project.getMainTemplate(context));
            projectpackage = context.project.packages.
                    get(context.project.defaultpackage);
            source = new Source();
            projectpackage.sources.put(context.project.currentsource, source);
            break;
        }
        
        editor.setLineSize(80);
        
        new TextArea(editorcontainer, "output").setEnabled(false);
        new TabbedPaneItem(mainpane, "source").setContainer(editorcontainer);
        
        installcontainer = new StandardContainer(mainpane, "installcontainer");
        new DataForm(installcontainer, "link").importModel(
                context.installmodel);
        
        new TabbedPaneItem(mainpane, "install").setContainer(installcontainer);
//        context.view.setTitle(Context.TITLES[context.mode]);
    }
    
    public static final void save(Context context) throws Exception {
//        ExtendedObject project, projectname;
//        Documents documents = new Documents(context.function);
//        
//        switch (context.mode) {
//        case Context.CREATE:
//            project = new ExtendedObject(context.projectmodel);
//            context.project.id = documents.getNextNumber("IP_PRJID");
//            project.set("ID", context.project.id);
//            documents.save(project);
//            
//            projectname = new ExtendedObject(context.projectnamemodel);
//            projectname.set("NAME", context.project.name);
//            projectname.set("ID", context.project.id);
//            documents.save(projectname);
//            
//            context.mode = Context.LOAD;
//            break;
//        default:
//            documents.update(Common.QUERIES[Common.DEL_SRCCODE],
//                    context.project.id);
//            documents.update(Common.QUERIES[Common.DEL_SOURCES],
//                    context.project.id);
//            documents.update(Common.QUERIES[Common.DEL_PACKAGES],
//                    context.project.id);
//            break;
//        }
//        
//        registerPackages(context, documents);
    }
}
