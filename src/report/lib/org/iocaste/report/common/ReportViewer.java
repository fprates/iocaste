package org.iocaste.report.common;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.report.common.data.ReportViewerData;
import org.iocaste.report.common.export.CSVGenerate;

public class ReportViewer {
    
    public ReportViewer(ReportViewerData data) {
        if (data.input.name != null) {
            if (data.input.input == null)
                data.input.input = new ReportInputInput();
            
            data.context.add(
                    data.input.name, new InputPanelPage(data), data.extcontext);
        }
        
        if (data.output.input == null)
            data.output.input = new ReportOutputInput();
        data.context.add(
                data.output.name, new OutputPanelPage(data), data.extcontext);
    }

}

class InputPanelPage extends AbstractPanelPage {
    private ReportViewerData data;
    
    public InputPanelPage(ReportViewerData data) {
        this.data = data;
    }
    
    @Override
    public void execute() {
        data.input.config.setViewerData(data);
        data.input.input.setViewerData(data);
        data.select.setViewerData(data);
        
        set(new ReportInputSpec());
        set(data.input.config);
        set(data.input.input);
        setDesign(data.ncdesign);
        action("select", data.select);
    }
    
}

class OutputPanelPage extends AbstractPanelPage {
    private ReportViewerData data;

    public OutputPanelPage(ReportViewerData data) {
        this.data = data;
    }
    
    @Override
    public void execute() {
        AbstractActionHandler handler;
        
        data.output.config.setViewerData(data);
        data.output.input.setViewerData(data);
        
        set(new ReportOutputSpec());
        set(new ReportOutputConfig(data));
        set(data.output.input);
        setDesign(data.ncdesign);
        for (String key : data.output.actions.keySet()) {
            handler = data.output.actions.get(key);
            if (handler != null)
                action(key, handler);
        }
        if ((data.export != null) && !data.output.actions.containsKey("csv"))
            action("csv", new CSVGenerate(data.export));
    }
    
}

class ReportInputInput extends AbstractReportInput { }

class ReportOutputInput extends AbstractReportInput { }
