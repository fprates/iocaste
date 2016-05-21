package org.iocaste.appbuilder.common.navcontrol;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.StandardContainer;

public class NavControl {
    public static final byte NORMAL = 0;
    public static final byte SUBMIT = 1;
    private PageBuilderContext context;
    private Container container;
    private NavControlDesign design;
    private Map<String, NavControlButton> buttons;
    
    public NavControl(Form container, PageBuilderContext context) {
        this.context = context;
        this.container = new StandardContainer(container, "navcontrol_cntnr");
        buttons = new LinkedHashMap<>();
    }
    
    public final void add(String action) {
        NavControlButton button;
        
        button = new NavControlButton();
        button.type = NORMAL;
        buttons.put(action, button);
    }
    
    public final void build(PageBuilderContext context) {
        if (design == null)
            design = new StandardNavControlDesign();
        
        design.build(container, context);
        for (String name : buttons.keySet())
            design.buildButton(name, buttons.get(name));
    }
    
    @SuppressWarnings("unchecked")
    public final <T extends NavControlDesign> T getDesign() {
        return (T)design;
    }
    
    public final void noScreenLockFor(String... actions) {
        for (String action : actions)
            buttons.get(action).nolock = true;
    }
    
    public final void setDesign(NavControlDesign design) {
        this.design = design;
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
