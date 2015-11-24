package org.iocaste.report.common.data;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.navcontrol.NavControlDesign;
import org.iocaste.report.common.ReportContext;

public class ReportViewerData {
    public PageBuilderContext context;
    public ReportContext extcontext;
    public ReportViewerDataStage input, output;
    public NavControlDesign ncdesign;
    public String titletext;
    public Object[] titleargs;
    
    public ReportViewerData(PageBuilderContext context) {
        this.context = context;
        input = new ReportViewerDataStage();
        output = new ReportViewerDataStage();
    }
}
