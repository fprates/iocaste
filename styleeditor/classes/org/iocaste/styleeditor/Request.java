package org.iocaste.styleeditor;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.TableItem;

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
        String element;
        String query = "select * from STYLE_ELEMENT_DETAIL where ELEMENT = ?";
        InputComponent input = context.view.getElement("NAME");
        Documents documents = new Documents(context.function);
        
        context.element = input.get();
        for (TableItem item : context.items.getItems()) {
            element = ((Link)item.get("NAME")).getText();
            if (!element.equals(context.element))
                continue;

            input = item.get("INDEX");
            context.eproperties = documents.select(query, input.get());
            break;
        }
        
        context.view.redirect("detail");
    }
    
    public static final ExtendedObject[] load(Context context) {
        String query, style;
        Documents documents = new Documents(context.function);
        
        style = ((DataForm)context.view.getElement("selection")).
                get("estilo").get();
        
        context.header = documents.getObject("STYLE", style);
        if (context.header == null) {
            context.view.message(Const.ERROR, "invalid.style");
            return null;
        }
        
        context.view.redirect("style");
        query = "from STYLE_ELEMENT where STYLE = ?";
        return documents.select(query, style);
    }
}
