package org.iocaste.shell.common;

public class CustomComponent extends AbstractComponent {
    private static final long serialVersionUID = 7689711993523839095L;
    private String renderurl;
    
    public CustomComponent(Container container, String name) {
        super(container, Const.CUSTOM_COMPONENT, name);
    }

    public final String getRenderURL() {
        return renderurl;
    }
    
    @Override
    public boolean isControlComponent() {
        return false;
    }

    @Override
    public boolean isDataStorable() {
        return false;
    }

    public final void setRenderURL(String url) {
        renderurl = url;
    }
}
