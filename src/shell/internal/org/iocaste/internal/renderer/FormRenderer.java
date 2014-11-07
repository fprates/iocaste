package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.internal.TrackingData;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.View;

public class FormRenderer extends Renderer {
    
    /**
     * 
     * @param container
     * @param config
     * @return
     */
    public static final XMLElement render(Form container, Config config) {
        String[] printlines;
        Parameter parameter;
        View view;
        XMLElement hiddentag, content, pagecontrol;
        XMLElement formtag = new XMLElement("form");
        String enctype = container.getEnctype();
        String currentaction = container.getAction();
        String htmlname = container.getHtmlName();
        
        config.setCurrentAction(currentaction);
        config.addAction(currentaction);
        config.form = container;

        for (Element element : container.getElements()) {
            if (element.getType() != Const.PAGE_CONTROL)
                continue;
            pagecontrol = PageControlRenderer.
                    render((PageControl)element, config);
            formtag.addChild(pagecontrol);
            break;
        }
        
        formtag.add("method", "post");
        formtag.add("action", "index.html");
        formtag.add("id", htmlname);
        formtag.add("name", htmlname);
        formtag.add("style", "height: 100%");
        
        addEvents(formtag, container);
        if (enctype != null)
            formtag.add("enctype", enctype);
        
        parameter = new Parameter(config.getView(), "pagetrack");
        parameter.set(config.getPageTrack());
        hiddentag = ParameterRenderer.render(parameter);
        formtag.addInner(hiddentag.toString());

        content = new XMLElement("div");
        content.add("class", container.getStyleClass());
        if (config.getShControl() != null)
            content.addChildren(renderSh(config));
        content.addChildren(renderElements(container.getElements(), config));

        view = config.getView();
        printlines = view.getPrintLines();
        if (printlines.length > 0) {
            content.addChild(renderPrintLines(printlines));
            view.clearPrintLines();
        }
        
        formtag.addChild(content);
        return formtag;
    }
    
    /**
     * 
     * @param line
     * @return
     */
    private static final XMLElement renderPrintLines(String[] lines) {
        XMLElement pretag = new XMLElement("pre");
        
        pretag.add("class", "output_list");
        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].replaceAll("[<]", "&lt");
            lines[i] = lines[i].replaceAll("[>]", "&gt");
        }
        pretag.addInner(lines);
        
        return pretag;
    }
    
    private static final List<XMLElement> renderSh(Config config) {
        Map<String, Object> parameters;
        Object[] viewreturn;
        List<XMLElement> tags = new ArrayList<>();
        TrackingData tracking = config.getTracking();
        Service service = new Service(tracking.sessionid, tracking.contexturl);
        Message message = new Message("get_view_data");
        View view = new View("iocaste-search-help", "main");
        StyleSheet stylesheet = config.getView().styleSheetInstance();
        
        parameters = new HashMap<>();
        parameters.put("sh", config.getShControl());
        view.setStyleSheet(stylesheet.getElements());
        
        message.add("view", view);
        message.add("init", true);
        message.add("parameters", parameters);
        viewreturn = (Object[])service.call(message);
        view = (View)viewreturn[0];
        
        config.getView().setStyleSheet(view.styleSheetInstance().getElements());
        for (Container container : view.getContainers())
            Renderer.renderContainer(tags, container, config);
        
        return tags;
    }

}
