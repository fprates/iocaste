package org.iocaste.report.common.data;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.report.common.AbstractReportConfig;
import org.iocaste.report.common.AbstractReportInput;

public class ReportViewerDataStage {
    public String name;
    public Map<String, AbstractActionHandler> actions;
    public AbstractReportConfig config;
    public AbstractReportInput input;
    
    public ReportViewerDataStage() {
        actions = new LinkedHashMap<>();
    }
}