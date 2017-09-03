package org.iocaste.shell.common;

public interface MultipageContainer extends Container {

    public abstract String getCurrentPage();
    
    public abstract void setCurrentPage(String name);
}
