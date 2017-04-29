package org.iocaste.kernel.runtime.shell.renderer;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;

public class DataFormRenderer extends AbstractElementRenderer<DataForm> {
    
    public DataFormRenderer(HtmlRenderer renderer) {
        super(renderer, Const.DATA_FORM);
    }

    @Override
    protected final XMLElement execute(DataForm form, Config config) {
        Map<String, String[]> groups;
        XMLElement formtag;
        Set<Element> elements;
        String htmlname = form.getHtmlName();

        groups = form.getGroups();
        if (groups.size() == 0)
            return renderGroup(form, form.getElements(), htmlname, config);
        
        elements = new LinkedHashSet<>();
        formtag = new XMLElement("div");
        for (String name : groups.keySet()) {
            for (String element : groups.get(name))
                elements.add(form.get(element));
            formtag.addChild(renderGroup(form, elements, name, config));
            elements.clear();
        }
        return formtag;
    }
    
    private final XMLElement renderGroup(DataForm form,
            Set<Element> elements, String groupname, Config config) {
        DataItem dataitem;
        DataItemRenderer renderer = get(Const.DATA_ITEM);
        XMLElement formtag = new XMLElement("ul");
        
        formtag.add("class", form.getStyleClass());
        formtag.add("id", groupname);
        addAttributes(formtag, form);
        
        for (Element element : elements) {
            if (!element.isDataStorable() || !element.isVisible())
                continue;
            dataitem = (DataItem)element;
            renderer.execute(dataitem, formtag, config);
        }
        
        return formtag;
    }
}
