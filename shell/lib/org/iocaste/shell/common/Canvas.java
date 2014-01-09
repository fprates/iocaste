package org.iocaste.shell.common;

import org.iocaste.shell.common.AbstractComponent;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;

public class Canvas extends AbstractComponent {
    private static final long serialVersionUID = 5356998094110609194L;
    private String content;
    
    public Canvas(Container container, String name) {
        super(container, Const.CANVAS, name);
    }

    public final String get() {
        return content;
    }
    
    @Override
    public boolean isControlComponent() {
        return false;
    }

    @Override
    public boolean isDataStorable() {
        return false;
    }

    public final void set(String content) {
        this.content = content;
    }
}
