package org.iocaste.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.ViewData;
import org.iocaste.shell.renderer.Config;
import org.iocaste.shell.renderer.Renderer;

public class HtmlRenderer {
    private String username, msgtext;
    private Const msgtype;
    private List<String> script;
    private Set<String> actions;
    private int logid;
    private Map<String, Map<String, String>> csselements;
    private MessageSource msgsource;
    
    public HtmlRenderer() {
        String line;
        InputStream is = getClass().getResourceAsStream("/META-INF/shell.js");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        
        script = new ArrayList<String>();
        logid = 0;
        
        try {
            while ((line = reader.readLine()) != null)
                script.add(line);
            
            reader.close();
            is.close();
        } catch (IOException e) {
            new RuntimeException(e);
        }
    }

    /**
     * 
     * @param vdata
     */
    private final void configNavigationBar(ViewData vdata, Config config) {
        NavigationBar navbar;
        Text txtuname, txttitle;
        Map<String, Boolean> navbarstatus;
        Container container = vdata.getNavBarContainer();
        String title = vdata.getTitle();
        
        container.clear();
        navbar = new NavigationBar(container, config);
        
        navbarstatus = vdata.getNavbarStatus();
        for (String name : navbarstatus.keySet())
            navbar.setEnabled(name, navbarstatus.get(name));
        
        txttitle = new Text(navbar.getStatusArea(), "navbar.title");
        txttitle.setStyleClass("title");
        txttitle.setText((title == null)? vdata.getAppName() : title);
        txttitle.setTag("h1");
        
        txtuname = new Text(navbar.getStatusArea(), "navbar.username");
        txtuname.setStyleClass("status");
        txtuname.setText(new StringBuilder(username).
                append("@term:").append(logid).toString());
        
        navbar.setMessage((msgtype == null)? Const.STATUS : msgtype, msgtext);
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
    private final XMLElement renderHeader(ViewData vdata, Config config) {
        Element elementfocus;
        String focus = vdata.getFocus();
        String title = vdata.getTitle();
        XMLElement headtag = new XMLElement("head");
        XMLElement metatag = new XMLElement("meta");
        XMLElement titletag = new XMLElement("title");
        
        if (focus == null) {
            elementfocus = vdata.getElementFocus();
            focus = (elementfocus == null)? null : elementfocus.getHtmlName();
        }
        
        metatag.add("http-equiv", "Content-Type");
        metatag.add("content", "text/html; charset=utf-8");
        
        titletag.addInner((title == null)?"Iocaste" : config.
                getText(title, title));

        if (focus != null)
            config.addOnload(new StringBuffer("document.getElementById('").
                    append(focus).append("').focus();").toString());
        
        headtag.addChild(metatag);
        headtag.addChild(titletag);
        headtag.addChild(renderJavaScript(config));
        headtag.addChild(renderStyleSheet(vdata));
        
        return headtag;
    }
    
    /**
     * 
     * @param vdata
     * @return
     */
    private final XMLElement renderJavaScript(Config config) {
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
     * @param line
     * @return
     */
    private final XMLElement renderPrintLines(String[] lines) {
        XMLElement pretag = new XMLElement("pre");
        
        pretag.add("class", "output_list");
        pretag.addInner(lines);
        
        return pretag;
    }
    
    /**
     * 
     * @param vdata
     * @return
     */
    private final XMLElement renderStyleSheet(ViewData vdata) {
        Map<String, String> properties;
        String sheet = vdata.getStyleSheet();
        XMLElement styletag = new XMLElement("style");
        
        styletag.add("type", "text/css");
        
        if (sheet == null)
            sheet = "DEFAULT";
        
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
     * @param vdata dados da visão
     * @return código html da visão
     */
    public final String[] run(ViewData vdata) {
        String[] printlines;
        List<String> html = new ArrayList<String>();
        List<XMLElement> tags = new ArrayList<XMLElement>();
        List<XMLElement> bodycontent = new ArrayList<XMLElement>();
        XMLElement htmltag = new XMLElement("html");
        XMLElement bodytag = new XMLElement("body");
        Config config = new Config();
        
        config.addMessageSource(vdata.getMessages());
        config.addMessageSource(msgsource);
        config.setPageTrack(new StringBuffer(vdata.getAppName()).append(".").
                append(vdata.getPageName()).append(":").append(logid).
                toString());
        
        html.add("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" " +
                "\"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");

        bodytag.add("onLoad", "initialize()");
        
        configNavigationBar(vdata, config);
        
        for (Container container : vdata.getContainers())
            Renderer.renderContainer(bodycontent, container, config);
        
        bodytag.addChildren(bodycontent);
        tags.add(renderHeader(vdata, config));
        tags.add(bodytag);
        
        msgtext = null;
        msgtype = Const.NONE;
        
        htmltag.addChildren(tags);
        printlines = vdata.getPrintLines();
        if (printlines.length > 0)
            htmltag.addChild(renderPrintLines(printlines));
        
        vdata.clearPrintLines();
        
        html.add(htmltag.toString());
        
        actions = config.getActions();
        
        return html.toArray(new String[0]);
    }

    /**
     * 
     * @param elements
     */
    public final void setCssElements(Map<String,
            Map<String, String>> csselements) {
        this.csselements = csselements;
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
     * 
     * @param logid
     */
    public final void setLogid(int logid) {
        this.logid = logid;
    }
    
    /**
     * Ajusta tipo de mensagem da barra de mensagens.
     * @param msgtype
     */
    public final void setMessageType(Const msgtype) {
        this.msgtype = msgtype;
    }
    
    /**
     * Ajusta nome do usuário
     * @param username nome
     */
    public final void setUsername(String username) {
        this.username = username;
    }
}
