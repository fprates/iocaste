package org.iocaste.appbuilder.common.style;

public class CommonStyleBorder {
    public CommonStyleBorderItem all, top, bottom, left, right;
    
    public CommonStyleBorder() {
        all = new CommonStyleBorderItem();
        top = new CommonStyleBorderItem();
        bottom = new CommonStyleBorderItem();
        left = new CommonStyleBorderItem();
        right = new CommonStyleBorderItem();
    }
}