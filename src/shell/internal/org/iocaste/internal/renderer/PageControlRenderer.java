package org.iocaste.internal.renderer;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Button;
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
     * @param pagecontrol
     * @param config
     * @return
     */
    public static final XMLElement render(
            PageControl pagecontrol, Config config) {
        Button button;
        String[] actions, components;
        StandardContainer linkarea, statusarea, componentarea;
        XMLElement pctag;
        Text text;
        View view = config.getView();
        String submit, title = view.getTitle();
        
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
        text.setText(config.getUsername());

        components = pagecontrol.getComponents();
        if (components.length > 0) {
            componentarea = new StandardContainer(pagecontrol,
                    "navbar.components");
            submit = pagecontrol.getSubmitComponent();
            componentarea.setStyleClass("navbar_components");
            for (String component : components) {
                button = new Button(componentarea, component);
                if (submit != null && submit.equals(component))
                    button.setSubmit(true);
                
                componentarea.add(button);
            }
        }
        
        pctag.addChildren(Renderer.renderElements(
                pagecontrol.getElements(), config));
        
        return pctag;
    }
}
