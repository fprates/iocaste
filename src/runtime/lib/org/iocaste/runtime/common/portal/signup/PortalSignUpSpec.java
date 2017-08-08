package org.iocaste.runtime.common.portal.signup;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.page.AbstractViewSpec;

public class PortalSignUpSpec extends AbstractViewSpec {

    @Override
    protected void execute(Context context) {
        nodelist(parent, "viewport");
        nodelistitem("viewport", "user_node");
        dataform("user_node", "user");
        button("user_node", "record");
    }
}

