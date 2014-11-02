package org.iocaste.usereditor;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolColumn;
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
        DataForm form;

        pagecontrol.add("home");
        pagecontrol.add("back");
        
        /*
         * identificação
         */
        tabitem = new TabbedPaneItem(tabs, "idtab");
        form = new DataForm(tabitem, "identity");
        form.importModel("LOGIN", context.function);
        form.get("ID").setVisible(false);
        secret = form.get("INIT");
        secret.setComponentType(Const.CHECKBOX);
        secret = form.get("SECRET");
        secret.setSecret(true);
        username = form.get("USERNAME");
        username.setEnabled(false);
        
        switch (context.mode) {
        case Context.CREATE:
            username.set(context.userdata.username);
            pagecontrol.add("save", PageControl.REQUEST);
            break;
            
        case Context.DISPLAY:
            form.setObject(context.userdata.identity);
            for (Element element : form.getElements())
                element.setEnabled(false);
            break;
            
        case Context.UPDATE:
            form.setObject(context.userdata.identity);
            pagecontrol.add("save", PageControl.REQUEST);
            break;
        }
        
        /*
         * tarefas
         */
        tabitem = new TabbedPaneItem(tabs, "taskstab");
        ttdata = new TableToolData(tabitem, "tasks");
        ttdata.model = "USER_TASKS_GROUPS";
        ttdata.show = new String[] {"GROUP"};
        ttdata.objects = context.userdata.tasks;
        new TableToolColumn(ttdata, "GROUP").sh = "SH_TASKS_GROUPS";
        
        switch (context.mode) {
        case Context.CREATE:
        case Context.UPDATE:
            ttdata.mark = true;
            ttdata.mode = TableTool.UPDATE;
            break;
            
        case Context.DISPLAY:
            ttdata.mode = TableTool.DISPLAY;
            break;
        }
        
        context.taskshelper = new TableTool(context, ttdata);
        
        /*
         * perfis
         */
        tabitem = new TabbedPaneItem(tabs, "profiletab");
        ttdata = new TableToolData(tabitem, "profiles");
        ttdata.model = "USER_AUTHORITY";
        ttdata.show = new String[] {"PROFILE"};
        ttdata.objects = context.userdata.profiles;
        new TableToolColumn(ttdata, "PROFILE").sh = "SH_USER_PROFILE";
        
        switch (context.mode) {
        case Context.CREATE:
        case Context.UPDATE:
            ttdata.mark = true;
            ttdata.mode = TableTool.UPDATE;
            break;
            
        case Context.DISPLAY:
            ttdata.mode = TableTool.DISPLAY;
            break;
        }

        context.profileshelper = new TableTool(context, ttdata);
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
