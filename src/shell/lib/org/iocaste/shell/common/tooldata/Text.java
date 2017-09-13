package org.iocaste.shell.common.tooldata;

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
    
    public Text(Context viewctx, ToolData tooldata) {
    	super(viewctx, Const.TEXT, tooldata);
        init();
    }
    
    public Text(Context viewctx, String name) {
        super(viewctx, Const.TEXT, name);
        init();
    }
    
    private final void init() {
        if (tooldata.tag == null)
            tooldata.tag = "p";
    }
}
