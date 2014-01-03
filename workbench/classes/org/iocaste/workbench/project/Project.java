package org.iocaste.workbench.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PageControl;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.ProjectPackage;

public class Project {
    
//    public static final void create(Context context) {
//        ExtendedObject header;
//        Documents documents;
//        DataForm form = context.view.getElement("project");
//        
//        context.project.name = form.get("NAME").get();
//        documents = new Documents(context.function);
//        header = loadProjectHeader(context.project.name, documents);
//        if (header != null) {
//            context.view.message(Const.ERROR, "project.exists");
//            return;
//        }
//        
//        context.view.redirect("defaultpackage");
//    }
//    
//    public static final void createDefaultPackage(Context context) {
//        DataForm form = context.view.getElement("defaultpackage");
//        String defaultpackage = form.get("NAME").get();
//        
//        context.project.defaultpackage = defaultpackage;
//        context.project.header.set("NAME", context.project.name);
//        context.project.header.set("PACKAGE", defaultpackage);
//        context.project.packages.clear();
//        context.project.packages.put(defaultpackage, new ProjectPackage());
//        context.view.dontPushPage();
//        context.view.redirect("editor");
//        context.mode = Context.CREATE;
//    }
//    
//    public static final void defaultPackage(Context context) {
//        Form container = new Form(context.view, "main");
//        PageControl pagecontrol = new PageControl(container);
//        DataForm form = new DataForm(container, "defaultpackage");
//        
//        pagecontrol.add("back");
//        form.importModel(context.packagemodel);
//        for (Element element : form.getElements())
//            if (!element.getName().equals("NAME")) {
//                element.setVisible(false);
//            } else {
//                ((InputComponent)element).setObligatory(true);
//                context.view.setFocus(element);
//            }
//        
//        new Button(container, "createdefaultpackage").setSubmit(true);
//        context.view.setTitle("create.default.package");
//    }
//    
//    public static final String getMainTemplate(Context context) {
//        String line;
//        AbstractPage page = (AbstractPage)context.function;
//        InputStream is = page.getResourceAsStream(
//                "/WEB-INF/META-INF/template.txt");
//        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//        StringBuilder sb = new StringBuilder("package ");
//        
//        sb.append(context.project.defaultpackage).append("\n\n");
//        try {
//            while ((line = reader.readLine()) != null)
//                sb.append(line).append('\n');
//            
//            reader.close();
//            is.close();
//        } catch (IOException e) {
//            new RuntimeException(e);
//        }
//        
//        return sb.toString();
//    }
//    
//    /**
//     * 
//     * @param context
//     */
//    public static final void load(Context context) {
////        Query query;
////        ExtendedObject header;
////        ExtendedObject[] packages;
////        Documents documents = new Documents(context.function);
////        DataForm form = context.view.getElement("project");
////        
////        context.project.name = form.get("NAME").get();
////        header = loadProjectHeader(context.project.name, documents);
////        if (header == null) {
////            context.view.message(Const.ERROR, "invalid.project");
////            return;
////        }
////        
////        context.project.header.set("NAME", context.project.name);
////        context.project.id = header.getl("ID");
////        query = new Query();
////        query.setModel("ICSTPRJ_PACKAGES");
////        query.andEqual("PROJECT", context.project.id);
////        packages = documents.select(query);
////        if (packages != null)
////            loadPackages(packages, context, documents);
////        
////        context.view.redirect("editor");
////        context.mode = Context.LOAD;
//    }
//    
//    private static ExtendedObject loadProjectHeader(String project,
//            Documents documents) {
//        ExtendedObject header = documents.getObject("ICSTPRJ_PROJECT_NAMES",
//                project);
//        
//        return (header == null)? null : header;
//    }
//
//    public static final void select(Context context) {
//        InputComponent input;
//        DataForm form;
//        Form container = new Form(context.view, "main");
//        PageControl pagecontrol = new PageControl(container);
//        
//        pagecontrol.add("home");
//        
//        form = new DataForm(container, "project");
//        form.importModel(context.editorhdrmodel);
//        for (Element element : form.getElements())
//            if (!element.getName().equals("NAME")) {
//                element.setVisible(false);
//            } else {
//                input = (InputComponent)element;
//                input.setObligatory(true);
//                context.view.setFocus(input);
//            }
//        
//        new Button(container, "loadproject");
//        new Button(container, "createproject");
//    }
}
