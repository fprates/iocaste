package org.iocaste.kernel.runtime.shell.renderer;

import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.kernel.runtime.shell.renderer.internal.ActionEventHandler;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.kernel.runtime.shell.renderer.legacy.ParameterRenderer;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.LinkComponent;
import org.iocaste.shell.common.LinkEntry;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.Shell;

public class LinkRenderer extends AbstractElementRenderer<LinkComponent> {
    
    public LinkRenderer(HtmlRenderer renderer) {
        super(renderer, Const.LINK);
    }

    @Override
    protected final XMLElement execute(LinkComponent link, Config config)
            throws Exception {
        ActionEventHandler handler;
        DataElement element;
        LinkEntry entry;
        XMLElement atag, imgtag;
        String text, image, htmlname, action;
        StringBuilder onclick;
        Map<String, String> events;
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
        atag.add("id", htmlname);
        atag.add("class", link.getStyleClass());

        onclick = new StringBuilder();
        parameterrenderer = get(Const.PARAMETER);
        parameters = link.getParametersMap();
        if (parameters.size() > 0) {
            for (String name : parameters.keySet()) {
                entry = parameters.get(name);
                if (entry == null)
                    throw new IocasteException(
                            "%s is an unreferenced parameter of %s.",
                            name, htmlname);
                
                if (entry.value == null)
                    entry.value = "";
                
                onclick.append(Shell.setValue(name, entry.value));
                parameter = new Parameter(config.viewctx.view, name);
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
        
        action = link.getAction();
        if (!absolute) {
            if (action != null) {
                atag.add("role", "button");
                onclick.append(link.isScreenLockable()?
                    Shell.formSubmit(
                        config.currentform, config.currentaction, action) :
                    Shell.formSubmitNoLock(
                        config.currentform, config.currentaction, action));
                
                handler = config.viewctx.
                        getEventHandler(htmlname, action, "click");
                handler.name = htmlname;
                handler.call = onclick.toString();
            } else {
                action = htmlname;
            }
        } else {
            atag.add("href", action);
            action = htmlname;
        }
        
        events = link.getEvents();
        for (String event : events.keySet()) {
            handler = config.viewctx.getEventHandler(htmlname, action, event);
            handler.name = htmlname;
            handler.call = events.get(event);
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
