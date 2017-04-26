package org.iocaste.runtime.common.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.runtime.common.ActionHandler;
import org.iocaste.runtime.common.application.ToolData;
import org.iocaste.runtime.common.application.ViewExport;
import org.iocaste.runtime.common.navcontrol.NavControl;
import org.iocaste.runtime.common.style.StyleSheet;
import org.iocaste.runtime.common.style.ViewConfigStyle;
import org.iocaste.shell.common.HeaderLink;
import org.iocaste.shell.common.ViewTitle;

public abstract class AbstractPage {
    public ViewExport outputview;
    private Map<String, String> specalias;
    private Map<String, ToolData> entries;
    private Map<String, ActionHandler<?>> handlers;
    private Set<String> actions;
    private String submit;
    private Map<String, AbstractPage> children;
    private ViewSpec spec;
    private ViewConfig config;
    private ViewInput input;
    private ViewConfigStyle style;
    private List<HeaderLink> links;
    private StyleSheet stylesheet;
    private ViewTitle title;
    private NavControl navcontrol;
    
    public AbstractPage() {
        actions = new HashSet<>();
		outputview = new ViewExport();
        entries = new LinkedHashMap<>();
        handlers = new HashMap<>();
        links = new ArrayList<>();
        children = new LinkedHashMap<>();
        title = new ViewTitle();
        specalias = new HashMap<>();
        navcontrol = new NavControl();
    }
    
    protected void action(String action, ActionHandler<?> handler) {
        actions.add(action);
        put(action, handler);
    }
    
    public final void add(ToolData data) {
        entries.put(data.name, data);
    }
    
    public final void add(HeaderLink link) {
    	links.add(link);
    }
    
    public final void addSpecAlias(String alias, String spec) {
    	specalias.put(alias, spec);
    }
    
    public final void clearToolData() {
    	entries.clear();
    }
    
    public abstract void execute() throws Exception;
    
    /**
     * 
     * @param action
     * @return
     */
    @SuppressWarnings("unchecked")
    public final <T extends ActionHandler<?>> T getActionHandler(
            String action) {
        return (T)handlers.get(action);
    }
    
    public final Set<String> getActions() {
        return actions;
    }
    
    public final AbstractPage getChild(String name) {
    	return children.get(name);
    }
    
    public final Set<String> getChildren() {
    	return children.keySet();
    }
    
    public final ViewConfig getConfig() {
        return config;
    }
    
    public final ViewConfigStyle getConfigStyle() {
        return style;
    }
    
    /**
     * 
     * @return
     */
    public final Set<String> getHandlers() {
        return handlers.keySet();
    }
    
    public final ViewInput getInput() {
        return input;
    }
    
    public final HeaderLink[] getLinks() {
    	return links.toArray(new HeaderLink[0]);
    }
    
    public final NavControl getNavControl() {
    	return navcontrol;
    }
    
    public final ViewSpec getSpec() {
        return spec;
    }

	public final StyleSheet getStyleSheet() {
		return stylesheet;
	}

    public final void importStyle(Object[][] constants, Object[][] objects) {
		stylesheet = StyleSheet.instance(objects, constants);
    }
    
    public final String getSubmit() {
        return submit;
    }
	
    public final ViewTitle getTitle() {
    	return title;
    }
    
	public final ToolData getToolData(String name) {
		ToolData tooldata;
		return ((tooldata = entries.get(name)) != null)?
				tooldata : entries.get(specalias.get(name));
	}
    
    /**
     * 
     * @param action
     * @param handler
     */
    public final void put(String action, ActionHandler<?> handler) {
        handlers.put(action, handler);
    }
    
    public final void put(String name, AbstractPage page) {
    	children.put(name, page);
    }
//    
//    protected final void put(String name, AbstractExtendedValidator validator) {
//        view.put(name, validator);
//    }
//    
//    protected final void run(String action) throws Exception {
//        context.run(name, action);
//    }
    
    protected void set(ViewConfig config) {
        this.config = config;
    }
    
    protected void set(ViewInput input) {
        this.input = input;
    }
    
    protected void set(ViewSpec spec) {
        this.spec = spec;
    }
    
    protected final void set(ViewConfigStyle style) {
        this.style = style;
    }
    
    public final void set(StyleSheet stylesheet) {
    	this.stylesheet = stylesheet;
    }
    
    protected final void submit(String action, ActionHandler<?> handler) {
        submit = action;
        put(action, handler);
    }
//    
//    protected final void task(String action, String task) {
//        put(action, new TaskCall(task));
//    }
//    
//    protected final void update() {
//        view.setUpdate(true);
//    }
}
//
//class TaskCall extends AbstractActionHandler<Context> {
//    private String task;
//    
//    public TaskCall(String task) {
//        this.task = task;
//    }
//    
//    @Override
//    protected void execute(Context context) throws Exception {
//        taskredirect(task);
//    }
//    
//}