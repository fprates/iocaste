package org.iocaste.shell.renderer;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.shell.XMLElement;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.EventAware;
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
        Button button;
        StringBuilder sb;
        TabbedPaneHandler tpanehandler = new TabbedPaneHandler(tabbedpane);
        XMLElement tabitem, tabbedtag = new XMLElement("div");
        String[] names = tabbedpane.getItensNames();
        
        tabbedtag.add("id", tabbedpane.getName());
        
        for (String name : names) {
            sb = new StringBuilder("setElementDisplay('").append(name);
            
            if (tabbedpane.getCurrent().equals(name))
                sb.append(".tabitem', 'block');");
            else
                sb.append(".tabitem', 'none');");
            
            config.addOnload(sb.toString());
            
            button = new Button(tabbedpane, name);
            button.setSubmit(false);
            button.setEventHandler(tpanehandler);
            
            sb = new StringBuilder();
            
            for (String name_ : names) {
                sb.append("setElementDisplay('").append(name_);
                
                sb.append((name.equals(name_))?
                        ".tabitem', 'block');" : ".tabitem', 'none');");
            }
            
            sb.append("send('").append(name).append("', null)");
            
            button.addAttribute("onClick", sb.toString());
            
            tabbedtag.addChild(ButtonRenderer.render(button, config));
        }
        
        for (String name : names) {
            tabitem = StandardContainerRenderer.render(new StandardContainer(
                    null, name + ".tabitem"), config);
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

class TabbedPaneHandler implements EventAware {
    private static final long serialVersionUID = -4701284786729026501L;
    private TabbedPane tpane;
    
    public TabbedPaneHandler(TabbedPane tpane) {
        this.tpane = tpane;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.EventAware#onEvent(byte, java.lang.String)
     */
    @Override
    public void onEvent(byte event, String action) {
        tpane.setCurrent(action);
    }
    
}