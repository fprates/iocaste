package org.iocaste.shell;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.renderer.Config;

public class NavigationBar implements Serializable {
    private static final long serialVersionUID = -3075185426719189481L;
    private Map<String, Link> bar;
    private Container linkarea;
    private Container statusarea;
    private Text message;
    private Config config;
    
    public NavigationBar(Container container, Config config) {
        bar = new LinkedHashMap<String, Link>();
        linkarea = new StandardContainer(container, "navbar.linkarea");
        statusarea = new StandardContainer(container, "navbar.status");
        message = new Text(container, "navbar.message");
        this.config = config;
        
        addAction("home", "home");
        addAction("back", "back");
        addAction("help", "help");
        
        setHelpControl("help", true);
    }

    /**
     * 
     * @param name
     * @param action
     */
    public void addAction(String name, String action) {
        Link link = new Link(linkarea, name, action);
        
        link.setEnabled(false);
        link.setCancellable(true);
        link.setAllowStacking(false);
        link.setStyleClass("navbar_link");
        link.setText(config.getText(name, name));
        
        bar.put(name, link);
    }
    
    /**
     * 
     * @return
     */
    public final Container getStatusArea() {
        return statusarea;
    }
    
    /**
     * 
     * @param name
     * @param enabled
     */
    public final void setCancellable(String name, boolean cancellable) {
        bar.get(name).setCancellable(cancellable);
    }
    
    /**
     * 
     * @param enabled
     * @param active
     */
    public final void setEnabled(String name, boolean enabled) {
        bar.get(name).setEnabled(enabled);
    }
    
    /**
     * 
     * @param name
     * @param helpcontrol
     */
    public final void setHelpControl(String name, boolean helpcontrol) {
        bar.get(name).setAllowStacking(helpcontrol);
    }
    
    /**
     * 
     * @param text
     */
    public final void setMessage(Const msgtype, String text) {
        message.setVisible((text == null)? false : true);
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
}
