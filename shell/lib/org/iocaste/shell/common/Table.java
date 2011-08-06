package org.iocaste.shell.common;

public class Table extends AbstractContainer {
    private static final long serialVersionUID = -245959547368570624L;
    private int cols;
    
    public Table(Container container, int cols, String name) {
        super(container, Const.TABLE, name);
        this.cols = cols;
    }
    
    public final int getWidth() {
        return cols;
    }
}
