package org.iocaste.login;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class MainSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        standardcontainer("content", "logincnt");
        dataform("logincnt", "login");
        button("logincnt", "connect");
    }

}
