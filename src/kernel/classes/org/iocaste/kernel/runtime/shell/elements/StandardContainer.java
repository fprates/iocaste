package org.iocaste.kernel.runtime.shell.elements;

import org.iocaste.kernel.runtime.shell.ViewContext;
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

	public StandardContainer(ViewContext viewctx, String name) {
        super(viewctx, Const.STANDARD_CONTAINER, name);
    	tooldata.container = true;
    	tooldata.action = tooldata.control = tooldata.datastore = false;
    }
}