package org.iocaste.shell.common;

import org.iocaste.shell.common.tooldata.Context;
import org.iocaste.shell.common.tooldata.ElementViewContext;
import org.iocaste.shell.common.tooldata.ToolDataElement;
import org.iocaste.shell.common.tooldata.ViewSpecItem.TYPES;

/**
 * Implementação de componente de parâmetro html.
 * 
 * @author francisco.prates
 *
 */
public class Parameter extends ToolDataElement {
    private static final long serialVersionUID = -8750478677094210518L;
    
    public Parameter(View view, String name) {
        this(new ElementViewContext(
                view, null, TYPES.PARAMETER, name), name);
    }
    
    public Parameter(Container container, String name) {
        this(new ElementViewContext(
                null, container, TYPES.PARAMETER, name), name);
    }
    
    public Parameter(Context context, String name) {
        super(context, Const.PARAMETER, name);
    }
    
    @Override
    public final boolean isDataStorable() {
        return true;
    }
}
