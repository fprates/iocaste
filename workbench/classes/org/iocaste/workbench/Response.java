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
        Map<String, Map<String, String>> css;
        Map<String, String> csspane;
        Container navpane, fontpane;
        DataForm form;
        Link screenlink, sourcelink;
        Parameter screenname, sourcename, packagename;
        ProjectPackage projectpackage;
        NodeList objlist, screenlist, commandlist, packagelist, sourcelist;
        InputComponent input;
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        
        pagecontrol.add("home");
        pagecontrol.add("back");
        
        if (!context.validrepo) {
            context.view.message(Const.ERROR, "invalid.repository");
            return;
        }
        
        css = context.view.getStyleSheet();
        csspane = new HashMap<>();
        csspane.put("float", "left");
        css.put(".pane", csspane);
        
        navpane = new StandardContainer(container, "navpane");
        navpane.setStyleClass("pane");
        objlist = new NodeList(navpane, "objlist");
//        
//        screenlist = new NodeList(objlist, "screenlist");
//        screenlist.setListType(NodeList.DEFINITION);
//        screenname = new Parameter(screenlist, "screenname");
//        for (String name : project.views.keySet()) {
//            screenlink = new Link(screenlist, name, "editscreen");
//            screenlink.add(screenname, name);
//        }
//        
//        new Button(screenlist, "addscreen");
//        
//        commandlist = new NodeList(objlist, "commandlist");
//        commandlist.setListType(NodeList.DEFINITION);

        sourcename = new Parameter(container, "sourcename");
        packagename = new Parameter(container, "packagename");
        packagelist = new NodeList(objlist, "packagelist");
        packagelist.setListType(NodeList.DEFINITION);
        for (String package_ : context.project.packages.keySet()) {
            projectpackage = context.project.packages.get(package_);
            sourcelist = new NodeList(packagelist, package_);
            sourcelist.setListType(NodeList.DEFINITION);
            for (String name : projectpackage.sources.keySet()) {
                sourcelink = new Link(sourcelist, name, "editsource");
                sourcelink.add(sourcename, name);
                sourcelink.add(packagename, package_);
            }
        }
        
        fontpane = new StandardContainer(container, "fontpane");    
        fontpane.setStyleClass("pane");
        form = new DataForm(fontpane, "project");
        form.importModel(context.editorhdrmodel);
        form.setObject(context.project.header);
        
        input = form.get("PACKAGE");
        input.setVisibleLength(60);
        for (Element element : form.getElements())
            element.setEnabled(false);
        
        new TextArea(fontpane, "editor");
        
        new Button(container, "save");
//        new Button(fontpane, "activate");
        
        context.view.setTitle(Context.TITLES[context.mode]);
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
