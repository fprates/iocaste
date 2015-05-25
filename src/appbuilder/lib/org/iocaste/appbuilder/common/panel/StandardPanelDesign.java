package org.iocaste.appbuilder.common.panel;

import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.navcontrol.NavControlButton;
import org.iocaste.appbuilder.common.navcontrol.NavControlDesign;
import org.iocaste.appbuilder.common.style.CommonStyle;
import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.Text;

public class StandardPanelDesign implements NavControlDesign {
    private boolean offline;
    private Iocaste iocaste;
    
    public StandardPanelDesign() {
        offline = true;
    }
    
    @Override
    public void build(Container container, PageBuilderContext context) {
        Text text;
        Map<String, String> style;
        String name;
        StyleSheet stylesheet;
        CommonStyle profile;
        
        stylesheet = context.view.styleSheetInstance();
        profile = CommonStyle.get();
        
        style = stylesheet.newElement(".std_navcontrol_head");
        style.put("height", "70px");
        style.put("width", "100%"); 
        style.put("background-color", profile.head.bgcolor);
        style.put("margin", "0px");
        style.put("padding", "0px");
        style.put("left", "0px");
        style.put("top", "0px");
        style.put("position", "fixed");
        style.put("display", "block");
        style.put("border-bottom-style", "solid");
        style.put("border-bottom-width", "2px");
        style.put("border-bottom-color", profile.content.bgcolor);
        container.setStyleClass("std_navcontrol_head");
        
        style = stylesheet.newElement(".std_navcontrol_title");
        style.put("color", profile.head.font.color);
        style.put("margin", "0px");
        style.put("padding", "1em");
        style.put("bottom", "0px");
        style.put("left", "0px");
        style.put("position", "absolute");
        style.put("font-size", profile.head.font.size);
        style.put("font-family", profile.head.font.family);

        name = context.view.getTitle();
        if (offline) {
            if (iocaste == null)
                iocaste = new Iocaste(context.function);
            offline = !iocaste.isConnected();
        }
        
        if (!offline && (name == null)) {
            name = iocaste.getCurrentApp();
        }
        
        if (name == null)
            return;
        
        text = new Text(container, "navcontrol_title");
        text.setStyleClass("std_navcontrol_title");
        text.setText(name);
    }

    @Override
    public void buildButton(String action, NavControlButton button) {
        // TODO Stub de método gerado automaticamente
        
    }
}
