package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.TextField;

public class TextFieldFactory extends AbstractSpecFactory {

    @Override
    protected void execute(ViewContext viewctx,
    		Container container, String parent, String name) {
        TextField textfield = new TextField(container, name);
        viewctx.addEventHandler("focus", textfield.getHtmlName());
    }

}
