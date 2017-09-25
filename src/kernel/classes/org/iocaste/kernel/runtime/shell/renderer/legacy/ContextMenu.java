package org.iocaste.kernel.runtime.shell.renderer.legacy;

import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.protocol.utils.XMLElement;

public class ContextMenu {
    private XMLElement options;
    private String itemstyle, title;
    private Config config;
    
    public ContextMenu(
            Config config, String title, XMLElement tagt, String name) {
        String tfcontext;
        XMLElement tag;

        this.title = title;
        this.config = config;
        tfcontext = name.concat("_menu");
        tag = new XMLElement("li");
        renderOpenMenuButton(tag, tfcontext, name);
        tagt.addChild(tag);
        tag = new XMLElement("li");
        renderCloseMenuButton(tag, tfcontext, name);
        tagt.addChild(tag);
        tag = new XMLElement("li");
        tag.add("style", "padding:0px;margin:0px;float:left");
        options = new XMLElement("ul");
        options.add("id", tfcontext);
        options.add("style", "display:none");
        options.add("class", "ctxmenu");
        tag.addChild(options);
        tagt.addChild(tag);
        itemstyle = "ctxmenu_item";
    }
    
    public final void add(String text) {
        XMLElement tag;
        
        tag = new XMLElement("li");
        tag.add("class", itemstyle);
        tag.addInner(text);
        options.addChild(tag);
    }
    
    public final void add(XMLElement element) {
        XMLElement tag;
        
        tag = new XMLElement("li");
        tag.add("class", itemstyle);
        tag.addChild(element);
        options.addChild(tag);
    }
    
    public final void add(String htmlname, String text) {
        add(ContextMenuButtonRenderer.
                render(htmlname, config, text));
    }
    
    public static final String getMessage(Config config, String id) {
        String text;
        return ((text = config.viewctx.messagesrc.get(id)) == null)?
                id : text;
    }
    
    private final void renderCloseMenuButton(XMLElement button,
            String menu, String name) {
        String open = name.concat("_openmenu");
        String close = name.concat("_closemenu");
        
        button.add("id", close);
        button.add("class", "button_ctxmenu_close");
        button.add("style", "display:none");
        button.add("onclick", new StringBuilder(
                setElementDisplay(menu, "none")).
                append(setElementDisplay(open, "inline")).
                append(setElementDisplay(close, "none")).toString());
        button.addInner("-");
    }
    
    private final void renderOpenMenuButton(XMLElement button,
            String menu, String name) {
        String open = name.concat("_openmenu");
        String close = name.concat("_closemenu");
        
        button.add("id", open);
        button.add("class", "button_ctxmenu_open");
        button.add("style", "display:inline");
        button.add("onclick", new StringBuilder(
                setElementDisplayOfClass(".ctxmenu", "none")).
                append(setElementDisplayOfClass(
                        ".button_ctxmenu_close", "none")).
                append(setElementDisplayOfClass(
                        ".button_ctxmenu_open", "inline")).
                append(setElementDisplay("nc_login_options", "none")).
                append(setElementDisplay(menu, "inline-block")).
                append(setElementDisplay(open, "none")).
                append(setElementDisplay(close, "inline")).toString());
        if (title != null)
            button.add("title", getMessage(config, title));
        button.addInner("+");
    }
    
    private final String setElementDisplay(String name, String value) {
        return new StringBuilder("setElementDisplay('").append(name).
                append("','").append(value).append("');").toString();
    }

    private final String setElementDisplayOfClass(
            String style, String display) {
        return new StringBuilder("setElementDisplayOfClass('").
                append(style).append("','").append(display).append("');").
                toString();
    }
    
    public final void setItemStyle(String itemstyle) {
        this.itemstyle = itemstyle;
    }
}
