package org.iocaste.report.common;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.StandardPanel;
import org.iocaste.report.common.data.ReportViewerData;

public class ReportViewer {
    private StandardPanel panel;
    
    public ReportViewer(ReportViewerData data) {
        data.input.config.setViewerData(data);
        if (data.input.input == null)
            data.input.input = new ReportInputInput();

        data.output.config.setViewerData(data);
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
        setRenderContext(!data.norenderctx);
        set(new ReportInputSpec());
        set(data.input.config);
        set(data.input.input);
        action("select", new ReportSelect());
    }
    
}

class OutputPanelPage extends AbstractPanelPage {
    private ReportViewerData data;

    public OutputPanelPage(ReportViewerData data) {
        this.data = data;
    }
    
    @Override
    public void execute() {
        setRenderContext(!data.norenderctx);
        set(new ReportOutputSpec());
        set(data.output.config);
        set(data.output.input);
    }
    
}

class ReportInputInput extends AbstractReportInputInput { }

class ReportOutputInput extends AbstractReportOutputInput { }
