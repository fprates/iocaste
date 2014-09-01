package org.iocaste.gconfigview;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;

public class Request {
    
    public static final void load(Context context) {
        Query query;
        DataForm pkgform = context.view.getElement("package");
        String program = pkgform.get("NAME").get();
        Documents documents = new Documents(context.function);
        
        query = new Query();
        query.addColumns("GLOBAL_CONFIG_ITEM.ID",
                "GLOBAL_CONFIG_ITEM.NAME",
                "GLOBAL_CONFIG_ITEM.TYPE",
                "GLOBAL_CONFIG_VALUES.VALUE");
        query.setModel("GLOBAL_CONFIG_ITEM");
        query.join("GLOBAL_CONFIG_VALUES", "GLOBAL_CONFIG_ITEM.ID", "ID");
        query.andEqual("GLOBAL_CONFIG_ITEM.GLOBAL_CONFIG", program);
        context.objects = documents.select(query);
        if (context.objects == null) {
            context.view.message(Const.STATUS, "no.config");
            return;
        }
        
        context.function.redirect("configform");
    }
    
    public static final void save(Context context) {
        InputComponent input;
        ExtendedObject object;
        String value, name;
        int id = 0;
        Documents documents = new Documents(context.function);
        DataForm form = context.view.getElement("package.config");
        DocumentModel model = documents.getModel("GLOBAL_CONFIG_VALUES");
        
        for (Element element : form.getElements()) {
            value = null;
            name = element.getName();
            for (ExtendedObject item : context.objects)
                if (item.get("NAME").equals(name)) {
                    id = item.geti("ID");
                    input = (InputComponent)element;
                    value = input.get().toString();
                    item.set("VALUE", value);
                    break;
                }
            
            if (value == null) {
                context.view.message(Const.ERROR, "parameters.save.error");
                return;
            }
            
            object = new ExtendedObject(model);
            object.set("ID", id);
            object.set("VALUE", value);
            documents.modify(object);
        }
        
        context.view.message(Const.STATUS, "save.successful");
    }

}
