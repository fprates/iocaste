package org.iocaste.kernel.runtime.shell.renderer.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.HeaderLink;

public abstract class AbstractHtmlRenderer implements HtmlRenderer {
    private List<String> script;
    private Map<Const, Renderer<? extends Element>> renderers;
    
    public AbstractHtmlRenderer() {
        String line;
        BufferedReader reader;
        InputStream is;
        
        renderers = new HashMap<>();
        
        is = getClass().getResourceAsStream("/META-INF/shell.js");
        if (is == null)
            return;
        
        reader = new BufferedReader(new InputStreamReader(is));
        script = new ArrayList<>();
        
        try {
            while ((line = reader.readLine()) != null)
                script.add(line);
            
            reader.close();
            is.close();
        } catch (IOException e) {
            new RuntimeException(e);
        }
    }

    @Override
    public final void add(Renderer<? extends Element> renderer) {
        renderers.put(renderer.getType(), renderer);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public final <T extends Renderer<? extends Element>> T getRenderer(
            Const type) {
        return (T)renderers.get(type);
    }
    
    /**
     * 
     * @param vdata
     * @return
     */
    protected final XMLElement renderHeader(Config config) {
        Object[][] stylesheet;
        XMLElement linktag;
        List<HeaderLink> links;
        Element focus = config.viewctx.view.getFocus();
        String focusname;
        XMLElement headtag = new XMLElement("head");
        XMLElement metatag = new XMLElement("meta");
        XMLElement titletag = new XMLElement("title");
        metatag.add("http-equiv", "Content-Type");
        metatag.add("content", "text/html; charset=utf-8");
        headtag.addChild(metatag);
        
        metatag = new XMLElement("meta");
        metatag.add("name", "viewport");
        metatag.add("content",
                "width=device-width, initial-scale=1.0, user-scalable=no");
        headtag.addChild(metatag);
        
        titletag.addInner(config.viewctx.viewexport.title.text);

        if (focus != null) {
            focusname = focus.getHtmlName();
            config.addOnload(new StringBuffer("document.getElementById('").
                    append(focusname).append("').focus();").toString());
        }
        headtag.addChild(titletag);
        
        links = config.viewctx.view.getLinks();
        if (links != null)
            for (HeaderLink link : links) {
                linktag = new XMLElement("link");
                linktag.add("rel", link.rel);
                if (link.type != null)
                    linktag.add("type", link.type);
                linktag.add("href", link.href);
                headtag.addChild(linktag);
            }
        
        if (script != null)
            headtag.addChild(renderJavaScript(script, config));
        stylesheet = config.viewctx.view.getStyleSheet();
        if (stylesheet != null)
            headtag.addChild(renderStyleSheet(stylesheet));
        
        return headtag;
    }
    
    private final XMLElement renderJavaScript(List<String> script,
            Config config) {
        StringBuilder sb;
        Map<String, Map<String, ActionEventHandler>> elements;
        Map<String, ActionEventHandler> handlers;
        HandlerScript handlerscript;
        Map<String, HandlerScript> handlerscripts;
        ActionEventHandler handler;
        XMLElement scripttag = new XMLElement("script");
        
        scripttag.setLineBreak(true);
        scripttag.add("type", "text/javascript");
        scripttag.addInner("function initialize() {");
        for (String line : config.getOnload())
            scripttag.addInner(line);
        scripttag.addInner("}");
        scripttag.addInner(script);

        handlerscripts = new HashMap<>();
        for (String elementkey : config.viewctx.actions.keySet()) {
            elements = config.viewctx.actions.get(elementkey);
            for (String actionkey : elements.keySet()) {
                handlers = elements.get(actionkey);
                for (String key : handlers.keySet()) {
                    handler = handlers.get(key);
                    if (handler.event == null)
                        continue;
                    
                    handlerscript = handlerscripts.get(handler.event);
                    if (handlerscript == null) {
                        handlerscript = new HandlerScript();
                        handlerscripts.put(handler.event, handlerscript);
                    }
                    
                    sb = new StringBuilder(handler.name).append(handler.event);
                    handlerscript.function = sb.toString().replace('.', '_');
    
                    sb = new StringBuilder("function ").
                            append(handlerscript.function).append("(e) {");
                    handlerscript.script.add(sb.toString());
                    handlerscript.script.add(handler.call);
                    handlerscript.script.add("}");
                    
                    sb = new StringBuilder("document.getElementById('").
                            append(handler.name).
                            append("').addEventListener('").
                            append(handler.event).
                            append("', ").
                            append(handlerscript.function).append(");");
                    handlerscript.handler.add(sb.toString());
                }
            }
        }

        for (String event : handlerscripts.keySet())
            scripttag.addInner(handlerscripts.get(event).script);
        
        scripttag.addInner(
                "document.addEventListener('DOMContentLoaded', function () {");
        for (String event : handlerscripts.keySet())
            scripttag.addInner(handlerscripts.get(event).handler);
        scripttag.addInner("});");
        
        return scripttag;
    }
    
    /**
     * 
     * @param csselements
     * @return
     */
    @SuppressWarnings("unchecked")
    private final XMLElement renderStyleSheet(Object[][] stylesheet) {
        Map<String, String> properties;
        boolean defaultmedia;
        XMLElement styletag;
        Map<String, Map<String, String>> csselements;

        styletag = new XMLElement("style");
        styletag.add("type", "text/css");
        styletag.setLineBreak(true);
            
        for (int i = 0; i < stylesheet.length; i++) {
            defaultmedia = stylesheet[i][0].equals("default");
            csselements = (Map<String, Map<String, String>>)stylesheet[i][2];
            if (!defaultmedia) {
                styletag.addInner(new StringBuilder("@media ").
                        append(stylesheet[i][1]).append(" {").toString());
            } else {
                properties = csselements.get(".screen_locked");
                if (properties == null) {
                    properties = new HashMap<>();
                    properties.put("position", "fixed");
                    properties.put("top", "0px");
                    properties.put("left", "0px");
                    properties.put("width", "100%");
                    properties.put("height", "100%");
                    properties.put("z-index", "99999");
                    properties.put("opacity", "0");
                    properties.put("background-color", "#000000");
                    csselements.put(".screen_locked", properties);
                }
            }
            
            if ((csselements != null) && (csselements.size() > 0))
                for (String element : csselements.keySet()) {
                    properties = csselements.get(element);
                    styletag.addInner(element+" {");
                    
                    for (String property: properties.keySet())
                        styletag.addInner(new StringBuilder(property).
                                append(": ").append(properties.get(property)).
                                append(";").toString());
                    
                    styletag.addInner("}");
                }
            
            
            if (!defaultmedia)
                styletag.addInner("}");
        }
        
        
        return styletag;
    }
}

class HandlerScript {
    public List<String> script;
    public List<String> handler;
    public String function;
    
    public HandlerScript() {
        script = new ArrayList<>();
        handler = new ArrayList<>();
    }
}
