package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.ExecAction;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Validator;
import org.iocaste.shell.common.ViewCustomAction;

public class BuilderCustomAction implements ViewCustomAction {
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
        InputComponent input;
        String error;
        PageBuilderContext _context;
        Map<String, Set<Validator>> validables;
        String page = context.view.getPageName();
        AbstractActionHandler handler = handlers.get(page).
                get(context.action);
        
        if (handler == null)
            throw new RuntimeException(
                    context.action.concat(" isn't a valid action."));

        components = ((PageBuilderContext)context).getView().getComponents();
        for (ComponentEntry entry : components.entries.values())
            entry.component.load(entry.data);
        handler.run(context);
        
        _context = (PageBuilderContext)context;
        validables = _context.getView(page).getValidables();
        for (String name : validables.keySet()) {
            input = context.view.getElement(name);
            if ((input == null) || !input.isEnabled())
                continue;
            
            for (Validator validator : validables.get(name)) {
                validator.clear();
                validator.setInput(input);
                validator.validate();
                error = validator.getMessage();
                if (error == null)
                    continue;
                context.view.setFocus(input);
                context.function.message(
                        Const.ERROR, ExecAction.getMessage(context, error));
            }
        }
    }
}
