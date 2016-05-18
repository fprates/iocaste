package org.iocaste.login;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class ChangeSecretSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        standardcontainer("content", "chgscrtcnt");
        dataform("chgscrtcnt", "chgscrt");
        button("chgscrtcnt", "changesecret");
    }

}
