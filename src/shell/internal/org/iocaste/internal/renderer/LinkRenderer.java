package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.LinkEntry;
import org.iocaste.shell.common.Parameter;

public class LinkRenderer extends Renderer {
    
    /**
     * 
     * @param link
     * @return
     */
    public static final List<XMLElement> render(Link link, Config config) {
        DataElement element;
        LinkEntry entry;
        XMLElement atag, imgtag;
        String text, href, image, htmlname;
        StringBuilder onclick, hrefsb;
        Map<String, LinkEntry> parameters;
        Parameter parameter;
        List<XMLElement> tags = new ArrayList<>();
        
        if (!link.isEnabled()) {
            atag = new XMLElement("span");
            atag.addInner(link.getText());
            atag.add("class", link.getStyleClass());
            tags.add(atag);
            return tags;
        }
        
        config.addAction(link.getAction(), link.getHtmlName());
        htmlname = link.getHtmlName();
        atag = new XMLElement("a");
        atag.add("id", htmlname);
        atag.add("class", link.getStyleClass());
        tags.add(atag);

        if (link.isAbsolute()) {
            href = link.getAction();
        } else {
            if (link.isScreenLockable())
                hrefsb = new StringBuilder("javascript:formSubmit('");
            else
                hrefsb = new StringBuilder("javascript:formSubmitNoLock('");
            
            hrefsb.append(config.getCurrentForm()).
                   append("', '").append(config.getCurrentAction()).
                   append("', '").append(link.getAction()).
                   append("');");
            
            href = hrefsb.toString();
        }
        
        parameters = link.getParametersMap();
        if (parameters.size() > 0) {
            onclick = new StringBuilder();
            for (String name : parameters.keySet()) {
                entry = parameters.get(name);
                if (entry == null)
                    throw new RuntimeException(new StringBuilder(name).
                            append(" is an unreferenced parameter of ").
                            append(htmlname).toString());
                
                if (entry.value == null)
                    entry.value = "";
                
                onclick.append("setValue('").append(name).
                        append("', '").append(entry.value).
                        append("');");
                
                parameter = new Parameter(config.getView(), name);
                element = null;
                switch (entry.type) {
                case DataType.CHAR:
                    
                    element = new DataElement(name);
                    element.setType(entry.type);
                    element.setUpcase(DataType.KEEPCASE);
                    element.setLength(entry.value.toString().length());
                    break;
                case DataType.INT:
                case DataType.BYTE:
                case DataType.LONG:
                case DataType.SHORT:
                    element = new DataElement(name);
                    element.setType(entry.type);
                    element.setLength(entry.value.toString().length());
                    break;
                }
                
                parameter.setDataElement(element);
                tags.add(ParameterRenderer.render(parameter));
            }
            
            atag.add("onClick", onclick.toString());
        }
        atag.add("href", href);
        
        image = link.getImage();
        if (image != null) {
            imgtag = new XMLElement("img");
            imgtag.add("src", image);
            atag.addChild(imgtag);
            
            text = link.getText();
            if (text != null)
                imgtag.add("alt", text);
        } else {
            text = link.getText();
            if (text != null)
                atag.addInner(text);
            else
                atag.addInner("");
        }
        
        if (link.size() > 0)
            atag.addChildren(renderElements(link.getElements(), config));
        
        return tags;
    }

}
