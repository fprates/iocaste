package org.iocaste.usereditor;

import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.Function;
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
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableTool;
import org.iocaste.shell.common.View;

public class Response {

    public static final void form(View view, Context context) {
        Table profiles, tasks;
        TabbedPaneItem tabitem;
        DataItem secret, username;
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        TabbedPane tabs = new TabbedPane(container, "tabs");
        DataForm form = new DataForm(tabs, "identity");

        pagecontrol.add("home");
        pagecontrol.add("back");
        
        /*
         * identificação
         */
        form.importModel(context.usermodel);
        form.get("ID").setVisible(false);
        secret = form.get("INIT");
        secret.setComponentType(Const.CHECKBOX);
        secret = form.get("SECRET");
        secret.setSecret(true);
        username = form.get("USERNAME");
        username.setEnabled(false);
        
        tabitem = new TabbedPaneItem(tabs, "idtab");
        tabitem.setContainer(form);
        
        /*
         * tarefas
         */
        context.taskshelper = new TableTool(tabs, "tasks");
        tasks = context.taskshelper.getTable();
        tasks.importModel(context.tasksmodel);
        context.taskshelper.visible("GROUP");
        context.taskshelper.setObjects(context.userdata.tasks);
        
        tabitem = new TabbedPaneItem(tabs, "taskstab");
        tabitem.setContainer(context.taskshelper.getContainer());
        
        /*
         * perfis
         */
        context.profileshelper = new TableTool(tabs, "profiles");
        profiles = context.profileshelper.getTable();
        profiles.importModel(context.profilesmodel);
        context.profileshelper.visible("PROFILE");
        context.profileshelper.setObjects(context.userdata.profiles);
        
        tabitem = new TabbedPaneItem(tabs, "profiletab");
        tabitem.setContainer(context.profileshelper.getContainer());
        
        switch (context.mode) {
        case Context.CREATE:
            username.set(context.userdata.username);
            pagecontrol.add("save", PageControl.REQUEST);
            context.taskshelper.setMode(TableTool.UPDATE, view);
            context.profileshelper.setMode(TableTool.UPDATE, view);
            break;
            
        case Context.DISPLAY:
            form.setObject(context.userdata.identity);
            for (Element element : form.getElements())
                element.setEnabled(false);
            context.taskshelper.setMode(TableTool.DISPLAY, view);
            context.profileshelper.setMode(TableTool.DISPLAY, view);
            break;
            
        case Context.UPDATE:
            form.setObject(context.userdata.identity);
            pagecontrol.add("save", PageControl.REQUEST);
            context.taskshelper.setMode(TableTool.UPDATE, view);
            context.profileshelper.setMode(TableTool.UPDATE, view);
            break;
        }
        
        view.setFocus(secret);
        view.setTitle(Context.TITLE[context.mode]);
    }
    
    /**
     * 
     * @param view
     * @param function
     */
    public static final void selector(View view, Function function) {
        InputComponent input;
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "selection");
        
        pagecontrol.add("home");
        form.importModel(new Documents(function).getModel("LOGIN"));
        for (Element element : form.getElements()) {
            if (!element.getName().equals("USERNAME")) {
                element.setVisible(false);
                continue;
            }

            input = (InputComponent)element;
            view.setFocus(input);
            input.getModelItem().setSearchHelp("SH_USER");
            input.setObligatory(true);
        }
        
        new Button(container, "create");
        new Button(container, "display");
        new Button(container, "update");
        
        view.setTitle("user-selection");
    }
}
