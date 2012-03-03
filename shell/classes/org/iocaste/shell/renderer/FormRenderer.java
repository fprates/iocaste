package org.iocaste.shell.renderer;

import org.iocaste.shell.XMLElement;
import org.iocaste.shell.common.Form;

public class FormRenderer extends Renderer {
    
    /**
     * 
     * @param container
     * @param config
     * @return
     */
    public static final XMLElement render(Form container, Config config) {
        XMLElement hiddentag, formtag = new XMLElement("form");
        String enctype = container.getEnctype();
        String currentaction = container.getAction();
        
        config.setCurrentAction(currentaction);
        config.addAction(currentaction);
        
        formtag.add("method", "post");
        formtag.add("action", "index.html");
        formtag.add("name", container.getHtmlName());

        addAttributes(formtag, container);
        
        if (enctype != null)
            formtag.add("enctype", enctype);
        
        hiddentag = new XMLElement("input");
        hiddentag.add("type", "hidden");
        hiddentag.add("name", "pagetrack");
        hiddentag.add("value", config.getPageTrack());
        
        formtag.addInner(hiddentag.toString());
        formtag.addChildren(renderElements(container.getElements(), config));
        
        return formtag;
    }

}
