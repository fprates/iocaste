package org.iocaste.internal.renderer;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Parameter;

public class FormRenderer extends Renderer {
    
    /**
     * 
     * @param container
     * @param config
     * @return
     */
    public static final XMLElement render(Form container, Config config) {
        Parameter parameter;
        XMLElement hiddentag, content, pagecontrol;
        XMLElement formtag = new XMLElement("form");
        String enctype = container.getEnctype();
        String currentaction = container.getAction();
        String htmlname = container.getHtmlName();
        
        config.setCurrentAction(currentaction);
        config.addAction(currentaction);
        
        formtag.add("method", "post");
        formtag.add("action", "index.html");
        formtag.add("id", htmlname);
        formtag.add("name", htmlname);

        content = new XMLElement("div");
        content.add("class", container.getStyleClass());
        content.addChildren(renderElements(container.getElements(), config));
        
        addEvents(formtag, container);
        
        if (enctype != null)
            formtag.add("enctype", enctype);
        
        parameter = new Parameter(null, "pagetrack");
        parameter.set(config.getPageTrack());
        hiddentag = ParameterRenderer.render(parameter);
        
        formtag.addInner(hiddentag.toString());
        pagecontrol = config.getPageControl();
        if (pagecontrol != null)
            formtag.addChild(pagecontrol);
        
        formtag.addChild(content);
        
        return formtag;
    }

}
