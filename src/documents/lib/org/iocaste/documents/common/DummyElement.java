package org.iocaste.documents.common;

/**
 * <p>Implements a full dummy data element.</p>
 * 
 * <p>Dummy elements doesn't hold any technical definitions.</p>
 * <p>It fits just for reference matters.</p>
 * 
 * @author francisco.prates
 *
 */
public class DummyElement extends DataElement {
    private static final long serialVersionUID = 5848722115522214722L;

    public DummyElement(String name) {
        super(name);
        setDummy(true);
    }
}
