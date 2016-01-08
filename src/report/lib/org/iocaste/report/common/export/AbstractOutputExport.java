package org.iocaste.report.common.export;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Shell;

public abstract class AbstractOutputExport {
    private String path[];
    private PageBuilderContext context;
    private DataElement datede;
    private Locale locale;
    private Map<String, ReportPrintItem> values;
    private Map<String, String> translations;
    
    public abstract void formatValues(ExtendedObject object);

    protected final PageBuilderContext getContext() {
        return context;
    }
    
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
    
    public final Map<String, String> getTranslations() {
        return translations;
    }
    
    protected final ReportPrintItem print(String column, String value) {
        ReportPrintItem item = new ReportPrintItem(value);
        values.put(column, item);
        return item;
    }
    
    protected final ReportPrintItem print(
            String column, ExtendedObject object, String field) {
        DataElement element;
        String value;
        
        element = object.getModel().getModelItem(field).getDataElement();
        switch (element.getType()) {
        case DataType.CHAR:
            value = object.getst(field);
            break;
        case DataType.NUMC:
            value = Long.toString(object.getl(field)).toString();
            break;
        case DataType.DATE:
            value = getDateString(object, field);
            break;
        case DataType.DEC:
            value = Shell.toString(object.get(field), element, locale, false);
            break;
        default:
            value = object.get(field).toString();
            break;
        }
        return print(column, value);
    }
    
    public void printHeader(DocumentModel model) { }
    
    public final void setContext(PageBuilderContext context) {
        this.context = context;
        locale = context.view.getLocale();
    }
    
    public abstract void setOutputFile(PageBuilderContext context);
    
    protected final void setPath(String... path) {
        this.path = path;
    }
    
    public final void setTranslations(Map<String, String> translations) {
        this.translations = translations;
    }
    
    public final void setValues(Map<String, ReportPrintItem> values) {
        this.values = values;
    }
}
