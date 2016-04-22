package org.iocaste.appbuilder.common;

public interface ExtendedContext {
    
    public abstract PageContext getPageContext();
    
    public abstract void pageInstance();
    
    public abstract void pageInstance(String page);

}
