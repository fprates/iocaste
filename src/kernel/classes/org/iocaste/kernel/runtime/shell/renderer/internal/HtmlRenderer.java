package org.iocaste.kernel.runtime.shell.renderer.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.kernel.runtime.shell.renderer.ButtonRenderer;
import org.iocaste.kernel.runtime.shell.renderer.CanvasRenderer;
import org.iocaste.kernel.runtime.shell.renderer.CheckBoxRenderer;
import org.iocaste.kernel.runtime.shell.renderer.Config;
import org.iocaste.kernel.runtime.shell.renderer.DataFormRenderer;
import org.iocaste.kernel.runtime.shell.renderer.DataItemRenderer;
import org.iocaste.kernel.runtime.shell.renderer.DummyRenderer;
import org.iocaste.kernel.runtime.shell.renderer.ExpandBarRenderer;
import org.iocaste.kernel.runtime.shell.renderer.FileEntryRenderer;
import org.iocaste.kernel.runtime.shell.renderer.FormRenderer;
import org.iocaste.kernel.runtime.shell.renderer.FrameRenderer;
import org.iocaste.kernel.runtime.shell.renderer.HtmlTagRenderer;
import org.iocaste.kernel.runtime.shell.renderer.LinkRenderer;
import org.iocaste.kernel.runtime.shell.renderer.ListBoxRenderer;
import org.iocaste.kernel.runtime.shell.renderer.MessageRenderer;
import org.iocaste.kernel.runtime.shell.renderer.NodeListItemRenderer;
import org.iocaste.kernel.runtime.shell.renderer.NodeListRenderer;
import org.iocaste.kernel.runtime.shell.renderer.ParameterRenderer;
import org.iocaste.kernel.runtime.shell.renderer.PrintAreaRenderer;
import org.iocaste.kernel.runtime.shell.renderer.RadioButtonRenderer;
import org.iocaste.kernel.runtime.shell.renderer.RangeFieldRenderer;
import org.iocaste.kernel.runtime.shell.renderer.Renderer;
import org.iocaste.kernel.runtime.shell.renderer.StandardContainerRenderer;
import org.iocaste.kernel.runtime.shell.renderer.TabbedPaneRenderer;
import org.iocaste.kernel.runtime.shell.renderer.TableItemRenderer;
import org.iocaste.kernel.runtime.shell.renderer.TableRenderer;
import org.iocaste.kernel.runtime.shell.renderer.TextAreaRenderer;
import org.iocaste.kernel.runtime.shell.renderer.TextFieldRenderer;
import org.iocaste.kernel.runtime.shell.renderer.TextRenderer;
import org.iocaste.kernel.runtime.shell.renderer.VirtualControlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.HeaderLink;

public class HtmlRenderer {
    private String username;
    private List<String> script;
    private ViewContext viewctx;
    private Map<Const, Renderer<? extends Element>> renderers;
    
    public HtmlRenderer() {
        String line;
        BufferedReader reader;
        InputStream is;
        
        renderers = new HashMap<>();
        new ButtonRenderer(this);
        new CanvasRenderer(this);
        new CheckBoxRenderer(this);
        new DataFormRenderer(this);
        new DataItemRenderer(this);
        new DummyRenderer(this);
        new DummyRenderer(this, Const.RADIO_GROUP);
        new ExpandBarRenderer(this);
        new FileEntryRenderer(this);
        new FormRenderer(this);
        new FrameRenderer(this);
        new HtmlTagRenderer(this);
        new LinkRenderer(this);
        new ListBoxRenderer(this);
        new MessageRenderer(this);
        new NodeListItemRenderer(this);
        new NodeListRenderer(this);
        new ParameterRenderer(this);
        new PrintAreaRenderer(this);
        new RadioButtonRenderer(this);
        new RangeFieldRenderer(this);
        new StandardContainerRenderer(this);
        new StandardContainerRenderer(this, Const.TABBED_PANE_ITEM);
        new TabbedPaneRenderer(this);
        new TableItemRenderer(this);
        new TableRenderer(this);
        new TextAreaRenderer(this);
        new TextFieldRenderer(this);
        new TextRenderer(this);
        new VirtualControlRenderer(this);
        
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

    public final void add(Renderer<? extends Element> renderer) {
        renderers.put(renderer.getType(), renderer);
    }
    
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
    private final XMLElement renderHeader(Config config) {
        Object[][] stylesheet;
        XMLElement linktag;
        List<HeaderLink> links;
        Element focus = viewctx.view.getFocus();
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
        
        titletag.addInner(viewctx.title.text);

        if (focus != null) {
            focusname = focus.getHtmlName();
            config.addOnload(new StringBuffer("document.getElementById('").
                    append(focusname).append("').focus();").toString());
        }
        headtag.addChild(titletag);
        
        links = viewctx.view.getLinks();
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
        stylesheet = viewctx.view.getStyleSheet();
        if (stylesheet != null)
            headtag.addChild(renderStyleSheet(stylesheet));
        
        return headtag;
    }
    
    /**
     * 
     * @param script
     * @param config
     * @return
     */
    private final XMLElement renderJavaScript(List<String> script,
            Config config) {
        StringBuilder sb;
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
        for (String name : config.viewctx.actions.keySet()) {
            handlers = config.viewctx.actions.get(name);
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
    
    /**
     * Retorna visão renderizada.
     * @param view dados da visão
     * @return código html da visão
     */
    public final List<String> run(ViewContext viewctx) {
        Config config;
        MessageRenderer messagerenderer;
        List<String> html = new ArrayList<>();
        List<XMLElement> tags = new ArrayList<>();
        List<XMLElement> bodycontent = new ArrayList<>();
        XMLElement htmltag = new XMLElement("html");
        XMLElement bodytag = new XMLElement("body");
        
        config = new Config();
        config.setViewContext(this.viewctx = viewctx);
        config.setUsername(username);
        config.setPageTrack(viewctx.tracking.id);
//        config.setPopupControl(pagectx.getPopupControl());
        config.setTracking(viewctx.tracking);
        
        html.add("<!DOCTYPE html>");
        bodytag.add("onLoad", "initialize()");
        for (Container container : viewctx.view.getContainers())
            renderers.get(container.getType()).run(
                    bodycontent, container, config);
        
        messagerenderer = (MessageRenderer)renderers.get(Const.MESSAGE);
        bodytag.addChildren(bodycontent);
        bodytag.addChild(messagerenderer.run(null, config));
        bodytag.addChild(ScreenLock.render());
        tags.add(renderHeader(config));
        tags.add(bodytag);
        htmltag.addChildren(tags);
        html.add(htmltag.toString());
        
        return html;
    }
    
    public final void set(Renderer<?> renderer) {
        
    }
    
    /**
     * Ajusta nome do usuário
     * @param username nome
     */
    public final void setUsername(String username) {
        this.username = username;
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