package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.NodeList;

public class NodeListFactory extends AbstractSpecFactory {

    @Override
    protected void execute(Container container, String parent, String name) {
        new NodeList(container, name);
    }

}
