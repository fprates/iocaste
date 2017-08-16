package org.iocaste.runtime.common.managedview;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.page.AbstractViewSpec;

public class ManagedSelectSpec extends AbstractViewSpec {

    @Override
    public void execute(Context context) {
        dataform(parent, "head");
    }

}