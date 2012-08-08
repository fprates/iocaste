package org.iocaste.documents.common;

public class DummyElement extends DataElement {
    private static final long serialVersionUID = 5848722115522214722L;

    public DummyElement(String name) {
        setName(name);
        setDummy(true);
    }
}
