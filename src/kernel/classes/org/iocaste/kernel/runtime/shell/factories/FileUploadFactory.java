package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.FileEntry;
import org.iocaste.shell.common.InputComponent;

public class FileUploadFactory extends AbstractSpecFactory {

    @Override
    public final void addEventHandler(ViewContext viewctx, String htmlname) {
        if (viewctx.view.getElement(htmlname).isVisible())
            viewctx.addEventHandler(htmlname, htmlname, "focus");
    }

    @Override
    protected void execute(ViewContext viewctx,
    		Container container, String parent, String name) {
        InputComponent input = new FileEntry(viewctx, name);
        if (input.hasMultipartSupport())
            viewctx.files.add(new String[] {input.getHtmlName(), name, null});
    }

}
