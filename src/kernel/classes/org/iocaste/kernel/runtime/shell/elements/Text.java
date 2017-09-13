package org.iocaste.kernel.runtime.shell.elements;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.runtime.common.application.ToolData;
import org.iocaste.shell.common.Const;

/**
 * Componente de parágrafo html.
 * 
 * Elemento padrão é <p>, mas pode ser alterado para outros.
 * 
 * @author francisco.prates
 *
 */
public class Text extends ToolDataElement {
	private static final long serialVersionUID = -631255089556679788L;
    
    public Text(ViewContext viewctx, ToolData tooldata) {
    	super(viewctx, Const.TEXT, tooldata);
        init();
    }
    
    public Text(ViewContext viewctx, String name) {
        super(viewctx, Const.TEXT, name);
        init();
    }
    
    private final void init() {
        if (tooldata.tag == null)
            tooldata.tag = "p";
    }
}
