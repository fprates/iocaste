package org.iocaste.shell.renderer;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.shell.XMLElement;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;

public class DataFormRenderer extends Renderer {
    
    /**
     * 
     * @param form
     * @param config
     * @return
     */
    public static final List<XMLElement> render(DataForm form, Config config) {
        DataItem dataitem;
        List<XMLElement> tags = new ArrayList<XMLElement>();
        XMLElement formtag = new XMLElement("table");
        
        for (Element element : form.getElements()) {
            if (!element.isDataStorable() || !element.isVisible())
                continue;

            dataitem = (DataItem)element;
            formtag.addChild(DataItemRenderer.render(dataitem, config));
        }
        
        tags.add(formtag);
        
        return tags;
    }
}
