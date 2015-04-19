package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.protocol.utils.XMLElement;
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
        int columns;
        List<XMLElement> tags = new ArrayList<XMLElement>();
        XMLElement nulltag, itemtag, divtag, formtag;
        
        formtag = new XMLElement("table");

        divtag = new XMLElement("div");
        divtag.add("class", form.getStyleClass());
        divtag.addChild(formtag);
        
        columns = form.getColumns();
        if (columns == 0)
            for (Element element : form.getElements()) {
                if (!element.isDataStorable() || !element.isVisible())
                    continue;

                itemtag = new XMLElement("tr");
                dataitem = (DataItem)element;
                DataItemRenderer.render(dataitem, itemtag, config);
                formtag.addChild(itemtag);
            }
        else
            for (String[] line : form.getLines()) {
                itemtag = new XMLElement("tr");
                for (String column : line) {
                    dataitem = (column == null)? null : form.get(column);
                    if (dataitem == null || !dataitem.isVisible()) {
                        nulltag = new XMLElement("td");
                        nulltag.add("class", "form_cell");
                        nulltag.addInner("");
                        itemtag.addChild(nulltag);
                        itemtag.addChild(nulltag);
                        
                        continue;
                    }
                    
                    DataItemRenderer.render(dataitem, itemtag, config);
                }
                formtag.addChild(itemtag);
            }
        
        tags.add(divtag);
        
        return tags;
    }
}
