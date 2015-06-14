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
import org.iocaste.shell.common.View;

public class TabbedPaneRenderer extends Renderer {
    
    /**
     * 
     * @param tabbedpane
     * @param config
     * @return
     */
    public static final XMLElement render(TabbedPane tabbedpane,
            Config config) {
        View view;
        Button button;
        String classname, name, text, current;
        TabbedPaneItem item;
        List<XMLElement> tags;
        Set<Element> elements;
        XMLElement tabbedtag = new XMLElement("div");
        
        tabbedtag.add("id", tabbedpane.getName());
        tabbedtag.add("class", "tp_outer");
        
        elements = tabbedpane.getElements();
        current = tabbedpane.getCurrentPage();
        view = config.getView();
        for (Element element : elements) {
            if (element.getType() != Const.TABBED_PANE_ITEM)
                continue;
            
            item = (TabbedPaneItem)element;
            name = item.getName();
            text = item.getText();
            
            classname = current.equals(name)?
                    "tp_button_focused" : "tp_button_unfocused";
            
            button = new Button(view, name.concat("_bt"));
            button.setText(text);
            button.setStyleClass(classname);
            button.setEventHandler(tabbedpane.getEventHandler());
            button.setEnabled(element.isEnabled());
            tabbedtag.addChild(ButtonRenderer.render(button, config));
        }

        tags = new ArrayList<>();
        item = view.getElement(current);
        renderContainer(tags, item, config);
        
        tabbedtag.addChildren(tags);
        return tabbedtag;
    }
}