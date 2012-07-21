package org.iocaste.usereditor;

import org.iocaste.authority.common.Authority;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.PackageTool;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.user.User;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.View;

public class Request {
    private static final byte PROFILES = 0;
    private static final byte DEL_USR_AUTH = 1;
    private static final byte TASKS = 2;
    private static final byte DEL_USR_TASK = 3;
    private static final String[] QUERIES = {
        "from USER_AUTHORITY where USERNAME = ?",
        "delete from USER_AUTHORITY where USERNAME = ?",
        "from USER_TASKS_GROUPS where USERNAME = ?",
        "delete from USER_TASKS_GROUPS where USERNAME = ?"
    };
    
    /**
     * 
     * @param view
     */
    public static final void addprofile(View view) {
        Table profiles = view.getElement("profiles");
        Common.insertItem(profiles, null, Common.getMode(view));
    }
    
    /**
     * 
     * @param view
     */
    public static final void addtask(View view) {
        Table tasks = view.getElement("tasks");
        Common.insertItem(tasks, null, Common.getMode(view));
    }
    
    /**
     * 
     * @param view
     * @param function
     */
    public static final void create(View view, Function function) {
        DataForm form = view.getElement("selection");
        String username = form.get("USERNAME").get();
        
        if (new Documents(function).getObject("LOGIN", username) != null) {
            view.message(Const.ERROR, "user.already.exists");
            return;
        }
        
        view.setReloadableView(true);
        view.export("username", username);
        view.export("mode", Common.CREATE);
        view.redirect("form");
    }
    
    /**
     * 
     * @param view
     * @param function
     * @param mode
     */
    public static final void load(View view, Function function, byte mode) {
        ExtendedObject[] objects;
        DataForm form = view.getElement("selection");
        String username = form.get("USERNAME").get();
        Documents documents = new Documents(function);
        ExtendedObject object = documents.getObject("LOGIN", username);
        
        if (object == null) {
            view.message(Const.ERROR, "invalid.user");
            return;
        }
        
        objects = documents.select(QUERIES[PROFILES], username);
        view.export("profiles", objects);
        
        objects = documents.select(QUERIES[TASKS], username);
        view.export("tasks", objects);
        
        view.setReloadableView(true);
        view.export("identity", object);
        view.export("mode", mode);
        view.redirect("form");
    }
    
    /**
     * 
     * @param view
     * @param function
     */
    public static final void save(View view, Function function) {
        PackageTool pkgtool;
        Authority authority;
        Table itens;
        User user;
        byte mode = Common.getMode(view);
        DataForm form = view.getElement("identity");
        ExtendedObject object = form.getObject();
        Documents documents = new Documents(function);
        String name;
        String username = object.getValue("USERNAME");
        String secret = object.getValue("SECRET");
        
        user = new User();
        user.setUsername(username);
        user.setSecret(secret);
        
        switch (mode) {
        case Common.CREATE:
            new Iocaste(function).create(user);
            mode = Common.UPDATE;
            view.export("mode", mode);
            view.setTitle(Common.TITLE[mode]);
            break;
            
        case Common.UPDATE:
            new Iocaste(function).update(user);
            documents.update(QUERIES[DEL_USR_AUTH], username);
            documents.update(QUERIES[DEL_USR_TASK], username);
            break;
        }
        
        authority = new Authority(function);
        itens = view.getElement("profiles");
        for (TableItem item : itens.getItens()) {
            name = ((InputComponent)item.get("PROFILE")).get();
            authority.assign(username, name);
        }
        
        itens = view.getElement("tasks");
        if (itens.length() > 0) {
            pkgtool = new PackageTool(function);
            for (TableItem item : itens.getItens()) {
                name = ((InputComponent)item.get("GROUP")).get();
                pkgtool.assignTaskGroup(name, username);
            }
        }
        view.message(Const.STATUS, "user.saved.successfully");
    }
    
    /**
     * 
     * @param view
     */
    public static final void removeprofile(View view) {
        Table profiles = view.getElement("profiles");
        Common.removeItem(profiles);
    }
    
    /**
     * 
     * @param view
     */
    public static final void removetask(View view) {
        Table tasks = view.getElement("tasks");
        Common.removeItem(tasks);
    }
}
