package org.iocaste.shell.common;

import org.iocaste.shell.common.tooldata.Context;
import org.iocaste.shell.common.tooldata.ElementViewContext;
import org.iocaste.shell.common.tooldata.ToolDataElement;
import org.iocaste.shell.common.tooldata.ViewSpecItem.TYPES;

/**
 * Componente de entrada TextArea.
 * 
 * O CSS desse componente ajusta a caixa para a larga total da página.
 * No momento, customize o CSS para personalizar esse componente.
 * 
 * @author francisco.prates
 *
 */
public class TextArea extends ToolDataElement {
    private static final long serialVersionUID = 4848464288942587299L;
    private int w, h;
    
    public TextArea(Context context, String name) {
        super(context, Const.TEXT_AREA, name);
        w = 20;
        h = 10;
    }
    
    public TextArea(Container container, String name) {
        this(new ElementViewContext(
                null, container, TYPES.TEXT_EDITOR, name), name);
    }
    
    public final int getHeight() {
        return h;
    }
    
    public final int getWidth() {
        return w;
    }
    
    @Override
    public final boolean isDataStorable() {
        return true;
    }
    
    public final void setSize(int w, int h) {
        this.w = w;
        this.h = h;
    }
}
