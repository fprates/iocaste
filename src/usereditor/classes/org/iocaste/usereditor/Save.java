package org.iocaste.usereditor;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.authority.common.Authority;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.user.User;
import org.iocaste.shell.common.Const;

public class Save extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext;
        Query query;
        Authority authority;
        User user;
        String name;
        ExtendedObject object;

        object = getdf("identity");
        extcontext = getExtendedContext();
        extcontext.userdata.username = object.get("USERNAME");
        
        user = new User();
        user.setUsername(extcontext.userdata.username);
        user.setSecret(object.getst("SECRET"));
        user.setFirstname(object.getst("FIRSTNAME"));
        user.setSurname(object.getst("SURNAME"));
        user.setInitialSecret(object.getbl("INIT"));
        
        if (extcontext.userdata.identity == null) {
            new Iocaste(context.function).create(user);
        } else {
            new Iocaste(context.function).update(user);
            query = new Query("delete");
            query.setModel("USER_AUTHORITY");
            query.andEqual("USERNAME", extcontext.userdata.username);
            update(query);
            
            query = new Query("delete");
            query.setModel("USER_TASKS_GROUPS");
            query.andEqual("USERNAME", extcontext.userdata.username);
            update(query);
            
            query = new Query("delete");
            query.setModel("LOGIN_EXTENSION");
            query.andEqual("USERNAME", extcontext.userdata.username);
        }

        extcontext.userdata.identity = object;
        extcontext.extras = getdf("extras");
        extcontext.extras.set("USERNAME", extcontext.userdata.username);
        save(extcontext.extras);
        
        authority = new Authority(context.function);
        extcontext.userdata.profiles = tableitemsget("profiles");
        for (ExtendedObject item : extcontext.userdata.profiles) {
            name = item.getst("PROFILE");
            if (name == null)
                continue;
            authority.assign(extcontext.userdata.username, name);
        }
        
        extcontext.userdata.tasks = tableitemsget("tasks");
        for (ExtendedObject item : extcontext.userdata.tasks) {
            name = item.getst("GROUP");
            if (name == null)
                continue;
            extcontext.pkgtool.assignTaskGroup(
                    name, extcontext.userdata.username);
        }
        
        message(Const.STATUS, "user.saved.successfully");
    }

}
