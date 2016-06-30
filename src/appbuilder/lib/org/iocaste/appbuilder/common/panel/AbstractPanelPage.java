package org.iocaste.appbuilder.common.panel;

import java.util.HashSet;
import java.util.Set;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewConfig;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.appbuilder.common.ViewInput;
import org.iocaste.appbuilder.common.ViewSpec;

public abstract class AbstractPanelPage {
    private ViewContext view;
    private ViewSpec spec;
    private ViewConfig config;
    private ViewInput input;
    private Set<String> actions;
    private String submit;
    
    public AbstractPanelPage() {
        actions = new HashSet<>();
    }
    
    protected void action(String action, AbstractActionHandler handler) {
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
    
    protected final <T extends ExtendedContext> T getExtendedContext() {
        return view.getExtendedContext();
    }
    
    public final ViewInput getInput() {
        return input;
    }
    
    public final ViewSpec getSpec() {
        return spec;
    }
    
    public final String getSubmit() {
        return submit;
    }
    
    protected final void put(String action, AbstractActionHandler handler) {
        view.put(action, handler);
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
    
    public final void setViewContext(ViewContext view) {
        this.view = view;
    }
    
    protected final void submit(String action, AbstractActionHandler handler) {
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