package org.iocaste.runtime.common.managedview.edit;

import org.iocaste.runtime.common.managedview.AbstractEntityCustomPage;

public class ManagedEditPage extends AbstractEntityCustomPage {
    
    public ManagedEditPage() {
        super(new ManagedEditSpec(),
                new ManagedEditConfig(), new ManagedEditInput());
    }
}
