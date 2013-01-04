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
     * @param function
     */
    public static final UserData create(View view, Function function) {
        UserData userdata;
        DataForm form = view.getElement("selection");
        String username = form.get("USERNAME").get();
        
        if (new Documents(function).getObject("LOGIN", username) != null) {
            view.message(Const.ERROR, "user.already.exists");
            return null;
        }
        
        userdata = new UserData();
        userdata.username = username;
        view.redirect("form");
        
        return userdata;
    }
    
    /**
     * 
     * @param view
     * @param function
     */
    public static final UserData load(View view, Function function) {
        DataForm form = view.getElement("selection");
        String username = form.get("USERNAME").get();
        Documents documents = new Documents(function);
        UserData userdata = new UserData();
        
        userdata.identity = documents.getObject("LOGIN", username);
        if (userdata.identity == null) {
            view.message(Const.ERROR, "invalid.user");
            return null;
        }
        
        userdata.profiles = documents.select(QUERIES[PROFILES], username);
        userdata.tasks = documents.select(QUERIES[TASKS], username);
        view.redirect("form");
        
        return userdata;
    }
    
    /**
     * 
     * @param view
     * @param function
     * @param mode
     */
    public static final void save(View view, Context context) {
        PackageTool pkgtool;
        Authority authority;
        Table itens;
        User user;
        String name;
        DataForm form = view.getElement("identity");
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
            view.setTitle(Context.TITLE[context.mode]);
            break;
            
        case Context.UPDATE:
            new Iocaste(context.function).update(user);
            documents.update(QUERIES[DEL_USR_AUTH], username);
            documents.update(QUERIES[DEL_USR_TASK], username);
            break;
        }
        
        authority = new Authority(context.function);
        itens = view.getElement("profiles");
        for (TableItem item : itens.getItens()) {
            name = ((InputComponent)item.get("PROFILE")).get();
            if (name == null)
                continue;
            authority.assign(username, name);
        }
        
        itens = view.getElement("tasks");
        if (itens.length() > 0) {
            pkgtool = new PackageTool(context.function);
            for (TableItem item : itens.getItens()) {
                name = ((InputComponent)item.get("GROUP")).get();
                if (name == null)
                    continue;
                pkgtool.assignTaskGroup(name, username);
            }
        }
        view.message(Const.STATUS, "user.saved.successfully");
    }
}
