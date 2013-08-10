package org.iocaste.workbench;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.NodeList;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.TextArea;
import org.iocaste.shell.common.View;

public class Response {

    public static final void editor(Context context) {
//        Map<String, String> css;
//        Container actionpane, navpane, fontpane;
        DataForm form;
//        Link screenlink, sourcelink;
//        Parameter screenname, sourcename, packagename;
//        ProjectPackage projectpackage;
//        NodeList objlist, screenlist, commandlist, packagelist, sourcelist;
//        NodeList actionlist;
        InputComponent input;
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        
        pagecontrol.add("home");
        pagecontrol.add("back");
//        
//        if (!context.validrepo) {
//            context.view.message(Const.ERROR, "invalid.repository");
//            return;
//        }
//        
//        css = new HashMap<>();
//        css.put("float", "left");
//        context.view.getStyleSheet().put(".pane", css);
//        
//        css = new HashMap<>();
//        css.put("list-style-type", "none");
//        context.view.getStyleSheet().put(".objlist", css);
//        
//        actionpane = new StandardContainer(container, "actionpane");
//        actionpane.setStyleClass("pane");
//        actionlist = new NodeList(actionpane, "actionlist");
//        actionlist.setStyleClass("objlist");
//        new Button(actionlist, "save");
//        new Button(actionlist, "activate");
//        
//        navpane = new StandardContainer(container, "navpane");
//        navpane.setStyleClass("pane");
//        
//        objlist = new NodeList(navpane, "objlist");
////        
////        screenlist = new NodeList(objlist, "screenlist");
////        screenlist.setListType(NodeList.DEFINITION);
////        screenname = new Parameter(screenlist, "screenname");
////        for (String name : project.views.keySet()) {
////            screenlink = new Link(screenlist, name, "editscreen");
////            screenlink.add(screenname, name);
////        }
////        
////        new Button(screenlist, "addscreen");
////        
////        commandlist = new NodeList(objlist, "commandlist");
////        commandlist.setListType(NodeList.DEFINITION);
//
//        sourcename = new Parameter(container, "sourcename");
//        packagename = new Parameter(container, "packagename");
//        packagelist = new NodeList(objlist, "packagelist");
//        packagelist.setListType(NodeList.DEFINITION);
//        for (String package_ : context.project.packages.keySet()) {
//            projectpackage = context.project.packages.get(package_);
//            sourcelist = new NodeList(packagelist, package_);
//            sourcelist.setListType(NodeList.DEFINITION);
//            for (String name : projectpackage.sources.keySet()) {
//                sourcelink = new Link(sourcelist, name, "editsource");
//                sourcelink.add(sourcename, name);
//                sourcelink.add(packagename, package_);
//            }
//        }
//        
//        fontpane = new StandardContainer(container, "fontpane");    
//        fontpane.setStyleClass("pane");
//        form = new DataForm(fontpane, "project");
        form = new DataForm(container, "project");
        form.importModel(context.editorhdrmodel);
        form.setObject(context.project.header);
        
        input = form.get("PACKAGE");
        input.setVisibleLength(60);
        for (Element element : form.getElements())
            element.setEnabled(false);
        
//        new TextArea(fontpane, "editor");
        pagecontrol.add("save", PageControl.REQUEST);
        pagecontrol.add("activate", PageControl.REQUEST);
        new TextArea(container, "editor").setSize(80, 20);
        new TextArea(container, "output").setEnabled(false);
        
//        context.view.setTitle(Context.TITLES[context.mode]);
    }
    
    public static final void main(Context context) {
        InputComponent input;
        DataForm form;
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        
        pagecontrol.add("home");
        
        form = new DataForm(container, "project");
        form.importModel(context.editorhdrmodel);
        for (Element element : form.getElements())
            if (!element.getName().equals("NAME")) {
                element.setVisible(false);
            } else {
                input = (InputComponent)element;
                input.setObligatory(true);
                context.view.setFocus(input);
             }
        
        new Button(container, "loadproject");
        new Button(container, "createproject");
    }
    
    /**
     * 
     * @param view
     * @param project
     */
    public static final void screeneditor(Context context) {
//        InputComponent item;
//        Form container = new Form(view, "main");
//        PageControl pagecontrol = new PageControl(container);
//        DataForm form = new DataForm(container, "screen");
//        
//        pagecontrol.add("back");
//        
//        item = new DataItem(form, Const.TEXT_FIELD, "name");
//        item.set(project.viewname);
//        item.setEnabled(false);
    }
}
