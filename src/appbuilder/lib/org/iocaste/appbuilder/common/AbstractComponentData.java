package org.iocaste.appbuilder.common;

public abstract class AbstractComponentData {
    public String name;
    public PageBuilderContext context;
    public ViewSpecItem.TYPES type;
    public AbstractComponentData nsdata;
    
    public AbstractComponentData(ViewSpecItem.TYPES type) {
        this.type = type;
    }
}
