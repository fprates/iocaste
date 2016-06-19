package org.iocaste.shell.common;

public abstract class AbstractContext {
    public View view;
    public AbstractPage function;
    public String action, control;
    public Object[][] appbuildersheet;
    public MessageSource messages;
    
    public AbstractContext() {
        messages = new MessageSource();
    }
}
