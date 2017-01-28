package org.iocaste.login;

import org.iocaste.appbuilder.common.style.AbstractViewConfigStyle;

public class Style extends AbstractViewConfigStyle {

    @Override
    public void execute() {
        instance(".logincnt");
        put("border-style", "none");
        put("max-width", "300px");
        put("margin", "auto");
        
        clone(".loginform", ".form");
        put("width", "100%");
        
        clone(".loginsubmit", ".button");
        put("width", "100%");
        
        clone(".loginsubmit:hover", ".button:hover");
        put("width", "100%");
        
        load(".nc_title");
        put("text-align", "center");
        put("margin-left", "auto");
        put("margin-right", "auto");
        put("margin-top", "0px");
        put("margin-bottom", "0px");
        put("display", "block");
        remove("margin");
        
        forEachMedia(new TitleStyle());
    }
}

class TitleStyle extends AbstractViewConfigStyle {

    @Override
    public void execute() {
        load("#nc_inner_title");
        put("width", getMediaKey().startsWith("mobile")?
                "100%" : "calc(100% - 145px - 145px)");
    }
    
}
