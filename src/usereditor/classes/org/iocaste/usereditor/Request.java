package org.iocaste.usereditor;

import org.iocaste.authority.common.Authority;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.packagetool.common.PackageTool;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.user.User;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;

public class Request {
    
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
        Query query;
        DataForm form = context.view.getElement("selection");
        String username = form.get("USERNAME").get();
        Documents documents = new Documents(context.function);
        UserData userdata = new UserData();
        
        userdata.identity = documents.getObject("LOGIN", username);
        if (userdata.identity == null) {
            context.view.message(Const.ERROR, "invalid.user");
            return null;
        }
        
        query = new Query();
        query.setModel("USER_AUTHORITY");
        query.andEqual("USERNAME", username);
        userdata.profiles = documents.select(query);
        
        query = new Query();
        query.setModel("USER_TASKS_GROUPS");
        query.andEqual("USERNAME", username);
        userdata.tasks = documents.select(query);
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
        Query[] queries;
        PackageTool pkgtool;
        Authority authority;
        Table itens;
        User user;
        String name;
        DataForm form = context.view.getElement("identity");
        ExtendedObject object = form.getObject();
        Documents documents = new Documents(context.function);
        String username = object.get("USERNAME");
        
        user = new User();
        user.setUsername(username);
        user.setSecret((String)object.get("SECRET"));
        user.setFirstname((String)object.get("FIRSTNAME"));
        user.setSurname((String)object.get("SURNAME"));
        user.setInitialSecret((boolean)object.get("INIT"));
        
        switch (context.mode) {
        case Context.CREATE:
            new Iocaste(context.function).create(user);
            context.mode = Context.UPDATE;
            context.view.setTitle(Context.TITLE[context.mode]);
            break;
            
        case Context.UPDATE:
            new Iocaste(context.function).update(user);
            queries = new Query[2];
            queries[0] = new Query("delete");
            queries[0].setModel("USER_AUTHORITY");
            queries[0].andEqual("USERNAME", username);
            
            queries[1] = new Query("delete");
            queries[1].setModel("USER_TASKS_GROUPS");
            queries[1].andEqual("USERNAME", username);
            documents.update(queries);
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