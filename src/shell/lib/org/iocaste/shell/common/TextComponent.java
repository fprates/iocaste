package org.iocaste.shell.common;

public interface TextComponent extends Component {

    public abstract String getMask();
    
    public abstract String getTag();
    
    public abstract void setMask(String mask);
    
    public abstract void setTag(String tag);
}
