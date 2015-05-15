package org.iocaste.login;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelSpec;

public class ChangeSecretSpec extends AbstractPanelSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        standardcontainer("content", "chgscrtcnt");
        dataform("chgscrtcnt", "chgscrt");
        button("chgscrtcnt", "changesecret");
    }

}
