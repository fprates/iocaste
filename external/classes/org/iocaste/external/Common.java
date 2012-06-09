package org.iocaste.external;

import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.View;

public class Common {

    public static final <T> T getInput(View view, String name) {
        return ((InputComponent)view.getElement(name)).get();
    }
}
