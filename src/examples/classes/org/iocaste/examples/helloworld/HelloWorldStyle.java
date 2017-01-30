package org.iocaste.examples.helloworld;

import org.iocaste.appbuilder.common.style.AbstractViewConfigStyle;

public class HelloWorldStyle extends AbstractViewConfigStyle {

    @Override
    public void execute() {
        clone(".hello_text", ".text");
        put("font-size", "64pt");
        put("background-image", "linear-gradient("
                + "to right, #ff0000 30%, #00ff00, #0000ff 70%);");
        put("background-clip", "text");
        put("text-align", "center");
        put("color", "transparent");
    }
    
}