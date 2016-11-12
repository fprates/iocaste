package org.iocaste.appbuilder.common.panel;

import java.util.HashSet;
import java.util.Set;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.AbstractExtendedValidator;
import org.iocaste.appbuilder.common.ActionHandler;
import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewConfig;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.appbuilder.common.ViewInput;
import org.iocaste.appbuilder.common.ViewSpec;
import org.iocaste.appbuilder.common.navcontrol.NavControlDesign;
import org.iocaste.appbuilder.common.style.ViewConfigStyle;

public abstract class AbstractPanelPage {
    private ViewContext view;
    public ViewSpec spec;
    public ViewConfig config;
    public ViewInput input;
    private Set<String> actions;
    private String submit;
    private ViewConfigStyle style;
    private NavControlDesign ncdesign;
    
    public AbstractPanelPage() {
        actions = new HashSet<>();
    }
    
    protected void action(String action, ActionHandler handler) {
        actions.add(action);
        put(action, handler);
    }
    
    public abstract void execute() throws Exception;
    
    public final Set<String> getActions() {
        return actions;
    }
    
    public final ViewConfig getConfig() {
        return config;
    }
    
    public final NavControlDesign getDesign() {
        return ncdesign;
    }
    
    protected final <T extends ExtendedContext> T getExtendedContext() {
        return view.getExtendedContext();
    }
    
    public final ViewInput getInput() {
        return input;
    }
    
    public final ViewSpec getSpec() {
        return spec;
    }
    
    public final ViewConfigStyle getConfigStyle() {
        return style;
    }
    
    public final String getSubmit() {
        return submit;
    }
    
    protected final void put(String action, ActionHandler handler) {
        view.put(action, handler);
    }
    
    protected final void put(String name, AbstractExtendedValidator validator) {
        view.put(name, validator);
    }
    
    protected final void set(ViewConfig config) {
        this.config = config;
    }
    
    protected final void set(ViewInput input) {
        this.input = input;
    }
    
    protected final void set(ViewSpec spec) {
        this.spec = spec;
    }
    
    protected final void set(ViewConfigStyle style) {
        this.style = style;
    }
    
    protected final void set(NavControlDesign ncdesign) {
        this.ncdesign = ncdesign;
    }
    
    public final void setViewContext(ViewContext view) {
        this.view = view;
    }
    
    protected final void submit(String action, ActionHandler handler) {
        submit = action;
        put(action, handler);
    }
    
    protected final void task(String action, String task) {
        put(action, new TaskCall(task));
    }
    
    protected final void update() {
        view.setUpdate(true);
    }
}

class TaskCall extends AbstractActionHandler {
    private String task;
    
    public TaskCall(String task) {
        this.task = task;
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        taskredirect(task);
    }
    
}