package org.iocaste.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.internal.renderer.Config;
import org.iocaste.internal.renderer.Renderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.RangeInputComponent;
import org.iocaste.shell.common.View;

public class HtmlRenderer {
    private String username, msgtext, dbname;
    private Const msgtype;
    private List<String> script;
    private Set<String> actions;
    private MessageSource msgsource;
    private ControlComponent shcontrol;
    
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
    public final Set<String> getActions() {
        return actions;
    }
    
    /**
     * 
     * @param vdata
     * @return
     */
    private final XMLElement renderHeader(View view, Config config) {
        Map<String, Map<String, String>> stylesheet;
        Element focus = view.getFocus();
        String focusname, title = view.getTitle();
        XMLElement headtag = new XMLElement("head");
        XMLElement metatag = new XMLElement("meta");
        XMLElement titletag = new XMLElement("title");
        
        metatag.add("http-equiv", "Content-Type");
        metatag.add("content", "text/html; charset=utf-8");
        
        titletag.addInner((title == null)?"Iocaste" : config.
                getText(title, title));

        if (focus != null) {
            if (focus.isDataStorable() &&
                    ((InputComponent)focus).isValueRangeComponent())
                focusname = ((RangeInputComponent)focus).getLowHtmlName();
            else
                focusname = focus.getHtmlName();
            
            config.addOnload(new StringBuffer("document.getElementById('").
                    append(focusname).append("').focus();").toString());
        }
        
        headtag.addChild(metatag);
        headtag.addChild(titletag);
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
        XMLElement scripttag = new XMLElement("script");
        
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
    private final XMLElement renderStyleSheet(
            Map<String, Map<String, String>> csselements) {
        Map<String, String> properties;
        XMLElement styletag = new XMLElement("style");
        
        styletag.add("type", "text/css");
        
        if (csselements.size() == 0)
            styletag.addInner("");
        
        for (String element : csselements.keySet()) {
            properties = csselements.get(element);
            styletag.addInner(element+" {");
            
            for (String property: properties.keySet())
                styletag.addInner(new StringBuffer(property).append(": ").
                        append(properties.get(property)).
                        append(";").toString());
            
            styletag.addInner("}");
        }
        
        return styletag;
    }
    
    /**
     * Retorna visão renderizada.
     * @param view dados da visão
     * @return código html da visão
     */
    public final String[] run(View view, TrackingData tracking) {
        Config config;
        List<String> html = new ArrayList<>();
        List<XMLElement> tags = new ArrayList<>();
        List<XMLElement> bodycontent = new ArrayList<>();
        XMLElement htmltag = new XMLElement("html");
        XMLElement bodytag = new XMLElement("body");
        
        config = new Config();
        config.setView(view);
        config.setUsername(username, tracking.logid);
        config.setMessage(msgtype, msgtext);
        config.addMessageSource(view.getMessages());
        config.addMessageSource(msgsource);
        config.setPageTrack(composePageTrack(view, tracking));
        config.setDBName(dbname);
        config.setShControl(shcontrol);
        config.setTracking(tracking);
        
        html.add("<!DOCTYPE html>");
        bodytag.add("onLoad", "initialize()");
        for (Container container : view.getContainers())
            Renderer.renderContainer(bodycontent, container, config);
        
        bodytag.addChildren(bodycontent);
        tags.add(renderHeader(view, config));
        tags.add(bodytag);
        msgtext = null;
        msgtype = Const.NONE;
        htmltag.addChildren(tags);
        
        html.add(htmltag.toString());
        actions = config.getActions();
        
        return html.toArray(new String[0]);
    }
    
    /**
     * 
     * @param dbname
     */
    public final void setDBName(String dbname) {
        this.dbname = dbname;
    }
    
    /**
     * 
     * @param msgsource
     */
    public final void setMessageSource(MessageSource msgsource) {
        this.msgsource = msgsource;
    }
    
    /**
     * Ajusta texto da barra de mensagens.
     * @param msgtext texto
     */
    public final void setMessageText(String msgtext) {
        this.msgtext = msgtext;
    }
    
    /**
     * Ajusta tipo de mensagem da barra de mensagens.
     * @param msgtype
     */
    public final void setMessageType(Const msgtype) {
        this.msgtype = msgtype;
    }
    
    /**
     * 
     * @param shcontrol
     */
    public final void setShControl(ControlComponent shcontrol) {
        this.shcontrol = shcontrol;
    }
    
    /**
     * Ajusta nome do usuário
     * @param username nome
     */
    public final void setUsername(String username) {
        this.username = username;
    }
}