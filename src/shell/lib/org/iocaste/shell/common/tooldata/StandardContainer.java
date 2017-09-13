package org.iocaste.shell.common.tooldata;

import org.iocaste.shell.common.Const;

/**
 * Container padrão.
 * 
 * Equivalente à <div>
 * 
 * @author francisco.prates
 *
 */
public class StandardContainer extends ToolDataElement {
	private static final long serialVersionUID = 3679193738291634976L;

	public StandardContainer(Context viewctx, String name) {
        super(viewctx, Const.STANDARD_CONTAINER, name);
    }
	
	@Override
	public final boolean isContainable() {
	    return true;
	}
}