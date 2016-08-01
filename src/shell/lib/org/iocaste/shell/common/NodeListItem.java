package org.iocaste.shell.common;


public class NodeListItem extends AbstractContainer {
    private static final long serialVersionUID = 7511141074610492313L;
    
    public NodeListItem(NodeList container, String name) {
        super(container, Const.NODE_LIST_ITEM, name);
    }
}