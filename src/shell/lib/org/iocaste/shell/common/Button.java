package org.iocaste.shell.common;

import org.iocaste.shell.common.tooldata.Context;
import org.iocaste.shell.common.tooldata.ElementViewContext;
import org.iocaste.shell.common.tooldata.ToolDataElement;
import org.iocaste.shell.common.tooldata.ViewSpecItem.TYPES;

/**
 * Botão html.
 * 
 * A ação chamada por padrão é o nome do botão.
 * submit é ajustável, false por default.
 * 
 * @author francisco.prates
 *
 */
public class Button extends ToolDataElement {
    private static final long serialVersionUID = 7733291339094177369L;

    public Button(View view, String name) {
        super(new ElementViewContext(view, null, TYPES.BUTTON, name),
                Const.BUTTON, name);
    }
    
    public Button(Container container, String name) {
        super(new ElementViewContext(null, container, TYPES.BUTTON, name),
                Const.BUTTON, name);
    }
    
    public Button(Context viewctx, String name) {
        super(viewctx, Const.BUTTON, name);
    }
    
    @Override
    public final EventHandler getEventHandler(String name) {
        return (name == null)?
                super.getEventHandler("click") : super.getEventHandler(name);
    }
    
    @Override
    public final boolean isControlComponent() {
        return true;
    }
    
    /**
     * Retorna se é submit.
     * @return true, se for submit.
     */
    public final boolean isSubmit() {
        return tooldata.submit;
    }
    
    /**
     * Ajusta propriedade submit.
     * @param submit true, para submit
     */
    public final void setSubmit(boolean submit) {
        tooldata.submit = submit;
    }
}