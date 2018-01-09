package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.TextArea;

public class TextEditorFactory extends AbstractSpecFactory {

    @Override
    public final void addEventHandler(ViewContext viewctx, String htmlname) {
        if (viewctx.view.getElement(htmlname).isVisible())
            viewctx.addEventHandler(htmlname, htmlname, "focus");
    }

    @Override
    protected void execute(ViewContext viewctx,
    		Container container, String parent, String name) {
        new TextArea(viewctx, name);
    }

}
