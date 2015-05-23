package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Element;
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
        Button button;
        StringBuilder sb;
        String classname, name;
        TabbedPaneItem item;
        List<XMLElement> tags;
        Set<Element> elements;
        XMLElement tabbedtag = new XMLElement("div");
        
        tabbedtag.add("id", tabbedpane.getName());
        tabbedtag.add("class", "tp_outer");
        
        elements = tabbedpane.getElements();
        for (Element element : elements) {
            if (element.getType() != Const.TABBED_PANE_ITEM)
                continue;
            
            item = (TabbedPaneItem)element;
            name = item.getName();
            sb = new StringBuilder("setElementDisplay('").append(name);
            
            if (tabbedpane.getCurrent().equals(name)) {
                sb.append("', 'block');");
                classname = "tp_button_focused";
            } else {
                sb.append("', 'none');");
                classname = "tp_button_unfocused";
            }
            
            config.addOnload(sb.toString());
            
            button = new Button(config.getView(), name.concat("_bt"));
            button.setText(name);
            button.setStyleClass(classname);
            button.setEventHandler(tabbedpane.getEventHandler());
            button.setEnabled(element.isEnabled());
            tabbedtag.addChild(ButtonRenderer.render(button, config));
        }

        tags = new ArrayList<>();
        for (Element element : elements) {
            if (element.getType() != Const.TABBED_PANE_ITEM)
                continue;
            
            item = (TabbedPaneItem)element;
            renderContainer(tags, item, config);
        }
        
        tabbedtag.addChildren(tags);
        return tabbedtag;
    }
}