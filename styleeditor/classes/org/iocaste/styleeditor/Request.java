package org.iocaste.styleeditor;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.View;

public class Request {

    public static final void addstyle(View view) {
        
    }
    
    public static final void create(View view, Function function)
            throws Exception {
        String style = ((DataForm)view.getElement("selection")).get("estilo").
                get();
        
        if (new Documents(function).getObject("STYLE", style) != null) {
            view.message(Const.ERROR, "style.already.exists");
            return;
        }
        
        view.export("mode", Common.CREATE);
        view.redirect(null, "style");
    }
    
    public static final byte getMode(View view) {
        return view.getParameter("mode");
    }
    
    public static final void load(View view, Function function, byte mode)
            throws Exception {
        ExtendedObject ostyle;
        ExtendedObject[] oelements;
        Documents documents = new Documents(function);
        String query, style = ((DataForm)view.getElement("selection")).
                get("estilo").get();
        
        ostyle = documents.getObject("STYLE", style);
        
        if (ostyle == null) {
            view.message(Const.ERROR, "invalid.style");
            return;
        }
        
        query = "from STYLE_ELEMENT where STYLE = ?";
        oelements = documents.select(query, style);
        
        view.export("elements", oelements);
        view.export("mode", mode);
        view.redirect(null, "style");
    }
}
