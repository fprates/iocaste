package org.iocaste.appbuilder.common.editor;

import org.iocaste.appbuilder.common.AbstractViewSpec;

public class SelectSpec extends AbstractViewSpec {

    @Override
    public void execute() {
        form("main");
        navControl("main");
        dataForm("main", "head");
    }

}