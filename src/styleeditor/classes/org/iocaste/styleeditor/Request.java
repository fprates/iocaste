package org.iocaste.styleeditor;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;

public class Request {
    
    public static final void create(Context context) {
        String style = ((DataForm)context.view.getElement("selection")).
                get("estilo").get();
        
        if (new Documents(context.function).getObject("STYLE", style) != null) {
            context.view.message(Const.ERROR, "style.already.exists");
            return;
        }
        
        context.view.redirect("style");
    }
    
    public static final void element(Context context) {
        Query query;
        InputComponent input = context.view.getElement("NAME");
        Documents documents = new Documents(context.function);
        
        context.element = input.get();
        for (ExtendedObject object : context.items.getObjects()) {
            if (!object.getst("NAME").equals(context.element))
                continue;

            query = new Query();
            query.setModel("STYLE_ELEMENT_DETAIL");
            query.andEqual("ELEMENT", object.get("INDEX"));
            context.eproperties = documents.select(query);
            break;
        }
        
        context.view.redirect("detail");
    }
    
    public static final ExtendedObject[] load(Context context) {
        String style;
        Query query;
        Documents documents = new Documents(context.function);
        
        style = ((DataForm)context.view.getElement("selection")).
                get("estilo").get();
        
        context.header = documents.getObject("STYLE", style);
        if (context.header == null) {
            context.view.message(Const.ERROR, "invalid.style");
            return null;
        }
        
        context.view.redirect("style");
        query = new Query();
        query.setModel("STYLE_ELEMENT");
        query.andEqual("STYLE", style);
        return documents.select(query);
    }
}
