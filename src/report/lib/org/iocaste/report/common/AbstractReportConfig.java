package org.iocaste.report.common;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.appbuilder.common.reporttool.ReportToolData;
import org.iocaste.appbuilder.common.reporttool.ReportToolStage;
import org.iocaste.report.common.data.ReportViewerData;
import org.iocaste.shell.common.Form;

public abstract class AbstractReportConfig extends AbstractViewConfig {
    private ReportViewerData data;
    
    public abstract void config(
            PageBuilderContext context, ReportToolStage config);
    
    @Override
    protected final void execute(PageBuilderContext context) {
        NavControl navcontrol;
        ReportToolData rtdata;
        Form container;
        String page;
        
        navcontrol = getNavControl();
        navcontrol.setTitle(data.titletext, data.titleargs);
        
        page = context.view.getPageName();
        if (page.equals(data.input.name)) {
            navcontrol.add("select");
            
            rtdata = getTool("head");
            rtdata.doInput();
            config(context, rtdata.input);
            return;
        }
        
        if (page.equals(data.output.name)) {
            for (String key : data.output.actions.keySet()) {
                if (data.output.actions.get(key) == null)
                    continue;
                navcontrol.add(key);
                navcontrol.noScreenLockFor(key);
            }
            
            container = getElement("main");
            container.setEnctype("multipart/form-data");
            
            rtdata = getTool("output");
            rtdata.doOutput();
            config(context, rtdata.output);
        }
    }
    
    public final void setViewerData(ReportViewerData data) {
        this.data = data;
    }
}
