package org.iocaste.runtime.common.managedview.display;

import org.iocaste.runtime.common.managedview.AbstractEntityDisplayPage;
import org.iocaste.runtime.common.managedview.edit.ManagedEditInput;
import org.iocaste.runtime.common.managedview.edit.ManagedEditSpec;

public class ManagedDisplayPage extends AbstractEntityDisplayPage {

    public ManagedDisplayPage() {
        super(new ManagedEditSpec(),
                new ManagedDisplayConfig(), new ManagedEditInput());
    }
     
}