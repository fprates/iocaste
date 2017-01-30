package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;

public abstract class AbstractViewConfig implements ViewConfig {
    private PageBuilderContext context;
    private NavControl navcontrol;
    private String prefix;
    private Map<String, DocumentModel> models;
    
    public AbstractViewConfig() {
        models = new HashMap<>();
    }
    
    /**
     * 
     * @param viewconfig
     */
    protected final void config(ViewConfig viewconfig) {
        viewconfig.setNavControl(navcontrol);
        viewconfig.run(context);
    }
    
    protected final void disable(AbstractComponentData data, String... fields) {
        setEnable(data, false, fields);
    }
    
    protected final void enable(AbstractComponentData data, String... fields) {
        setEnable(data, true, fields);
    }
    
    /**
     * 
     * @param context
     */
    protected abstract void execute(PageBuilderContext context);
    
    protected final <T extends AbstractComponentData> T getTool(String name) {
        return getViewComponents().getComponentData(name);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    protected final <T extends Element> T getElement(String name) {
        String elementname;
        
        if (prefix == null)
            elementname = name;
        else
            elementname = new StringBuilder(prefix).append("_").append(
                    name).toString();
        return context.view.getElement(elementname);
    }
    
    /**
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    protected final <T extends ExtendedContext> T getExtendedContext() {
        return (T)context.getView(context.view.getPageName()).
                getExtendedContext();
    }
    
    private final DocumentModel getModel(AbstractComponentData data) {
        DocumentModel model;
        
        if (data.custommodel != null)
            return data.custommodel;
        model = models.get(data.model);
        if (model != null)
            return model;
        model = new Documents(context.function).getModel(data.model);
        models.put(data.model, model);
        return model;
    }
    
    /**
     * 
     * @return
     */
    protected final NavControl getNavControl() {
        return navcontrol;
    }
    
    /**
     * 
     * @return
     */
    protected final String getPrefix() {
        return prefix;
    }
    
    /**
     * 
     * @param tab
     * @param name
     * @return
     */
    protected final TabbedPaneItem getTabbedItem(String tab, String name) {
        return ((TabbedPane)getElement(tab)).getElement(name);
    }
    
    /**
     * 
     * @return
     */
    private final ViewComponents getViewComponents() {
        return context.getView().getComponents();
    }
    
    protected final void hide(AbstractComponentData data, String... fields) {
        setVisible(data, false, fields);
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.appbuilder.common.ViewConfig#run(
     *    org.iocaste.appbuilder.common.PageBuilderContext)
     */
    @Override
    public final void run(PageBuilderContext context) {
        this.context = context;
        execute(context);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.appbuilder.common.ViewConfig#run(
     *    org.iocaste.appbuilder.common.PageBuilderContext, java.lang.String)
     */
    @Override
    public final void run(PageBuilderContext context, String prefix) {
        this.context = context;
        this.prefix = prefix;
        execute(context);
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.appbuilder.common.ViewConfig#setNavControl(
     *    org.iocaste.shell.common.NavControl)
     */
    @Override
    public final void setNavControl(NavControl navcontrol) {
        this.navcontrol = navcontrol;
    }

    private final void setEnable(AbstractComponentData data, boolean enable,
            String... fields) {
        DocumentModel model;
        DocumentModelItem ns;
        
        model = getModel(data);
        ns = model.getNamespace();
        if (ns != null)
            data.instance(ns.getName()).disabled = enable;
        for (DocumentModelItem item : model.getItens())
            data.instance(item.getName()).disabled = enable;
        for (String name : fields)
            data.get(name).disabled = !enable;
    }
    
    private final void setVisible(AbstractComponentData data, boolean visible,
            String... fields) {
        DocumentModel model;
        DocumentModelItem ns;
        
        model = getModel(data);
        ns = model.getNamespace();
        if (ns != null)
            data.instance(ns.getName()).invisible = visible;
        for (DocumentModelItem item : model.getItens())
            data.instance(item.getName()).invisible = visible;
        for (String name : fields)
            data.get(name).invisible = !visible;
    }
    
    protected final void show(AbstractComponentData data, String... fields) {
        setVisible(data, true, fields);
    }
}
