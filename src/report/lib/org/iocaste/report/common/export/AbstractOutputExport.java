package org.iocaste.report.common.export;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Shell;

public abstract class AbstractOutputExport {
    private String path[];
    private PageBuilderContext context;
    private DataElement datede;
    private Locale locale;
    
    public abstract void formatValues(
            Map<String, String> values, ExtendedObject object);

    protected final String getDateString(ExtendedObject item, String name) {
        Date date;
        
        if (datede == null)
            datede = item.getModel().getModelItem(name).getDataElement();
        
        date = item.getdt(name);
        return Shell.toString(date, datede, locale, false);
    }
    
    protected final <T extends ExtendedContext> T getExtendedContext() {
        return context.getView().getExtendedContext();
    }
    
    public final String[] getPath() {
        return path;
    }
    
    public final void setContext(PageBuilderContext context) {
        this.context = context;
        locale = context.view.getLocale();
    }
    
    public abstract void setOutputFile(PageBuilderContext context);
    
    protected final void setPath(String... path) {
        this.path = path;
    }
}
