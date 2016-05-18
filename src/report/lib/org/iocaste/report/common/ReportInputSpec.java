package org.iocaste.report.common;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class ReportInputSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        reporttool(parent, "head");
    }

}
