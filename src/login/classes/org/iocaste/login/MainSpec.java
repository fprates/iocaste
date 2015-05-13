package org.iocaste.login;

import org.iocaste.appbuilder.common.panel.AbstractPanelSpec;

public class MainSpec extends AbstractPanelSpec {

    @Override
    protected void execute() {
        standardcontainer("content", "logincnt");
        dataform("logincnt", "login");
        button("logincnt", "connect");
    }

}
