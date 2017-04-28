package org.iocaste.kernel.runtime.shell.renderer;

import java.util.Map;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Parameter;

public class FormRenderer extends AbstractElementRenderer<Form> {
    
    public FormRenderer(Map<Const, Renderer<?>> renderers) {
        super(renderers, Const.FORM);
    }

    /**
     * 
     * @param container
     * @param config
     * @return
     */
    protected final XMLElement execute(Form container, Config config) {
        String[] printlines;
        Parameter parameter;
        XMLElement hiddentag, content;
        ParameterRenderer parameterrenderer;
        XMLElement formtag = new XMLElement("form");
        String enctype = container.getEnctype();
        String htmlname = config.currentform = container.getHtmlName();
        
        config.currentaction = container.getAction();
        config.form = container;
        
        formtag.add("method", "post");
        formtag.add("action", "index.html");
        formtag.add("id", htmlname);
        formtag.add("name", htmlname);
        
        addAttributes(formtag, container);
        if (enctype != null)
            formtag.add("enctype", enctype);

        parameterrenderer = get(Const.PARAMETER);
        parameter = new Parameter(config.viewctx.view, "pagetrack");
        parameter.set(config.getPageTrack());
        hiddentag = parameterrenderer.run(parameter, config);
        formtag.addChild(hiddentag);

        content = new XMLElement("div");
        content.add("class", container.getStyleClass());
        content.addChildren(renderElements(container.getElements(), config));
        printlines = config.viewctx.view.getPrintLines();
        if (printlines.length > 0) {
            content.addChild(renderPrintLines(printlines));
            config.viewctx.view.clearPrintLines();
        }
        
        formtag.addChild(content);
        config.addOnload(setGlobalContext(config));
        return formtag;
    }
    
    /**
     * 
     * @param line
     * @return
     */
    private final XMLElement renderPrintLines(String[] lines) {
        XMLElement pretag = new XMLElement("pre");
        
        pretag.add("class", "output_list");
        for (int i = 0; i < lines.length; i++) {
            if (lines[i] == null)
                continue;
            lines[i] = lines[i].replaceAll("[<]", "&lt");
            lines[i] = lines[i].replaceAll("[>]", "&gt");
        }
        pretag.addInner(lines);
        
        return pretag;
    }
    
    private final String setGlobalContext(Config config) {
        StringBuilder sb;
        
        sb = new StringBuilder("setGlobalContext(\"").
                append(config.currentform).append("\", \"").
                append(config.currentaction).append("\")");
        return sb.toString();
    }

}
