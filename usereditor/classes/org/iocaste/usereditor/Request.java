package org.iocaste.usereditor;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.ViewData;

public class Request {

    public static final void create(ViewData view, Function function)
            throws Exception {
        DataForm form = view.getElement("selection");
        String username = form.get("USERNAME").get();
        
        if (new Documents(function).getObject("LOGIN", username) != null) {
            view.message(Const.ERROR, "user.already.exists");
            return;
        }
        
        view.setReloadableView(true);
        view.export("username", username);
        view.export("mode", Common.CREATE);
        view.redirect(null, "form");
    }
    
    public static final void load(ViewData view, Function function, byte mode)
            throws Exception {
        DataForm form = view.getElement("selection");
        String username = form.get("USERNAME").get();
        Documents documents = new Documents(function);
        ExtendedObject object = documents.getObject("LOGIN", username);
        
        if (object == null) {
            view.message(Const.ERROR, "invalid.user");
            return;
        }
        
        view.setReloadableView(true);
        view.export("identity", object);
        view.export("mode", mode);
        view.redirect(null, "form");
    }
    
    public static final void save(ViewData view, Function function)
            throws Exception {
        byte mode = Common.getMode(view);
        DataForm form = view.getElement("identity");
        ExtendedObject object = form.getObject();
        Documents documents = new Documents(function);
        
        switch (mode) {
        case Common.CREATE:
            documents.save(object);
            
            mode = Common.UPDATE;
            view.export("mode", mode);
            view.setTitle(Common.TITLE[mode]);
            break;
        }
        
        view.message(Const.STATUS, "user.saved.successfully");
    }
}
