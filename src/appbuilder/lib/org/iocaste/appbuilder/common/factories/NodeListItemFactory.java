package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.NodeList;
import org.iocaste.shell.common.NodeListItem;

public class NodeListItemFactory extends AbstractSpecFactory {

    @Override
    protected void execute(Container container, String parent, String name) {
        new NodeListItem((NodeList)container, name);
    }
}