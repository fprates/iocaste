package org.iocaste.kernel.runtime.shell.elements;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.shell.common.Const;

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
    
    public Button(ViewContext viewctx, String name) {
        super(viewctx, Const.BUTTON, name);
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