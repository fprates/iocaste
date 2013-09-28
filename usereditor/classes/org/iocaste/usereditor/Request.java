package org.iocaste.usereditor;

import org.iocaste.authority.common.Authority;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.PackageTool;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.user.User;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;

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
     * @param context
     */
    public static final void create(Context context) {
        DataForm form = context.view.getElement("selection");
        String username = form.get("USERNAME").get();
        
        context.userdata = null;
        if (exists(username, context)) {
            context.view.message(Const.ERROR, "user.already.exists");
            return;
        }
        
        context.userdata = new UserData();
        context.userdata.username = username;
        context.view.redirect("form");
    }
    
    public static final void delete(Context context) {
        DataForm form = context.view.getElement("selection");
        String username = form.get("USERNAME").get();
        
        if (!exists(username, context)) {
            context.view.message(Const.ERROR, "user.not.exists");
            return;
        }
        
        new Iocaste(context.function).dropUser(username);
        context.view.message(Const.STATUS, "user.dropped");
    }
    
    private static final boolean exists(String username, Context context) {
        Documents documents = new Documents(context.function);
        
        return (documents.getObject("LOGIN", username) != null); 
    }
    
    /**
     * 
     * @param context
     * @return
     */
    public static final UserData load(Context context) {
        DataForm form = context.view.getElement("selection");
        String username = form.get("USERNAME").get();
        Documents documents = new Documents(context.function);
        UserData userdata = new UserData();
        
        userdata.identity = documents.getObject("LOGIN", username);
        if (userdata.identity == null) {
            context.view.message(Const.ERROR, "invalid.user");
            return null;
        }
        
        userdata.profiles = documents.select(QUERIES[PROFILES], username);
        userdata.tasks = documents.select(QUERIES[TASKS], username);
        context.view.redirect("form");
        
        return userdata;
    }
    
    /**
     * 
     * @param view
     * @param function
     * @param mode
     */
    public static final void save(Context context) {
        PackageTool pkgtool;
        Authority authority;
        Table itens;
        User user;
        String name;
        DataForm form = context.view.getElement("identity");
        ExtendedObject object = form.getObject();
        Documents documents = new Documents(context.function);
        String username = object.getValue("USERNAME");
        
        user = new User();
        user.setUsername(username);
        user.setSecret((String)object.getValue("SECRET"));
        user.setFirstname((String)object.getValue("FIRSTNAME"));
        user.setSurname((String)object.getValue("SURNAME"));
        user.setInitialSecret((boolean)object.getValue("INIT"));
        
        switch (context.mode) {
        case Context.CREATE:
            new Iocaste(context.function).create(user);
            context.mode = Context.UPDATE;
            context.view.setTitle(Context.TITLE[context.mode]);
            break;
            
        case Context.UPDATE:
            new Iocaste(context.function).update(user);
            documents.update(QUERIES[DEL_USR_AUTH], username);
            documents.update(QUERIES[DEL_USR_TASK], username);
            break;
        }
        
        authority = new Authority(context.function);
        itens = context.view.getElement("profiles");
        for (TableItem item : itens.getItems()) {
            name = ((InputComponent)item.get("PROFILE")).get();
            if (name == null)
                continue;
            authority.assign(username, name);
        }
        
        itens = context.view.getElement("tasks");
        if (itens.length() > 0) {
            pkgtool = new PackageTool(context.function);
            for (TableItem item : itens.getItems()) {
                name = ((InputComponent)item.get("GROUP")).get();
                if (name == null)
                    continue;
                pkgtool.assignTaskGroup(name, username);
            }
        }
        context.view.message(Const.STATUS, "user.saved.successfully");
    }
}
