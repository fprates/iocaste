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
	private String mask;
    
    public Text(ViewContext viewctx, ToolData tooldata) {
    	super(viewctx, Const.TEXT, tooldata);
        init();
    }
    
    public Text(ViewContext viewctx, String name) {
        super(viewctx, Const.TEXT, name);
        init();
    }
    
    /**
     * 
     * @return
     */
    public final String getMask() {
        return mask;
    }
    
    /**
     * Obtem o elemento html do componente. 
     * @return elemento html
     */
    public final String getTag() {
        return tooldata.tag;
    }
    
    private final void init() {
        if (tooldata.tag == null)
            tooldata.tag = "p";
    }
    
    /**
     * 
     * @param mask
     */
    public final void setMask(String mask) {
        this.mask = mask;
    }
    
    /**
     * Ajusta o elemento html para o componente.
     * @param tag elemento html
     */
    public final void setTag(String tag) {
        tooldata.tag = tag;
    }
}
