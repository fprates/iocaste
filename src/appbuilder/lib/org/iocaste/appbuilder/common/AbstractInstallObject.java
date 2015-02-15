package org.iocaste.appbuilder.common;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DummyElement;
import org.iocaste.documents.common.DummyModelItem;
import org.iocaste.packagetool.common.SearchHelpData;
import org.iocaste.shell.common.StyleSheet;

public abstract class AbstractInstallObject {
    private StandardInstallContext context;
    
    protected final ComplexModelInstall cmodelInstance(String name) {
        return new ComplexModelInstall(name, context);
    }
    
    protected final DataElement elementbool(String name) {
        DataElement element = new DataElement(name);
        element.setType(DataType.BOOLEAN);
        context.put(name, element);
        return element;
    }
    
    protected final DataElement elementchar(
            String name, int length, boolean upcase) {
        DataElement element = new DataElement(name);
        element.setType(DataType.CHAR);
        element.setLength(length);
        element.setUpcase(upcase);
        context.put(name, element);
        return element;
    }
    
    protected final DataElement elementdate(String name) {
        DataElement element = new DataElement(name);
        element.setType(DataType.DATE);
        context.put(name, element);
        return element;
    }
    
    protected final DataElement elementdec(
            String name, int length, int decimals) {
        DataElement element = new DataElement(name);
        element.setType(DataType.DEC);
        element.setLength(length);
        element.setDecimals(decimals);
        context.put(name, element);
        return element;
    }
    
    protected final DataElement elementnumc(String name, int length) {
        DataElement element = new DataElement(name);
        
        element.setType(DataType.NUMC);
        element.setLength(length);
        context.put(name, element);
        return element;
    }
    
    protected abstract void execute(StandardInstallContext context)
            throws Exception ;
    
    protected final DocumentModelItem getItem(String name) {
        return context.getItem(name);
    }
    
    protected final void insert(ModelInstall model, Object... values) {
        context.getInstallData().addValues(model.getModel(), values);
    }
    
    protected final MessagesInstall messageInstance(String language) {
        return new MessagesInstall(context.getInstallData(), language);
    }
    
    protected final ModelInstall modelInstance(String name) {
        return modelInstance(name, null);
    }
    
    protected final ModelInstall modelInstance(String name, String table) {
        ModelInstall model = new ModelInstall(
                context.getInstallData(), name, table);
        model.setElements(context.getElements());
        
        return model;
    }
    
    protected final DummyElement reference(String dataelement) {
        return new DummyElement(dataelement);
    }
    
    protected final DummyModelItem reference(String model, String name) {
        return new DummyModelItem(model, name);
    }
    
    public final void run(StandardInstallContext context) throws Exception {
        this.context = context;
        execute(context);
    }
    
    public final DocumentModelItem searchhelp(DocumentModelItem item, String sh)
    {
        item.setSearchHelp(sh);
        return item;
    }
    
    protected final SearchHelpData searchHelpInstance(String name, String model)
    {
        SearchHelpData shd = new SearchHelpData(name);
        
        shd.setModel(model);
        context.getInstallData().add(shd);
        return shd;
    }
    
    public final DocumentModelItem tag(String name, DocumentModelItem item) {
        context.setItem(name, item);
        return item;
    }
    
    public final ModelInstall tag(String tag, ModelInstall model) {
        context.setModel(tag, model);
        return model;
    }
    
    protected final StyleSheet styleInstance(String name) {
        StyleSheet stylesheet = new StyleSheet();
        context.getInstallData().setStyleSheet(name, stylesheet);
        return stylesheet;
    }
}
