package org.iocaste.runtime.common.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.runtime.common.ActionHandler;
import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.navcontrol.NavControl;
import org.iocaste.runtime.common.style.ViewConfigStyle;
import org.iocaste.shell.common.HeaderLink;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.ViewTitle;
import org.iocaste.shell.common.tooldata.ToolData;
import org.iocaste.shell.common.tooldata.ViewExport;

public abstract class AbstractPage {
    public ViewExport outputview;
    private Map<String, String> specalias;
    private Map<String, ToolData> entries;
    private Map<String, ActionHandler> handlers;
    private Set<String> actions;
    private String submit, entity;
    private Map<String, ChildPage> children;
    private ViewSpec spec;
    private ViewConfig config;
    private ViewInput input;
    private ViewConfigStyle style;
    private List<HeaderLink> links;
    private StyleSheet stylesheet;
    private ViewTitle title;
    private NavControl navcontrol;
    private Context context;
    private AbstractPage parent, root;
    private MessageSource messages;
    private int subpagessize;
    private boolean ready;
    
    public AbstractPage() {
        actions = new HashSet<>();
		outputview = new ViewExport();
        entries = new LinkedHashMap<>();
        links = new ArrayList<>();
        children = new LinkedHashMap<>();
        title = new ViewTitle();
        specalias = new HashMap<>();
        root = this;
    }
    
    protected void action(String action, ActionHandler handler) {
        actions.add(action);
        put(action, handler);
        navcontrol.add(action);
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
    
    protected abstract void execute() throws Exception;
    
    /**
     * 
     * @param action
     * @return
     */
    @SuppressWarnings("unchecked")
    public final <T extends ActionHandler> T getActionHandler(
            String action) {
        return (T)handlers.get(action);
    }
    
    public final Set<String> getActions() {
        return actions;
    }
    
    public final AbstractPage getChild(String name) {
        ChildPage cpage = children.get(name);
        return (cpage == null)? null : cpage.page;
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
    
    @SuppressWarnings("unchecked")
    protected final <C extends Context> C getContext() {
        return (C)context;
    }
    
    public final String getEntity() {
        return entity;
    }
    
    /**
     * 
     * @return
     */
    public final Map<String, ActionHandler> getHandlers() {
        return handlers;
    }
    
    public final ViewInput getInput() {
        return input;
    }
    
    public final HeaderLink[] getLinks() {
    	return links.toArray(new HeaderLink[0]);
    }
    
    public final MessageSource getMessages() {
        return messages;
    }
    
    public final NavControl getNavControl() {
    	return navcontrol;
    }
    
    public final AbstractPage getRoot() {
        return root;
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
    
    public final int getSubPagesSize() {
        return subpagessize;
    }
    
    public final ViewTitle getTitle() {
    	return title;
    }
    
	private final ToolData getToolData(String name) {
		ToolData tooldata;
		return ((tooldata = entries.get(name)) != null)?
				tooldata : entries.get(specalias.get(name));
	}
    
    public final ToolData instance(String tooldata) {
        ToolData data = getToolData(tooldata);
        if (data == null) {
            if (parent != null)
                return parent.instance(tooldata);
            add(data = new ToolData(null, tooldata));
        }
        return data;
    }
    
    public final boolean isReady() {
        return ready;
    }
    
	public final boolean isSubPage(String name) {
	    return children.get(name).subpage;
	}
	
    /**
     * 
     * @param action
     * @param handler
     */
    public final void put(String action, ActionHandler handler) {
        handlers.put(action, handler);
    }
    
    public final void put(String name, AbstractPage page) {
        page.set(this);
        children.put(name, new ChildPage(page, false));
    }
    
//    protected final void put(String name, AbstractExtendedValidator validator) {
//        view.put(name, validator);
//    }
    
    public final void run() throws Exception {
        if (parent == null) {
            handlers = new HashMap<>();
            navcontrol = new NavControl();
        } else {
            handlers = parent.getHandlers();
            navcontrol = parent.getNavControl();
        }
        execute();
    }
    
    protected final void run(String action) throws Exception {
        handlers.get(action).run(context);
    }
    
    public final void set(AbstractPage parent) {
        this.parent = parent;
        this.root = parent.getRoot();
    }
    
    public final void set(MessageSource messages) {
        this.messages = messages;
    }
    
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
    
    public final void set(Context context) {
        this.context = context;
    }
    
    public final void setEntity(String entity) {
        this.entity = entity;
    }
    
    public final void setReady(boolean ready) {
        this.ready = ready;
    }
    
    protected final void submit(String action, ActionHandler handler) {
        submit = action;
        put(action, handler);
        navcontrol.submit(action);
    }
    
    public final void subpage(String name, AbstractPage page) {
        if (root == this) {
            page.set(this);
            children.put(name, new ChildPage(page, true));
            subpagessize++;
        } else {
            root.subpage(name, page);
        }
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

class ChildPage {
    public AbstractPage page;
    public boolean subpage;
    
    public ChildPage(AbstractPage page, boolean subpage) {
        this.page = page;
        this.subpage = subpage;
    }
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