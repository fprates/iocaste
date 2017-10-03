package org.iocaste.shell.common;

import org.iocaste.shell.common.tooldata.Context;
import org.iocaste.shell.common.tooldata.ElementViewContext;
import org.iocaste.shell.common.tooldata.ToolDataElement;
import org.iocaste.shell.common.tooldata.ViewSpecItem.TYPES;

/**
 * Campo de entrada de texto.
 * 
 * Comprimento da entrada é de 20 caracteres, por padrão.
 * 
 * @author francisco.prates
 *
 */
public class TextField extends ToolDataElement {
    private static final long serialVersionUID = 4027561075976606307L;
    
    public TextField(View view, String name) {
        this(new ElementViewContext(
                view, null, TYPES.TEXT_FIELD, name), name);
    }
    
    public TextField(Container container, String name) {
        this(new ElementViewContext(
                null, container, TYPES.TEXT_FIELD, name), name);
    }
    
    public TextField(Context context, String name) {
        super(context, Const.TEXT_FIELD, name);
        if (getLength() != 0 || getModelItem() != null)
            return;
        setLength(20);
        setVisibleLength(20);
    }
    
    @Override
    public final boolean isDataStorable() {
        return true;
    }
}
