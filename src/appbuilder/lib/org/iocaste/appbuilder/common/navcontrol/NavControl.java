package org.iocaste.appbuilder.common.navcontrol;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.StandardContainer;

public class NavControl {
    private static final byte NORMAL = 0;
    private static final byte SUBMIT = 1;
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
        buttons.put(action, new NavControlButton(NORMAL));
    }
    
    public final void build(PageBuilderContext context) {
        if (design == null)
            design = new StandardNavControlDesign();
        
        design.build(container, context);
        for (String name : buttons.keySet())
            switch (buttons.get(name).type) {
            case NORMAL:
                design.buildButton(name);
                break;
            case SUBMIT:
                design.buildSubmit(name);
                break;
            }
    }
    
    public final void setControlStyle(String control, String style) {
        context.view.getElement(control).setStyleClass(style);
    }
    
    public final void setDesign(NavControlDesign design) {
        this.design = design;
    }
    
    public final void setTitle(String title) {
        context.view.setTitle(title);
    }
    
    public final void submit(String action) {
        buttons.put(action, new NavControlButton(SUBMIT));
    }

}

class NavControlButton {
    public byte type;
    
    public NavControlButton(byte type) {
        this.type = type;
    }
}
