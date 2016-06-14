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
        String name, secret;
        ExtendedObject object;
        Iocaste iocaste;
        
        object = getdf("identity");
        secret = object.getst("SECRET");
        if (secret == null) {
            setFocus("identity", "SECRET");
            message(Const.ERROR, "undefined.password");
            return;
        }
        
        extcontext = getExtendedContext();
        extcontext.userdata.username = object.get("USERNAME");
        
        user = new User();
        user.setUsername(extcontext.userdata.username);
        user.setFirstname(object.getst("FIRSTNAME"));
        user.setSurname(object.getst("SURNAME"));
        
        iocaste = new Iocaste(context.function);
        if (extcontext.userdata.identity == null) {
            iocaste.create(user);
        } else {
            iocaste.update(user);
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

        if (secret != null)
            iocaste.setUserPassword(
                    extcontext.userdata.username, secret, object.getbl("INIT"));
        
        extcontext.userdata.identity = object;
        extcontext.extras = getdf("extras");
        extcontext.extras.set("USERNAME", extcontext.userdata.username);
        modify(extcontext.extras);
        
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
