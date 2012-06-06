package org.iocaste.external;

import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.ViewData;

public class Common {

    public static final <T> T getInput(ViewData view, String name) {
        return ((InputComponent)view.getElement(name)).get();
    }
}
