package org.iocaste.runtime.common.page;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.application.ToolData;

public abstract class AbstractViewConfig<C extends Context>
		implements ViewConfig {
	private C context;
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
    
    protected final void disable(ToolData data, String... fields) {
        setEnable(data, false, fields);
    }
    
    protected final void enable(ToolData data, String... fields) {
        setEnable(data, true, fields);
    }
    
    /**
     * 
     * @param context
     */
    protected abstract void execute(C context);
    
    protected final ToolData getTool(String name) {
    	ToolData tooldata;
    	AbstractPage page = context.getPage();
        tooldata = page.instance(name);
        if (tooldata != null)
        	return tooldata;
        for (String key : page.getChildren())
            if ((tooldata = page.getChild(key).instance(name)) != null)
            	return tooldata;
        return null;
    }
    
    private final DocumentModel getModel(ToolData data) {
        DocumentModel model;
        
        if (data.custommodel != null)
            return data.custommodel;
        model = models.get(data.model);
        if (model != null)
            return model;
        model = context.runtime().getModel(data.model);
        models.put(data.model, model);
        return model;
    }
    
    protected final void hide(ToolData data, String... fields) {
        setVisible(data, false, fields);
    }
    
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
        execute(this.context);
    }

    private final void setEnable(ToolData data, boolean enable,
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
    
    private final void setVisible(ToolData data, boolean visible,
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
    
    protected final void show(ToolData data, String... fields) {
        setVisible(data, true, fields);
    }
}
