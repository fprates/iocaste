package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.internal.XMLElement;
import org.iocaste.shell.common.Button;
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
        String classname;
        XMLElement tabitem, tabbedtag = new XMLElement("div");
        String[] names = tabbedpane.getItensNames();
        
        tabbedtag.add("id", tabbedpane.getName());
        
        for (String name : names) {
            sb = new StringBuilder("setElementDisplay('").append(name);
            
            if (tabbedpane.getCurrent().equals(name)) {
                sb.append(".tabitem', 'block');");
                classname = "tp_button_focused";
            } else {
                sb.append(".tabitem', 'none');");
                classname = "tp_button_unfocused";
            }
            config.addOnload(sb.toString());
            
            button = new Button(tabbedpane, name);
            button.setStyleClass(classname);
            button.setSubmit(false);
            button.setEventHandler(tabbedpane.getHandler());
            
            sb = new StringBuilder();
            
            for (String name_ : names) {
                sb.append("setElementDisplay('").append(name_);
                
                if (name.equals(name_))
                    sb.append(".tabitem', 'block'); setClassStyle('").
                            append(name_).append("', 'tp_button_focused'); ");
                else
                    sb.append(".tabitem', 'none'); setClassStyle('").
                            append(name_).append("', 'tp_button_unfocused'); ");
            }
            
            sb.append("send('").append(name).append("', null, null)");
            button.addEvent("onClick", sb.toString());
            tabbedtag.addChild(ButtonRenderer.render(button, config));
        }
        
        for (String name : names) {
            container = new StandardContainer(tabbedpane,
                    new StringBuilder(name).append(".tabitem").toString());
            container.setStyleClass("tp_item");
            
            tabitem = StandardContainerRenderer.render(container, config);
            tabitem.addChildren(renderTabbedPaneItem(tabbedpane.get(name),
                    config));
            
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
        List<XMLElement> elements = new ArrayList<XMLElement>();
        
        renderContainer(elements, tabbedpaneitem.getContainer(), config);
        
        return elements;
    }
}