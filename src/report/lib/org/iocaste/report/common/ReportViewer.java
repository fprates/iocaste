package org.iocaste.report.common;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.StandardPanel;
import org.iocaste.report.common.data.ReportViewerData;
import org.iocaste.report.common.export.CSVGenerate;

public class ReportViewer {
    private StandardPanel panel;
    
    public ReportViewer(ReportViewerData data) {
        if (data.input.input == null)
            data.input.input = new ReportInputInput();
        
        if (data.output.input == null)
            data.output.input = new ReportOutputInput();
        
        panel = new StandardPanel(data.context);
        panel.instance(
                data.input.name, new InputPanelPage(data), data.extcontext);
        panel.instance(
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
        
        setRenderContext(!data.norenderctx);
        set(new ReportInputSpec());
        set(data.input.config);
        set(data.input.input);
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
        data.output.config.setViewerData(data);
        data.output.input.setViewerData(data);
        
        setRenderContext(!data.norenderctx);
        set(new ReportOutputSpec());
        set(new ReportOutputConfig(data));
        set(data.output.input);
        if (data.export != null)
            action("csv", new CSVGenerate(data.export));
        for (String key : data.output.actions.keySet())
            action(key, data.output.actions.get(key));
    }
    
}

class ReportInputInput extends AbstractReportInput { }

class ReportOutputInput extends AbstractReportInput { }
