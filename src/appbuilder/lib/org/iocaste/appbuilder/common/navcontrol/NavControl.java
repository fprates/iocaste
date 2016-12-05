package org.iocaste.appbuilder.common.navcontrol;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Form;

public class NavControl {
    public static final byte NORMAL = 0;
    public static final byte SUBMIT = 1;
    private PageBuilderContext context;
    private Map<String, NavControlButton> buttons;
    
    public NavControl(Form container, PageBuilderContext context) {
        this.context = context;
        buttons = new LinkedHashMap<>();
    }
    
    public final void add(String action) {
        NavControlButton button;
        
        button = new NavControlButton();
        button.type = NORMAL;
        buttons.put(action, button);
    }
    
    public final NavControlButton get(String action) {
        return buttons.get(action);
    }
    
    public final void noScreenLockFor(String... actions) {
        for (String action : actions)
            buttons.get(action).nolock = true;
    }
    
    public final void setTitle(String title, Object... args) {
        context.view.setTitle(title, args);
    }
    
    public final void submit(String action) {
        NavControlButton button;
        
        button = new NavControlButton();
        button.type = SUBMIT;
        buttons.put(action, button);
    }
}
