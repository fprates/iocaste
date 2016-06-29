package org.iocaste.workbench.project.java.editor.handler;

public interface ClassHandler {
    public static final byte ADD = 0;
    public static final byte EDIT = 1;
    
    public abstract void execute();
    
    public abstract void setPackage(String packagename);
    
    public abstract void setClassName(String classname);
}
