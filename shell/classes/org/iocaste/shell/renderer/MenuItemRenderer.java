package org.iocaste.shell.renderer;

import org.iocaste.shell.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.Menu;
import org.iocaste.shell.common.MenuItem;
import org.iocaste.shell.common.Parameter;

public class MenuItemRenderer extends Renderer {
    
    /**
     * 
     * @param menuitem
     * @return
     */
    public static final XMLElement render(MenuItem menuitem, Config config) {
        Menu menu = (Menu)menuitem.getContainer();
        String name = new StringBuffer(menuitem.getName()).append(".link").
                toString();
        Link link = new Link(null, name, menu.getAction());
        
        link.setText(menuitem.getText());
        link.add(menu.getParameter(), menuitem.getFunction());
        
        for (Element element: menu.getElements()) {
            if ((element.getType() != Const.PARAMETER) ||
                    (element == menu.getParameter()))
                continue;
            
            link.add((Parameter)element, menuitem.getParameter(
                    element.getName()));
        }
                
        return LinkRenderer.render(link, config);
    }

}
