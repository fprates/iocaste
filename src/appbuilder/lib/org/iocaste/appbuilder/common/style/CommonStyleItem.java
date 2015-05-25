package org.iocaste.appbuilder.common.style;

public class CommonStyleItem {
    public CommonStyleFont font;
    public CommonStyleBorder border;
    public String itembgcolor, bgcolor, focusbgcolor;
    public String actionbgcolor, groupbgcolor, shade;
    public String width, height, overflow, position, margin;
    
    public CommonStyleItem() {
        font = new CommonStyleFont();
        border = new CommonStyleBorder();
    }
}
