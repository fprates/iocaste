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
import org.iocaste.internal.renderer.Renderer;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.HeaderLink;
import org.iocaste.shell.common.Media;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.View;
import org.iocaste.shell.common.ViewTitle;

public class HtmlRenderer {
    private String username;
    private List<String> script;
    private Map<String, String> actions;
    private Function function;
    private PageContext pagectx;
    
    public HtmlRenderer() {
        String line;
        BufferedReader reader;
        InputStream is = getClass().getResourceAsStream("/META-INF/shell.js");
        
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
    public final Map<String, String> getActions() {
        return actions;
    }
    
    /**
     * 
     * @param vdata
     * @return
     */
    private final XMLElement renderHeader(View view, Config config) {
        StyleSheet stylesheet;
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
        stylesheet = view.styleSheetInstance();
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
        XMLElement scripttag = new XMLElement("script");
        
        scripttag.setLineBreak(true);
        scripttag.add("type", "text/javascript");
        scripttag.addInner("function initialize() {");
        
        for (String line : config.getOnload())
            scripttag.addInner(line);

        scripttag.addInner("}");
        scripttag.addInner(script);
        
        return scripttag;
    }
    
    /**
     * 
     * @param csselements
     * @return
     */
    private final XMLElement renderStyleSheet(StyleSheet stylesheet) {
        Map<String, String> properties;
        Media media;
        boolean defaultmedia;
        XMLElement styletag;
        Map<String, Map<String, String>> csselements;
        Map<String, Media> medias = stylesheet.getMedias();

        styletag = new XMLElement("style");
        styletag.add("type", "text/css");
        styletag.setLineBreak(true);
            
        for (String mediakey : medias.keySet()) {
            media = medias.get(mediakey);
            defaultmedia = mediakey.equals("default");
            csselements = stylesheet.getElements(mediakey);
            if (!defaultmedia) {
                styletag.addInner(new StringBuilder("@media ").
                        append(media.getRule()).append(" {").toString());
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
            Renderer.renderContainer(bodycontent, container, config);
        
        bodytag.addChildren(bodycontent);
        bodytag.addChild(MessageRenderer.render(pagectx, config));
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
