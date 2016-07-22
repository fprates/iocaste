package org.iocaste.internal.renderer;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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
        Map<String, String[]> groups;
        XMLElement formtag;
        Set<Element> elements;
        String htmlname = form.getHtmlName();

        formtag = new XMLElement("ul");
        formtag.add("class", form.getStyleClass());
        formtag.add("id", htmlname);
        addEvents(formtag, form);

        groups = form.getGroups();
        if (groups.size() == 0) {
            renderGroup(formtag, form.getElements(), htmlname, config);
            return formtag;
        }
        
        elements = new LinkedHashSet<>();
        for (String name : groups.keySet()) {
            for (String element : groups.get(name))
                elements.add(form.get(element));
            renderGroup(formtag, elements, name, config);
            elements.clear();
        }
        return formtag;
    }
    
    private final void renderGroup(XMLElement formtag,
            Set<Element> elements, String groupname, Config config) {
        DataItem dataitem;
        XMLElement labeltag, valuetag, labellist, valuelist;
        DataItemRenderer renderer = get(Const.DATA_ITEM);
        
        labeltag = null;
        labellist = null;        
        valuetag = new XMLElement("li");
        valuetag.add("id", groupname.concat("_cvalue"));
        valuelist = new XMLElement("ul");
        valuelist.add("style", "margin:0px;padding:0px;list-style-type:none");
        valuetag.addChild(valuelist);
        for (Element element : elements) {
            if (!element.isDataStorable() || !element.isVisible())
                continue;

            dataitem = (DataItem)element;
            if ((labeltag == null) && !dataitem.hasPlaceHolder()) {
                labeltag = new XMLElement("li");
                labeltag.add("id", groupname.concat("_cname"));
                labeltag.add("style", "display:inline;float:left");
                labellist = new XMLElement("ul");
                labellist.add(
                        "style", "margin:0px;padding:0px;list-style-type:none");
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
    }
}
