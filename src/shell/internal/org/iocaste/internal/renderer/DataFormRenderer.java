package org.iocaste.internal.renderer;

import java.util.Map;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;

public class DataFormRenderer extends AbstractElementRenderer<DataForm> {
    
    public DataFormRenderer(Map<Const, Renderer<?>> renderers) {
        super(renderers, Const.DATA_FORM);
    }

    @Override
    protected final XMLElement execute(DataForm form, Config config) {
        DataItem dataitem;
        int columns;
        XMLElement nulltag, formtag, itemtag;
        DataItemRenderer renderer;
        
        formtag = new XMLElement("table");
        formtag.add("class", form.getStyleClass());
        formtag.add("id", form.getHtmlName());
        addEvents(formtag, form);
        
        renderer = get(Const.DATA_ITEM);
        columns = form.getColumns();
        if (columns == 0)
            for (Element element : form.getElements()) {
                if (!element.isDataStorable() || !element.isVisible())
                    continue;

                dataitem = (DataItem)element;
                itemtag = new XMLElement("tr");
                renderer.execute(dataitem, itemtag, config);
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
                    
                    renderer.execute(dataitem, itemtag, config);
                }
                formtag.addChild(itemtag);
            }
        
        return formtag;
    }
}
