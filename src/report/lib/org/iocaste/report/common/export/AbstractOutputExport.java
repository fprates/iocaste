package org.iocaste.report.common.export;

import java.util.Map;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;

public abstract class AbstractOutputExport {
    private String path[];
    private PageBuilderContext context;
    
    public abstract void formatValues(
            Map<String, String> values, ExtendedObject object);
    
    protected final <T extends ExtendedContext> T getExtendedContext() {
        return context.getView().getExtendedContext();
    }
    
    public final String[] getPath() {
        return path;
    }
    
    public final void setContext(PageBuilderContext context) {
        this.context = context;
    }
    
    public abstract void setOutputFile(PageBuilderContext context);
    
    protected final void setPath(String... path) {
        this.path = path;
    }
}
