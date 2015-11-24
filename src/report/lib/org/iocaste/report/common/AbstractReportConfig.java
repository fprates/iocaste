package org.iocaste.report.common;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.appbuilder.common.reporttool.ReportToolData;
import org.iocaste.appbuilder.common.reporttool.ReportToolStage;
import org.iocaste.report.common.data.ReportViewerData;
import org.iocaste.shell.common.Form;

public abstract class AbstractReportConfig extends AbstractViewConfig {
    protected static final byte INPUT = 0;
    protected static final byte OUTPUT = 1;
    private byte stage;
    private ReportViewerData data;
    protected ReportToolStage config;
    
    public AbstractReportConfig(byte stage) {
        this.stage = stage;
    }
    
    protected abstract void config(PageBuilderContext context);
    
    @Override
    protected final void execute(PageBuilderContext context) {
        NavControl navcontrol;
        ReportToolData rtdata;
        Form container;
        
        navcontrol = getNavControl();
        navcontrol.setDesign(data.ncdesign);
        navcontrol.setTitle(data.titletext, data.titleargs);
        
        switch (stage) {
        case INPUT:
            navcontrol.add("select");
            
            rtdata = getReportTool("head");
            rtdata.doInput();
            config = rtdata.input;
            break;
        case OUTPUT:
            for (String key : data.output.actions.keySet()) {
                navcontrol.add(key);
                navcontrol.noScreenLockFor(key);
            }
            
            container = getElement("main");
            container.setEnctype("multipart/form-data");
            
            rtdata = getReportTool("output");
            rtdata.doOutput();
            config = rtdata.output;
            break;
        }
        
        config(context);
    }
    
    public final void setViewerData(ReportViewerData data) {
        this.data = data;
    }
}
