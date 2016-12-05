package org.iocaste.report.common.data;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.report.common.AbstractReportContext;
import org.iocaste.report.common.AbstractReportSelect;
import org.iocaste.report.common.export.AbstractOutputExport;

public class ReportViewerData {
    public PageBuilderContext context;
    public AbstractReportContext extcontext;
    public AbstractReportSelect select;
    public Map<String, AbstractOutputExport> export;
    public ReportViewerDataStage input, output;
    public AbstractPanelPage ncdesign;
    public String titletext;
    public Object[] titleargs;
    public boolean dataonly;
    
    public ReportViewerData(PageBuilderContext context) {
        this.context = context;
        input = new ReportViewerDataStage();
        output = new ReportViewerDataStage();
        export = new HashMap<>();
    }
}
