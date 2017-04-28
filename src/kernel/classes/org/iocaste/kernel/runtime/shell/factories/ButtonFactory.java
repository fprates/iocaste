package org.iocaste.kernel.runtime.shell.factories;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Container;

public class ButtonFactory extends AbstractSpecFactory {

    @Override
    protected void execute(ViewContext viewctx,
    		Container container, String parent, String name) {
        Button button = new Button(container, name);
        String action = button.getAction();
        Map<String, String> events = button.getEvents();

        if (!events.containsKey("click"))
        	viewctx.addEventHandler("click", action);
        for (String key : events.keySet())
        	viewctx.addEventHandler(key, action);
    }

}
