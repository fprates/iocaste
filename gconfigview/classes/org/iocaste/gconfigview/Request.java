package org.iocaste.gconfigview;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.View;

public class Request {
    private static final byte SEL_ITENS = 0;
    private static final String[] QUERIES = {
        "select GLOBAL_CONFIG_ITEM.ID, GLOBAL_CONFIG_ITEM.NAME," +
        "GLOBAL_CONFIG_ITEM.TYPE, GLOBAL_CONFIG_VALUES.VALUE " +
        "from GLOBAL_CONFIG_ITEM " +
        "inner join GLOBAL_CONFIG_VALUES on " +
        "GLOBAL_CONFIG_ITEM.ID = GLOBAL_CONFIG_VALUES.ID " +
        "where GLOBAL_CONFIG_ITEM.GLOBAL_CONFIG = ?"
    };
    
    public static final void display(View view, Function function,
            Context context) {
        context.mode = Context.DISPLAY;
        load(view, function, context);
    }
    
    public static final void edit(View view, Function function,
            Context context) {
        context.mode = Context.EDIT;
        load(view, function, context);
    }
    
    private static final void load(View view, Function function,
            Context context) {
        DataForm pkgform = view.getElement("package");
        String program = pkgform.get("NAME").get();
        Documents documents = new Documents(function);
        
        context.objects = documents.select(QUERIES[SEL_ITENS], program);
        if (context.objects == null) {
            view.message(Const.STATUS, "no.config");
            return;
        }
        
        view.redirect("configform");
    }
    
    public static final void save(View view, Function function,
            Context context) {
        InputComponent input;
        ExtendedObject object;
        String value, name;
        int id = 0;
        Documents documents = new Documents(function);
        DataForm form = view.getElement("package.config");
        DocumentModel model = documents.getModel("GLOBAL_CONFIG_VALUES");
        
        for (Element element : form.getElements()) {
            value = null;
            name = element.getName();
            for (ExtendedObject item : context.objects)
                if (item.getValue("NAME").equals(name)) {
                    id = item.geti("ID");
                    input = (InputComponent)element;
                    value = input.get().toString();
                    item.setValue("VALUE", value);
                    break;
                }
            
            if (value == null) {
                view.message(Const.ERROR, "parameters.save.error");
                return;
            }
            
            object = new ExtendedObject(model);
            object.setValue("ID", id);
            object.setValue("VALUE", value);
            documents.modify(object);
        }
        
        view.message(Const.STATUS, "save.successful");
    }

}
