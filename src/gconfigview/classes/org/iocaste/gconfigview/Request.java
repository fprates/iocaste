package org.iocaste.gconfigview;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.Query;
import org.iocaste.globalconfig.common.GlobalConfig;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;

public class Request {
    
    public static final void load(Context context) {
        Query query;
        DataForm pkgform = context.view.getElement("package");
        Documents documents = new Documents(context.function);
        
        context.appname = pkgform.get("NAME").get();
        query = new Query();
        query.addColumns("GLOBAL_CONFIG_ITEM.ID",
                "GLOBAL_CONFIG_ITEM.NAME",
                "GLOBAL_CONFIG_ITEM.TYPE",
                "GLOBAL_CONFIG_VALUES.VALUE");
        query.setModel("GLOBAL_CONFIG_ITEM");
        query.join("GLOBAL_CONFIG_VALUES", "GLOBAL_CONFIG_ITEM.ID", "ID");
        query.andEqual("GLOBAL_CONFIG_ITEM.GLOBAL_CONFIG", context.appname);
        context.objects = documents.select(query);
        if (context.objects == null) {
            context.function.message(Const.STATUS, "no.config");
            return;
        }
        
        context.function.redirect("configform");
    }
    
    public static final void save(Context context) {
        InputComponent input;
        String name;
        GlobalConfig config;
        Map<String, Object> values;
        DataForm form = context.view.getElement("package.config");
        
        values = new HashMap<>();
        for (Element element : form.getElements()) {
            input = (InputComponent)element;
            name = input.getName();
            values.put(name, form.get(name).get());
        }
        
        config = new GlobalConfig(context.function);
        config.set(context.appname, values);
        context.function.message(Const.STATUS, "save.successful");
    }

}
