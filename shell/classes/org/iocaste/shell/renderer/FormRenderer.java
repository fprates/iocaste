package org.iocaste.shell.renderer;

import org.iocaste.shell.XMLElement;
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
        
        parameter = new Parameter(null, "pagetrack");
        parameter.setValue(config.getPageTrack());
        hiddentag = ParameterRenderer.render(parameter);
        
        formtag.addInner(hiddentag.toString());
        formtag.addChildren(renderElements(container.getElements(), config));
        
        return formtag;
    }

}
