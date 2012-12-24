package org.iocaste.internal.renderer;

import org.iocaste.internal.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.View;

public class PageControlRenderer extends Renderer {

    /**
     * 
     * @param container
     * @param name
     * @param action
     * @param config
     */
    private static final void addAction(Container container, String action,
            Config config, boolean extern) {
        Link link = new Link(container, action, action);
        
        link.setEnabled(true);
        link.setCancellable(true);
        link.setAllowStacking(extern);
        link.setStyleClass("navbar_link");
        link.setText(config.getText(action, action));
    }
    
    /**
     * 
     * @param text
     */
    private static final void setMessage(Text message, Const msgtype,
            String text) {
        message.setVisible(text != null);
        message.setText(text);
        
        switch (msgtype) {
        case ERROR:
            message.setStyleClass("error_message");
            break;
        case WARNING:
            message.setStyleClass("warning_message");
            break;
        case STATUS:
            message.setStyleClass("status_message");
            break;
        default:
            break;
        }
    }
    
    /**
     * 
     * @param pagecontrol
     * @param config
     * @return
     */
    public static final XMLElement render(
            PageControl pagecontrol, Config config) {
        String[] actions;
        StandardContainer linkarea;
        StandardContainer statusarea;
        XMLElement pctag;
        Const msgtype;
        Text text;
        View view = config.getView();
        String message, title = view.getTitle();
        
        pagecontrol.clear();
        
        actions = pagecontrol.getActions();
        if (actions.length > 0) {
            linkarea = new StandardContainer(pagecontrol, "navbar.linkarea");
            for (String action : actions)
                addAction(linkarea, action, config,
                        pagecontrol.isExternal(action));
        }
        
        statusarea = new StandardContainer(pagecontrol, "navbar.status");

        pctag = new XMLElement("div");
        pctag.add("class", pagecontrol.getStyleClass());
        
        text = new Text(statusarea, "navbar.title");
        text.setStyleClass("title");
        text.setText((title == null)? view.getAppName() : title);
        text.setTag("h1");
        
        text = new Text(statusarea, "navbar.username");
        text.setStyleClass("status");
        text.setText(new StringBuilder(config.getUsername()).append("@").
                append(config.getDBName()).append(":").
                append(config.getLogId()).toString());
        
        message = config.getMessage();
        if (message != null) {
            text = new Text(pagecontrol, "navbar.message");
            msgtype = config.getMessageType();
            setMessage(text, (msgtype == null)? Const.STATUS : msgtype,
                    message);
        }
        
        pctag.addChildren(Renderer.renderElements(
                pagecontrol.getElements(), config));
        
        return pctag;
    }
}
