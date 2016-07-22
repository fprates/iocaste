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
        String htmlname;
        int columns;
        XMLElement formtag, labeltag, valuetag, labellist, valuelist;
        DataItemRenderer renderer;

        labeltag = null;
        labellist = null;
        htmlname = form.getHtmlName();
        formtag = new XMLElement("ul");
        formtag.add("class", form.getStyleClass());
        formtag.add("id", htmlname);
        addEvents(formtag, form);
        
        renderer = get(Const.DATA_ITEM);
        columns = form.getColumns();
        if (columns == 0) {
            valuetag = new XMLElement("li");
            valuetag.add("id", htmlname.concat("_cname"));
            valuelist = new XMLElement("ul");
            valuelist.add("id", htmlname.concat("_cvalue"));
            valuelist.add(
                    "style", "margin:0px;padding:0px;list-style-type:none");
            valuetag.addChild(valuelist);
            for (Element element : form.getElements()) {
                if (!element.isDataStorable() || !element.isVisible())
                    continue;

                dataitem = (DataItem)element;
                if ((labeltag == null) && !dataitem.hasPlaceHolder()) {
                    labeltag = new XMLElement("li");
                    labeltag.add("style", "display:inline;float:left");
                    labellist = new XMLElement("ul");
                    labellist.add("style",
                            "margin:0px;padding:0px;list-style-type:none");
                    labeltag.addChild(labellist);
                }
                
                renderer.execute(dataitem, labellist, valuelist, config);
            }
            if (labeltag != null) {
                formtag.addChild(labeltag);
                valuetag.add("style", "display:inline;float:left");
            } else {
                valuetag.add("style", "display:inline;float:left;width:100%");
            }
            formtag.addChild(valuetag);
//        } else {
//            for (String[] line : form.getLines()) {
//                itemtag = new XMLElement("tr");
//                for (String column : line) {
//                    dataitem = (column == null)? null : form.get(column);
//                    if (dataitem == null || !dataitem.isVisible()) {
//                        nulltag = new XMLElement("td");
//                        nulltag.add("class", "form_cell");
//                        nulltag.addInner("");
//                        itemtag.addChild(nulltag);
//                        itemtag.addChild(nulltag);
//                        continue;
//                    }
//                    
//                    renderer.execute(dataitem, itemtag, config);
//                }
//                formtag.addChild(itemtag);
//            }
        }
        
        return formtag;
    }
}
