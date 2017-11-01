package org.iocaste.kernel.runtime.shell.renderer;

import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Form;

public class FormRenderer extends AbstractElementRenderer<Form> {
    
    public FormRenderer(HtmlRenderer renderer) {
        super(renderer, Const.FORM);
    }

    /**
     * 
     * @param container
     * @param config
     * @return
     */
    protected final XMLElement execute(Form container, Config config)
            throws Exception {
        String[] printlines;
        String action;
        XMLElement content;
        XMLElement formtag = new XMLElement("form");
        String enctype = container.getEnctype();
        String htmlname = config.currentform = container.getHtmlName();
        
        config.currentaction = container.getAction();
        config.form = container;
        
        action = container.getAction();
        if (action == null)
            action = config.viewctx.viewexport.path;
        formtag.add("method", "post");
        formtag.add("action", action);
        formtag.add("id", htmlname);
        formtag.add("name", htmlname);
        
        addAttributes(formtag, container);
        if (enctype != null)
            formtag.add("enctype", enctype);

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
