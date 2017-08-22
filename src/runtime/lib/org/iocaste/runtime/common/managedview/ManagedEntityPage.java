package org.iocaste.runtime.common.managedview;

public class ManagedEntityPage extends AbstractEntityPage {

    public ManagedEntityPage() {
        super(null, null, null);
        spec = new ManagedSelectSpec();
        input = new ManagedSelectInput();
    }

}
