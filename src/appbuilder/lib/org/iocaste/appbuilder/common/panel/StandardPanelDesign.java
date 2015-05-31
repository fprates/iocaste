package org.iocaste.appbuilder.common.panel;

import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.navcontrol.NavControlButton;
import org.iocaste.appbuilder.common.navcontrol.NavControlCustomAction;
import org.iocaste.appbuilder.common.navcontrol.NavControlDesign;
import org.iocaste.appbuilder.common.navcontrol.StandardNavControlDesign;
import org.iocaste.appbuilder.common.style.CommonStyle;
import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.PageStackItem;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.Text;

public class StandardPanelDesign implements NavControlDesign {
    private boolean offline;
    private Iocaste iocaste;
    private String submit, loginapp, backaction;
    private Shell shell;
    
    public StandardPanelDesign() {
        offline = true;
    }
    
    @Override
    public void build(Container container, PageBuilderContext context) {
        Button button;
        Link link;
        Text text;
        Map<String, String> style;
        String name, address;
        boolean backrule;
        StyleSheet stylesheet;
        CommonStyle profile;
        PageStackItem position;
        PageStackItem[] items;
        
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
        
        name = context.view.getTitle();
        if (offline) {
            if (iocaste == null)
                iocaste = new Iocaste(context.function);
            offline = !iocaste.isConnected();
        }
        
        if (!offline && (name == null))
            name = iocaste.getCurrentApp();
        
        if (name == null)
            return;
        
        if (!offline) {
            if (shell == null)
                shell = new Shell(context.function);
            
            items = shell.getPagesPositions();
            if (loginapp == null)
                loginapp = shell.getLoginApp();
            
            backrule = (items.length == 1) && !StandardNavControlDesign.
                    getAddress(items[0]).equals(loginapp);
            backrule = backrule || (items.length > 1);
            if (backrule || (backaction != null)) {
                style = stylesheet.newElement(".std_navcontrol_back");
                style.put("width", "128px");
                style.put("height", "128px");
                style.put("top", "-30px");
                style.put("left", "-30px");
                style.put("position", "absolute");

                if (backaction == null) {
                    position = items[items.length - 1];
                    address = StandardNavControlDesign.getAddress(position);
                } else {
                    address = backaction;
                }
                
                link = new Link(container, address, address);
                link.setImage("/iocaste-shell/images/back.svg");
                link.setStyleClass("std_navcontrol_back");
                link.setCancellable(true);
                
                context.function.register(
                        address, new NavControlCustomAction(address));
            }
            
            if ((submit != null) && (submit.equals("validate"))) {
                style = stylesheet.newElement(".std_navcontrol_validate");
                style.put("margin", "0px");
                style.put("display", "inline");
                style.put("float", "right");
                style.put("top", "0px");
                style.put("right", "0px");
                style.put("width", "80px");
                style.put("height", "70px");
                style.put("position", "relative");
                style.put("color", "transparent");
                style.put("background-color", "transparent");
                style.put("border-style", "none");
                
                button = new Button(container, submit);
                button.setSubmit(true);
                button.setStyleClass("std_navcontrol_validate");
            }
        }
        
        style = stylesheet.newElement(".std_navcontrol_title");
        style.put("color", profile.head.font.color);
        style.put("margin", "0px");
        style.put("padding", "1em");
        style.put("bottom", "0px");
        style.put("left", "80px");
        style.put("position", "absolute");
        style.put("font-size", profile.head.font.size);
        style.put("font-family", profile.head.font.family);
        
        text = new Text(container, "navcontrol_title");
        text.setStyleClass("std_navcontrol_title");
        text.setText(name);
        context.view.setTitle(name);
    }

    @Override
    public void buildButton(String action, NavControlButton button) { }
    
    public final void setBackAction(String backaction) {
        this.backaction = backaction;
    }
    
    public final void setSubmit(String submit) {
        this.submit = submit;
    }
}
