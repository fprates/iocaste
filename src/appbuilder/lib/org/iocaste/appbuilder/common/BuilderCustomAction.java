package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.ViewCustomAction;

public class BuilderCustomAction implements ViewCustomAction {
    private static final long serialVersionUID = 2367760748660650540L;
    private Map<String, Map<String, AbstractActionHandler>> handlers;
    
    public BuilderCustomAction() {
        handlers = new HashMap<>();
    }
    
    public final void addHandler(String view,
            String action, AbstractActionHandler handler) {
        Map<String, AbstractActionHandler> actions = handlers.get(view);
        
        if (actions == null) {
            actions = new HashMap<>();
            handlers.put(view, actions);
        }
        
        actions.put(action, handler);
    }

    @Override
    public final void execute(AbstractContext context) throws Exception {
        ViewComponents components;
        String view = context.view.getPageName();
        AbstractActionHandler handler = handlers.get(view).
                get(context.action);
        
        if (handler == null)
            throw new RuntimeException(
                    context.action.concat(" isn't a valid action."));

        components = ((PageBuilderContext)context).getView().getComponents();
        for (ComponentEntry entry : components.entries.values())
            entry.component.load(entry.data);
        handler.run(context);
    }
}
