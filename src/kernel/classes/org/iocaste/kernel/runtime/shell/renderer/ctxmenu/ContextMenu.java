package org.iocaste.kernel.runtime.shell.renderer.ctxmenu;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.EventHandler;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.NodeList;
import org.iocaste.shell.common.NodeListItem;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.tooldata.ToolData;
import org.iocaste.shell.common.tooldata.ViewSpecItem.TYPES;

public class ContextMenu {
    private NodeList options;
    private String itemstyle, title, container;
    private Config config;
    private List<NodeListItem> items;
    
    public ContextMenu(
            Config config, String title, String name) {
        this(config, null, title, name);
    }
    
    public ContextMenu(
            Config config, String container, String title, String name) {
        NodeListItem item;
        String tfcontext = name.concat("_menu");
        
        this.title = title;
        this.config = config;
        this.itemstyle = "ctxmenu_item";
        this.container = container;
        this.items = new ArrayList<>();
        
        renderOpenMenuButton(tfcontext, name);
        renderCloseMenuButton(tfcontext, name);
        
        item = getNodeItem(name.concat("_options"));
        item.addAttribute("style", "padding:0px;margin:0px;float:left");
        
        options = new NodeList(item, tfcontext);
        options.addAttribute("style", "display:none");
        options.setStyleClass("ctxmenu");
    }
    
    public final void add(String text) {
        String htmlname = new StringBuilder(options.getHtmlName()).
                append("_").append(options.getElements().size()).toString();
        get(htmlname).setText(text);
    }
    
    public final void add(String linkname,
            String ctrlname, String text, EventHandler handler) {
        renderItem(get(linkname), linkname, ctrlname, config, text, handler);
    }
    
    private final NodeListItem get(String name) {
        NodeListItem item;
        
        item = new NodeListItem(options, name.concat("_option"));
        item.setStyleClass(itemstyle);
        return item;
    }
    
    public final List<NodeListItem> getElements() {
        return items;
    }
    
    private final String getMessage(String id) {
        String text;
        return ((text = config.viewctx.messagesrc.get(id)) == null)?
                id : text;
    }
    
    private final NodeListItem getNodeItem(String name) {
        ToolData tooldata;
        NodeListItem item;
        
        tooldata = config.viewctx.instance(TYPES.NODE_LIST_ITEM, name);
        tooldata.parent = container;
        item = new NodeListItem(config.viewctx, name);
        if (tooldata.parent == null)
            tooldata.tag = "li";
        items.add(item);
        return item;
    }
    
    private final void renderCloseMenuButton(String menu, String name) {
        NodeListItem item;
        String open = name.concat("_openmenu");
        String close = name.concat("_closemenu");

        item = getNodeItem(close);
        item.setStyleClass("button_ctxmenu_close");
        item.addAttribute("style", "display:none");
        item.addAttribute("onclick", new StringBuilder(
                Shell.setElementDisplay(menu, "none")).
                append(Shell.setElementDisplay(open, "inline")).
                append(Shell.setElementDisplay(close, "none")).toString());
        item.setText("-");
    }
    
    private final void renderItem(NodeListItem container, String linkname,
           String ctrlname, Config config, String text, EventHandler handler) {
        ControlComponent control;
        String onclick;
        Link link;
        ToolData tooldata;
        
        onclick = new StringBuilder("javascript:").append(Shell.formSubmit(
            config.getCurrentForm(), config.getCurrentAction(), ctrlname)).
                toString();
        
        tooldata = config.viewctx.instance(TYPES.LINK, linkname);
        tooldata.parent = container.getHtmlName();
        
        link = new Link(config.viewctx, linkname);
        link.setAction(onclick);
        link.setStyleClass("ctxmenu_link");
        link.setText(getMessage(text));
        link.setAbsolute(true);
        
        control = config.viewctx.view.getElement(ctrlname);
        control.put("click", handler);
    }
    
    private final void renderOpenMenuButton(String menu, String name) {
        NodeListItem item;
        String open = name.concat("_openmenu");
        String close = name.concat("_closemenu");

        item = getNodeItem(open);
        item.setStyleClass("button_ctxmenu_open");
        item.addAttribute("style", "display:inline");
        item.addAttribute("onclick", new StringBuilder(
                Shell.setElementDisplayOfClass(".ctxmenu", "none")).
                append(Shell.setElementDisplayOfClass(
                        ".button_ctxmenu_close", "none")).
                append(Shell.setElementDisplayOfClass(
                        ".button_ctxmenu_open", "inline")).
                append(Shell.setElementDisplay("nc_login_options", "none")).
                append(Shell.setElementDisplay(menu, "inline-block")).
                append(Shell.setElementDisplay(open, "none")).
                append(Shell.setElementDisplay(close, "inline")).toString());
        if (title != null)
            item.addAttribute("title", getMessage(title));
        item.setText("+");
    }
    
    public final void setItemStyle(String itemstyle) {
        this.itemstyle = itemstyle;
    }
}
