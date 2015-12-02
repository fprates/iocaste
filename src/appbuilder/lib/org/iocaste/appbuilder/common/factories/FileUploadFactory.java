package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.FileEntry;

public class FileUploadFactory extends AbstractSpecFactory {

    @Override
    protected void execute(Container container, String parent, String name) {
        new FileEntry(container, name);
    }

}
