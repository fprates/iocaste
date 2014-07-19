package org.iocaste.usereditor;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.documents.common.Documents;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;

public class Response {

    /**
     * 
     * @param context
     */
    public static final void form(Context context) {
        TabbedPaneItem tabitem;
        DataItem secret, username;
        TableToolData ttdata;
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        TabbedPane tabs = new TabbedPane(container, "tabs");
        DataForm form = new DataForm(tabs, "identity");

        pagecontrol.add("home");
        pagecontrol.add("back");
        
        /*
         * identificação
         */
        form.importModel("LOGIN", context.function);
        form.get("ID").setVisible(false);
        secret = form.get("INIT");
        secret.setComponentType(Const.CHECKBOX);
        secret = form.get("SECRET");
        secret.setSecret(true);
        username = form.get("USERNAME");
        username.setEnabled(false);
        
        tabitem = new TabbedPaneItem(tabs, "idtab");
        tabitem.set(form);
        
        /*
         * tarefas
         */
        ttdata = new TableToolData();
        ttdata.container = tabs;
        ttdata.name = "tasks";
        ttdata.context = context;
        context.taskshelper = new TableTool(ttdata);
        context.taskshelper.model("USER_TASKS_GROUPS");
        context.taskshelper.setVisibility(true, "GROUP");
        context.taskshelper.setObjects(context.userdata.tasks);
        context.taskshelper.setSearchHelp("GROUP", "SH_TASKS_GROUPS");
        
        tabitem = new TabbedPaneItem(tabs, "taskstab");
        tabitem.set(context.taskshelper.getContainer());
        
        /*
         * perfis
         */
        ttdata = new TableToolData();
        ttdata.container = tabs;
        ttdata.name = "profiles";
        ttdata.context = context;
        context.profileshelper = new TableTool(ttdata);
        context.profileshelper.model("USER_AUTHORITY");
        context.profileshelper.setVisibility(true, "PROFILE");
        context.profileshelper.setObjects(context.userdata.profiles);
        context.profileshelper.setSearchHelp("PROFILE", "SH_USER_PROFILE");
        
        tabitem = new TabbedPaneItem(tabs, "profiletab");
        tabitem.set(context.profileshelper.getContainer());
        
        switch (context.mode) {
        case Context.CREATE:
            username.set(context.userdata.username);
            pagecontrol.add("save", PageControl.REQUEST);
            context.taskshelper.setMode(TableTool.UPDATE);
            context.profileshelper.setMode(TableTool.UPDATE);
            break;
            
        case Context.DISPLAY:
            form.setObject(context.userdata.identity);
            for (Element element : form.getElements())
                element.setEnabled(false);
            context.taskshelper.setMode(TableTool.DISPLAY);
            context.profileshelper.setMode(TableTool.DISPLAY);
            break;
            
        case Context.UPDATE:
            form.setObject(context.userdata.identity);
            pagecontrol.add("save", PageControl.REQUEST);
            context.taskshelper.setMode(TableTool.UPDATE);
            context.profileshelper.setMode(TableTool.UPDATE);
            break;
        }
        
        context.view.setFocus(secret);
        context.view.setTitle(Context.TITLE[context.mode]);
    }
    
    public static final void selector(Context context) {
        InputComponent input;
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "selection");
        
        pagecontrol.add("home");
        form.importModel(new Documents(context.function).getModel("LOGIN"));
        for (Element element : form.getElements()) {
            if (!element.getName().equals("USERNAME")) {
                element.setVisible(false);
                continue;
            }

            input = (InputComponent)element;
            context.view.setFocus(input);
            input.getModelItem().setSearchHelp("SH_USER");
            input.setObligatory(true);
        }
        
        new Button(container, "create");
        new Button(container, "display");
        new Button(container, "update");
        new Button(container, "delete");
        
        context.view.setTitle("user-selection");
    }
}
