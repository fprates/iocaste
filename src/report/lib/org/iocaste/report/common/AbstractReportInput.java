package org.iocaste.report.common;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.report.common.data.ReportViewerData;

public abstract class AbstractReportInput extends AbstractViewInput {
    private ReportViewerData data;
    
    @Override
    protected final void execute(PageBuilderContext context) {
        AbstractReportContext extcontext = getExtendedContext();
        String page = context.view.getPageName();
        
        if (page.equals(data.input.name))
            reportset("head", extcontext.object);
        else
            reportset("output", extcontext.items);
    }

    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }
    
    public final void setList(String field, Map<String, Object> values) {
        reportlistset("head", field, values);
    }

    public final void setList(String field, ExtendedObject[] values) {
        reportlistset("head", field, values);
    }
    
    public final void setViewerData(ReportViewerData data) {
        this.data = data;
    }
}
