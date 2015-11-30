package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.NodeList;

public class NodeListFactory extends AbstractSpecFactory {

    @Override
    protected void execute() {
        new NodeList(container, name);
    }

}
