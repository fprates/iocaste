package org.iocaste.appbuilder.common.portal.signup;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class PortalSignUpSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        nodelist(parent, "viewport");
        nodelistitem("viewport", "user_node");
        dataform("user_node", "user");
        button("user_node", "record");
    }
    
}

