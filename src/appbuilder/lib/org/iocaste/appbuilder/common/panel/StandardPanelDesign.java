package org.iocaste.appbuilder.common.panel;

import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.navcontrol.NavControlButton;
import org.iocaste.appbuilder.common.navcontrol.NavControlDesign;
import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.Text;

public class StandardPanelDesign implements NavControlDesign {
    private boolean offline;
    private Iocaste iocaste;
    private Map<Colors, String> colors;
    
    public StandardPanelDesign() {
        offline = true;
    }
    
    @Override
    public void build(Container container, PageBuilderContext context) {
        Text text;
        String style, name;
        StyleSheet stylesheet;
        
        style = ".std_navcontrol_head";
        stylesheet = context.view.styleSheetInstance();
        
        stylesheet.newElement(style);
        stylesheet.put(style, "height", "70px");
        stylesheet.put(style, "width", "100%"); 
        stylesheet.put(
                style, "background-color", colors.get(Colors.HEAD_BG));
        stylesheet.put(style, "margin", "0px");
        stylesheet.put(style, "padding", "0px");
        stylesheet.put(style, "left", "0px");
        stylesheet.put(style, "top", "0px");
        stylesheet.put(style, "position", "fixed");
        stylesheet.put(style, "display", "block");
        stylesheet.put(style, "font-name", "Verdana; sans-serif");
        stylesheet.put(style, "border-bottom-style", "solid");
        stylesheet.put(style, "border-bottom-width", "2px");
        stylesheet.put(
                style, "border-bottom-color", colors.get(Colors.CONTENT_BG));
        
        container.setStyleClass(style.substring(1));
        
        style = ".std_navcontrol_title";
        stylesheet.newElement(style);
        stylesheet.put(style, "color", colors.get(Colors.FONT));
        stylesheet.put(style, "margin", "0px");
        stylesheet.put(style, "padding", "1em");
        stylesheet.put(style, "bottom", "0px");
        stylesheet.put(style, "left", "0px");
        stylesheet.put(style, "position", "absolute");
        stylesheet.put(style, "font-size", "16pt");

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
        text.setStyleClass(style.substring(1));
        text.setText(name);
    }

    @Override
    public void buildButton(String action, NavControlButton button) {
        // TODO Stub de método gerado automaticamente
        
    }
    
    public final void setColors(Map<Colors, String> colors) {
        this.colors = colors;
    }
}
