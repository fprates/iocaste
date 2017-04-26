package org.iocaste.runtime.common.page;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.application.ToolData;

public abstract class AbstractViewConfig<C extends Context>
		implements ViewConfig {
	private C context;
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
        viewconfig.run(context);
    }
//    
//    protected final void disable(AbstractComponentData data, String... fields) {
//        setEnable(data, false, fields);
//    }
//    
//    protected final void enable(AbstractComponentData data, String... fields) {
//        setEnable(data, true, fields);
//    }
    
    /**
     * 
     * @param context
     */
    protected abstract void execute(C context);
    
    protected final ToolData getTool(String name) {
    	ToolData tooldata;
        String toolname = (prefix == null)? name : new StringBuilder(prefix).
        		append("_").append(name).toString();
    	AbstractPage page = context.getPage();
        tooldata = page.getToolData(toolname);
        if (tooldata != null)
        	return tooldata;
        for (String key : page.getChildren())
            if ((tooldata = page.getChild(key).getToolData(toolname)) != null)
            	return tooldata;
        return null;
    }
    
//    private final DocumentModel getModel(AbstractComponentData data) {
//        DocumentModel model;
//        
//        if (data.custommodel != null)
//            return data.custommodel;
//        model = models.get(data.model);
//        if (model != null)
//            return model;
//        model = context.getIocaste().getModel(data.model);
//        models.put(data.model, model);
//        return model;
//    }
    
    /**
     * 
     * @return
     */
    protected final String getPrefix() {
        return prefix;
    }
    
//    /**
//     * 
//     * @param tab
//     * @param name
//     * @return
//     */
//    protected final TabbedPaneItem getTabbedItem(String tab, String name) {
//        return ((TabbedPane)getElement(tab)).getElement(name);
//    }
//    
//    protected final void hide(AbstractComponentData data, String... fields) {
//        setVisible(data, false, fields);
//    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.appbuilder.common.ViewConfig#run(
     *    org.iocaste.appbuilder.common.PageBuilderContext)
     */
	@Override
    @SuppressWarnings("unchecked")
    public final void run(Context context) {
        this.context = (C)context;
        execute(this.context);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.appbuilder.common.ViewConfig#run(
     *    org.iocaste.appbuilder.common.PageBuilderContext, java.lang.String)
     */
	@Override
    @SuppressWarnings("unchecked")
    public final void run(Context context, String prefix) {
        this.context = (C)context;
        this.prefix = prefix;
        execute(this.context);
    }
//
//    private final void setEnable(AbstractComponentData data, boolean enable,
//            String... fields) {
//        DocumentModel model;
//        DocumentModelItem ns;
//        
//        model = getModel(data);
//        ns = model.getNamespace();
//        if (ns != null)
//            data.instance(ns.getName()).disabled = enable;
//        for (DocumentModelItem item : model.getItens())
//            data.instance(item.getName()).disabled = enable;
//        for (String name : fields)
//            data.get(name).disabled = !enable;
//    }
//    
//    private final void setVisible(AbstractComponentData data, boolean visible,
//            String... fields) {
//        DocumentModel model;
//        DocumentModelItem ns;
//        
//        model = getModel(data);
//        ns = model.getNamespace();
//        if (ns != null)
//            data.instance(ns.getName()).invisible = visible;
//        for (DocumentModelItem item : model.getItens())
//            data.instance(item.getName()).invisible = visible;
//        for (String name : fields)
//            data.get(name).invisible = !visible;
//    }
//    
//    protected final void show(AbstractComponentData data, String... fields) {
//        setVisible(data, true, fields);
//    }
}
