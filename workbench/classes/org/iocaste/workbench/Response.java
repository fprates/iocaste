package org.iocaste.workbench;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.NodeList;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.TextArea;
import org.iocaste.shell.common.View;

public class Response {

    public static final void editor(View view, boolean validrepo,
            Project project) {
        Map<String, Map<String, String>> css;
        Map<String, String> csspane;
        Container navpane, fontpane;
        DataForm form;
        Link screenlink;
        Parameter screenname;
        NodeList objlist, screenlist, commandlist;
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        
        pagecontrol.add("home");
        pagecontrol.add("back");
        
        if (!validrepo) {
            view.message(Const.ERROR, "invalid.repository");
            return;
        }
        
        css = view.getStyleSheet();
        csspane = new HashMap<>();
        csspane.put("float", "left");
        css.put(".pane", csspane);
        
        navpane = new StandardContainer(container, "navpane");
        navpane.setStyleClass("pane");
        objlist = new NodeList(navpane, "objlist");
        
        screenlist = new NodeList(objlist, "screenlist");
        screenlist.setListType(NodeList.DEFINITION);
        screenname = new Parameter(screenlist, "screenname");
        for (String name : project.views.keySet()) {
            screenlink = new Link(screenlist, name, "editscreen");
            screenlink.add(screenname, name);
        }
        
        new Button(screenlist, "addscreen");
        
        commandlist = new NodeList(objlist, "commandlist");
        commandlist.setListType(NodeList.DEFINITION);
        
        fontpane = new StandardContainer(container, "fontpane");    
        fontpane.setStyleClass("pane");
        form = new DataForm(fontpane, "project");
        form.importModel(project.model);
        form.setObject(project.sources.get(project.source).header);
        
        new TextArea(fontpane, "editor");
        new Button(fontpane, "save");
        new Button(fontpane, "activate");
    }
    
    public static final void main(View view) {
        DataForm form;
        InputComponent input;
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        
        pagecontrol.add("home");
        
        form = new DataForm(container, "project");
        input = new DataItem(form, Const.TEXT_FIELD, "name");
        input.setObligatory(true);
        
        view.setFocus(input);
        
        new Button(container, "createproject");
    }
    
    /**
     * 
     * @param view
     * @param project
     */
    public static final void screeneditor(View view, Project project) {
        InputComponent item;
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "screen");
        
        pagecontrol.add("back");
        
        item = new DataItem(form, Const.TEXT_FIELD, "name");
        item.set(project.viewname);
        item.setEnabled(false);
    }
}
