package org.iocaste.report.common;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.reporttool.ReportTool;
import org.iocaste.appbuilder.common.reporttool.ReportToolData;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.report.common.data.ReportViewerData;
import org.iocaste.shell.common.Const;

public abstract class AbstractReportSelect extends AbstractActionHandler {
    private ReportViewerData data;
    private DocumentModel outputmodel;
    private AbstractReportContext extcontext;
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        ReportToolData rtdata;
        
        inputrefresh();
        extcontext = getExtendedContext();
        
        rtdata = new ReportToolData(context, "output");
        data.output.config.config(context, rtdata.output);
        outputmodel = ReportTool.buildModel(rtdata);
        
        extcontext.object = reportinputget("head");
        extcontext.items.clear();
        select(context);
        if (extcontext.items.size() == 0) {
            message(Const.ERROR, "no.match");
            return;
        }
        
        init(data.output.name, extcontext);
        redirect(data.output.name);
    }
    
    protected final ExtendedObject outputLineInstance() {
        ExtendedObject object = new ExtendedObject(outputmodel);
        extcontext.items.add(object);
        return object;
    }
    
    protected abstract void select(PageBuilderContext context) throws Exception;
    
    public final void setViewerData(ReportViewerData data) {
        this.data = data;
    }

}
