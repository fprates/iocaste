package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;

public class TabbedPaneRenderer extends Renderer {
    
    /**
     * 
     * @param tabbedpane
     * @param config
     * @return
     */
    public static final XMLElement render(TabbedPane tabbedpane,
            Config config) {
        StandardContainer container;
        Button button;
        StringBuilder sb;
        String classname, name;
        TabbedPaneItem item;
        XMLElement tabitem, tabbedtag = new XMLElement("div");
        Set<Element> elements;
        
        tabbedtag.add("id", tabbedpane.getName());
        
        elements = tabbedpane.getElements();
        for (Element element : elements) {
            if (element.getType() != Const.TABBED_PANE_ITEM)
                continue;
            
            name = element.getName();
            sb = new StringBuilder("setElementDisplay('").append(name);
            
            if (tabbedpane.getCurrent().equals(name)) {
                sb.append(".tabitem', 'block');");
                classname = "tp_button_focused";
            } else {
                sb.append(".tabitem', 'none');");
                classname = "tp_button_unfocused";
            }
            config.addOnload(sb.toString());
            
            button = new Button(config.getView(), name.concat("_bt"));
            button.setText(name);
            button.setStyleClass(classname);
            button.setEventHandler(tabbedpane.getEventHandler());
            tabbedtag.addChild(ButtonRenderer.render(button, config));
        }

        for (Element element : elements) {
            if (element.getType() != Const.TABBED_PANE_ITEM)
                continue;
            
            name = element.getName();
            container = new StandardContainer(config.getView(),
                    new StringBuilder(name).append(".tabitem").toString());
            container.setStyleClass("tp_item");
            
            item = (TabbedPaneItem)element;
            tabitem = StandardContainerRenderer.render(container, config);
            tabitem.addChildren(renderTabbedPaneItem(item, config));
            
            tabbedtag.addChild(tabitem);
        }
        
        return tabbedtag;
    }
    
    /**
     * 
     * @param tabbedpaneitem
     * @param config
     * @return
     */
    private static final List<XMLElement> renderTabbedPaneItem(
            TabbedPaneItem tabbedpaneitem, Config config) {
        List<XMLElement> elements = new ArrayList<>();
        Container container = tabbedpaneitem.get();
        
        if (container != null)
            renderContainer(elements, container, config);
        
        return elements;
    }
}