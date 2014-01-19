package org.iocaste.shell.common;

/**
 * Container padrão.
 * 
 * Equivalente à <div>
 * 
 * @author francisco.prates
 *
 */
public class StandardContainer extends AbstractContainer {
    private static final long serialVersionUID = 1412766459212743893L;

    public StandardContainer(View view, String name) {
        super(view, Const.STANDARD_CONTAINER, name);
    }
    
    public StandardContainer(Container container, String name) {
        super(container, Const.STANDARD_CONTAINER, name);
    }
}