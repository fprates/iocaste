package org.iocaste.gconfigview;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.View;

public class Request {
    private static final byte SEL_ITENS = 0;
    private static final String[] QUERIES = {
        "select GLOBAL_CONFIG_ITEM.NAME, GLOBAL_CONFIG_ITEM.TYPE, " +
        "GLOBAL_CONFIG_VALUES.VALUE " +
        "from GLOBAL_CONFIG_ITEM " +
        "inner join GLOBAL_CONFIG_VALUES on " +
        "GLOBAL_CONFIG_ITEM.ID = GLOBAL_CONFIG_VALUES.ID " +
        "where GLOBAL_CONFIG_ITEM.GLOBAL_CONFIG = ?"
    };
    
    public static final void display(View view, Function function) {
        load(view, Common.DISPLAY, function);
    }
    
    public static final void edit(View view, Function function) {
        load(view, Common.EDIT, function);
    }
    
    private static final void load(View view, byte mode, Function function) {
        ExtendedObject[] objects;
        DataForm pkgform = view.getElement("package");
        String program = pkgform.get("NAME").get();
        Documents documents = new Documents(function);
        
        objects = documents.select(QUERIES[SEL_ITENS], program);
        if (objects == null) {
            view.message(Const.STATUS, "no.config");
            return;
        }
        
        view.setReloadableView(true);
        view.export("objects", objects);
        view.export("mode", mode);
        view.redirect("configform");
    }

}
