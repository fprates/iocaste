package org.iocaste.kernel.runtime.shell.renderer.legacy;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.kernel.runtime.shell.renderer.AbstractElementRenderer;
import org.iocaste.kernel.runtime.shell.renderer.DataItemRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;

public class DataFormRenderer extends AbstractElementRenderer<DataForm> {
    
    public DataFormRenderer(HtmlRenderer renderers) {
        super(renderers, Const.DATA_FORM);
    }

    @Override
    protected final XMLElement execute(DataForm form, Config config)
            throws Exception {
        Map<String, String[]> groups;
        XMLElement formtag;
        Set<Element> elements;
        String htmlname = form.getHtmlName();

        groups = form.getGroups();
        if (groups.size() == 0) {
            formtag = renderGroup(form, form.getElements(), htmlname, config);
            renderHiddenInputs(formtag, config);
            return formtag;
        }
        
        elements = new LinkedHashSet<>();
        formtag = new XMLElement("div");
        for (String name : groups.keySet()) {
            for (String element : groups.get(name))
                elements.add(form.get(element));
            formtag.addChild(renderGroup(form, elements, name, config));
            elements.clear();
        }
        
        renderHiddenInputs(formtag, config);
        return formtag;
    }
    
    private final XMLElement renderGroup(DataForm form, Set<Element> elements,
            String groupname, Config config) throws Exception {
        DataItem dataitem;
        DataItemRenderer renderer = get(Const.DATA_ITEM);
        XMLElement formtag = new XMLElement("ul");
        
        formtag.add("class", form.getStyleClass());
        formtag.add("id", groupname);
        addAttributes(formtag, form);
        
        for (Element element : elements) {
            if (!element.isDataStorable())
                continue;
            dataitem = (DataItem)element;
            if (!element.isVisible()) {
                hide(dataitem);
                continue;
            }
            renderer.execute(dataitem, formtag, config);
        }
        
        return formtag;
    }
}
