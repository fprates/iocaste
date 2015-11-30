package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.Link;

public class LinkFactory extends AbstractSpecFactory {

    @Override
    protected void execute() {
        new Link(container, name, name);
    }

}
