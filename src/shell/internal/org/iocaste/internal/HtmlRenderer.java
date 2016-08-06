package org.iocaste.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.internal.renderer.Config;
import org.iocaste.internal.renderer.DataFormRenderer;
import org.iocaste.internal.renderer.DataItemRenderer;
import org.iocaste.internal.renderer.DummyRenderer;
import org.iocaste.internal.renderer.ExpandBarRenderer;
import org.iocaste.internal.renderer.FileEntryRenderer;
import org.iocaste.internal.renderer.FormRenderer;
import org.iocaste.internal.renderer.FrameRenderer;
import org.iocaste.internal.renderer.HtmlTagRenderer;
import org.iocaste.internal.renderer.LinkRenderer;
import org.iocaste.internal.renderer.ListBoxRenderer;
import org.iocaste.internal.renderer.MessageRenderer;
import org.iocaste.internal.renderer.NodeListItemRenderer;
import org.iocaste.internal.renderer.NodeListRenderer;
import org.iocaste.internal.renderer.ParameterRenderer;
import org.iocaste.internal.renderer.PrintAreaRenderer;
import org.iocaste.internal.renderer.RadioButtonRenderer;
import org.iocaste.internal.renderer.RangeFieldRenderer;
import org.iocaste.internal.renderer.Renderer;
import org.iocaste.internal.renderer.StandardContainerRenderer;
import org.iocaste.internal.renderer.TabbedPaneRenderer;
import org.iocaste.internal.renderer.TableItemRenderer;
import org.iocaste.internal.renderer.TableRenderer;
import org.iocaste.internal.renderer.TextAreaRenderer;
import org.iocaste.internal.renderer.TextFieldRenderer;
import org.iocaste.internal.renderer.TextRenderer;
import org.iocaste.internal.renderer.VirtualControlRenderer;
import org.iocaste.internal.renderer.ButtonRenderer;
import org.iocaste.internal.renderer.CanvasRenderer;
import org.iocaste.internal.renderer.CheckBoxRenderer;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.HeaderLink;
import org.iocaste.shell.common.View;
import org.iocaste.shell.common.ViewTitle;

public class HtmlRenderer {
    private String username;
    private List<String> script;
    private Map<String, List<EventHandler>> actions;
    private Function function;
    private PageContext pagectx;
    private Map<Const, Renderer<?>> renderers;
    
    public HtmlRenderer() {
        String line;
        BufferedReader reader;
        InputStream is;
        
        renderers = new HashMap<>();
        new ButtonRenderer(renderers);
        new CanvasRenderer(renderers);
        new CheckBoxRenderer(renderers);
        new DataFormRenderer(renderers);
        new DataItemRenderer(renderers);
        new DummyRenderer(renderers);
        new DummyRenderer(renderers, Const.RADIO_GROUP);
        new ExpandBarRenderer(renderers);
        new FileEntryRenderer(renderers);
        new FormRenderer(renderers);
        new FrameRenderer(renderers);
        new HtmlTagRenderer(renderers);
        new LinkRenderer(renderers);
        new ListBoxRenderer(renderers);
        new MessageRenderer(renderers);
        new NodeListItemRenderer(renderers);
        new NodeListRenderer(renderers);
        new ParameterRenderer(renderers);
        new PrintAreaRenderer(renderers);
        new RadioButtonRenderer(renderers);
        new RangeFieldRenderer(renderers);
        new StandardContainerRenderer(renderers);
        new StandardContainerRenderer(renderers, Const.TABBED_PANE_ITEM);
        new TabbedPaneRenderer(renderers);
        new TableItemRenderer(renderers);
        new TableRenderer(renderers);
        new TextAreaRenderer(renderers);
        new TextFieldRenderer(renderers);
        new TextRenderer(renderers);
        new VirtualControlRenderer(renderers);
        
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
    
    private final String composePageTrack(View view, TrackingData tracking) {
        return new StringBuffer(view.getAppName()).append(".").
                append(view.getPageName()).append(":").
                append(tracking.sessionid).append(":").
                append(tracking.logid).append(":").
                append(tracking.sequence).toString();
    }
    
    /**
     * 
     * @return
     */
    public final Map<String, List<EventHandler>> getActions() {
        return actions;
    }
    
    /**
     * 
     * @param vdata
     * @return
     */
    private final XMLElement renderHeader(View view, Config config) {
        Object[][] stylesheet;
        XMLElement linktag;
        List<HeaderLink> links;
        Element focus = view.getFocus();
        String focusname;
        ViewTitle title = view.getTitle();
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
        
        titletag.addInner((title.text == null)? "Iocaste" : title.text);

        if (focus != null) {
            focusname = focus.getHtmlName();
            config.addOnload(new StringBuffer("document.getElementById('").
                    append(focusname).append("').focus();").toString());
        }
        headtag.addChild(titletag);
        
        links = view.getLinks();
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
        stylesheet = view.getStyleSheet();
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
        List<EventHandler> handlers;
        HandlerScript handlerscript;
        Map<String, List<EventHandler>> actions;
        Map<String, HandlerScript> handlerscripts;
        XMLElement scripttag = new XMLElement("script");
        
        scripttag.setLineBreak(true);
        scripttag.add("type", "text/javascript");
        scripttag.addInner("function initialize() {");
        for (String line : config.getOnload())
            scripttag.addInner(line);
        scripttag.addInner("}");
        scripttag.addInner(script);

        handlerscripts = new HashMap<>();
        
        actions = config.getActions();
        for (String name : actions.keySet()) {
            handlers = actions.get(name);
            for (EventHandler handler : handlers) {
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
    public final List<String> run(View view, TrackingData tracking) {
        Config config;
        MessageRenderer messagerenderer;
        List<String> html = new ArrayList<>();
        List<XMLElement> tags = new ArrayList<>();
        List<XMLElement> bodycontent = new ArrayList<>();
        XMLElement htmltag = new XMLElement("html");
        XMLElement bodytag = new XMLElement("body");
        
        config = new Config();
        config.function = function;
        config.pagectx = pagectx;
        config.setView(view);
        config.setUsername(username, tracking.logid);
        config.setPageTrack(composePageTrack(view, tracking));
        config.setPopupControl(pagectx.getPopupControl());
        config.setTracking(tracking);
        
        html.add("<!DOCTYPE html>");
        bodytag.add("onLoad", "initialize()");
        for (Container container : view.getContainers())
            renderers.get(container.getType()).run(
                    bodycontent, container, config);
        
        messagerenderer = (MessageRenderer)renderers.get(Const.MESSAGE);
        messagerenderer.pagectx = pagectx;
        bodytag.addChildren(bodycontent);
        bodytag.addChild(messagerenderer.run(null, config));
        bodytag.addChild(ScreenLock.render());
        tags.add(renderHeader(view, config));
        tags.add(bodytag);
        htmltag.addChildren(tags);
        
        html.add(htmltag.toString());
        actions = config.getActions();
        
        return html;
    }
    
    /**
     * 
     * @param function
     */
    public final void setFunction(Function function) {
        this.function = function;
    }
    
    /**
     * 
     * @param pagectx
     */
    public final void setPageContext(PageContext pagectx) {
        this.pagectx = pagectx;
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