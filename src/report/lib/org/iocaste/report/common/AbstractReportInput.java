package org.iocaste.report.common;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;

public abstract class AbstractReportInput extends AbstractViewInput {
    protected static final byte INPUT = 0;
    protected static final byte OUTPUT = 1;
    private byte stage;
    
    public AbstractReportInput(byte stage) {
        this.stage = stage;
    }
    
    @Override
    protected final void execute(PageBuilderContext context) {
        ReportContext extcontext = getExtendedContext();
        switch (stage) {
        case INPUT:
            reportset("head", extcontext.object);
            break;
        case OUTPUT:
            reportset("output", extcontext.items);
            break;
        }
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
}
