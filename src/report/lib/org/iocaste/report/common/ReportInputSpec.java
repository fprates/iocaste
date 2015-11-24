package org.iocaste.report.common;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelSpec;

public class ReportInputSpec extends AbstractPanelSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        reporttool(parent, "head");
    }

}
