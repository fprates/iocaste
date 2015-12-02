package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Link;

public class LinkFactory extends AbstractSpecFactory {

    @Override
    protected void execute(Container container, String parent, String name) {
        new Link(container, name, name);
    }

}
