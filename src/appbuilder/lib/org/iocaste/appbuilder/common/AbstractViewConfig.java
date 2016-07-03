package org.iocaste.appbuilder.common;

import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;

public abstract class AbstractViewConfig implements ViewConfig {
    private PageBuilderContext context;
    private NavControl navcontrol;
    private String prefix;
    private StyleSheet stylesheet;
    
    /**
     * 
     * @param viewconfig
     */
    protected final void config(ViewConfig viewconfig) {
        viewconfig.setNavControl(navcontrol);
        viewconfig.run(context);
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
    
    /**
     * 
     * @return
     */
    protected final NavControl getNavControl() {
        return navcontrol;
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
    
    @Override
    public final StyleSheet getStyleSheet() {
        return stylesheet;
    }
    
    /**
     * 
     * @return
     */
    private final ViewComponents getViewComponents() {
        return context.getView().getComponents();
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
        stylesheet = context.view.styleSheetInstance();
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
    
    protected final void show(AbstractComponentData data, String... fields) {
        DocumentModel model = new Documents(context.function).
                getModel(data.model);
        for (DocumentModelItem item : model.getItens())
            data.instance(item.getName()).invisible = true;
        for (String name : fields)
            data.get(name).invisible = false;
    }
}
