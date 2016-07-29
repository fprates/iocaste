package org.iocaste.internal.renderer;

import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.internal.EventHandler;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.LinkEntry;
import org.iocaste.shell.common.Parameter;

public class LinkRenderer extends AbstractElementRenderer<Link> {
    
    public LinkRenderer(Map<Const, Renderer<?>> renderers) {
        super(renderers, Const.LINK);
    }

    @Override
    protected final XMLElement execute(Link link, Config config) {
        EventHandler handler;
        DataElement element;
        LinkEntry entry;
        XMLElement atag, imgtag;
        String text, image, htmlname;
        StringBuilder onclick;
        Map<String, LinkEntry> parameters;
        Parameter parameter;
        ParameterRenderer parameterrenderer;
        boolean absolute = link.isAbsolute();
        
        if (!link.isEnabled()) {
            atag = new XMLElement("span");
            atag.addInner(link.getText());
            atag.add("class", link.getStyleClass());
            return atag;
        }
        
        htmlname = link.getHtmlName();
        atag = new XMLElement("a");
        if (absolute)
            atag.add("href", link.getAction());
        
        atag.add("id", htmlname);
        atag.add("class", link.getStyleClass());

        onclick = new StringBuilder();
        parameterrenderer = get(Const.PARAMETER);
        parameters = link.getParametersMap();
        if (parameters.size() > 0) {
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
                atag.addChild(parameterrenderer.run(parameter, config));
            }
        }
        
        if (!absolute) {
            atag.add("role", "button");
            if (link.isScreenLockable())
                onclick.append("formSubmit('");
            else
                onclick.append("formSubmitNoLock('");
            
            onclick.append(config.getCurrentForm()).
                   append("', '").append(config.getCurrentAction()).
                   append("', '").append(link.getAction()).
                   append("');");
        }
        
        if (onclick.length() > 0) {
            handler = config.actionInstance(link.getAction());
            handler.name = link.getHtmlName();
            handler.event = "click";
            handler.call = onclick.toString();
        }
        
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
        
        return atag;
    }

}
