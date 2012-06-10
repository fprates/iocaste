/*  
    ViewData.java, implementação de camada de visão.
    Copyright (C) 2011  Francisco de Assis Prates
   
    Este programa é software livre; você pode redistribuí-lo e/ou
    modificá-lo sob os termos da Licença Pública Geral GNU, conforme
    publicada pela Free Software Foundation; tanto a versão 2 da
    Licença como (a seu critério) qualquer versão mais nova.

    Este programa é distribuído na expectativa de ser útil, mas SEM
    QUALQUER GARANTIA; sem mesmo a garantia implícita de
    COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
    PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
    detalhes.
 
    Você deve ter recebido uma cópia da Licença Pública Geral GNU
    junto com este programa; se não, escreva para a Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
    02111-1307, USA.
*/

package org.iocaste.shell.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Implementação de camada de visão
 * 
 * @author Francisco Prates
 *
 */
public class View implements Serializable {
    private static final long serialVersionUID = -8331879385859372046L;
    private byte[] content;
    private Element elementfocus;
    private String title, appname, pagename;
    private String contenttype, rapp, rpage, messagetext;
    private MessageSource messages;
    private List<String> inputs, lines;
    private List<MultipartElement> mpelements;
    private List<Container> containers;
    private Map<String, Object> parameters;
    private Map<String, String> headervalues;
    private Map<String, Map<String, String>> sheet;
    private boolean disabledhead, reloadable, dontpushpage, pagecall;
    private Const messagetype;
    private Locale locale;
    
    public View(String appname, String pagename) {
        inputs = new ArrayList<String>();
        lines = new ArrayList<String>();
        parameters = new HashMap<String, Object>();
        headervalues = new HashMap<String, String>();
        containers = new ArrayList<Container>();
        mpelements = new ArrayList<MultipartElement>();
        disabledhead = false;
        dontpushpage = false;
        contenttype = null;
        
        this.appname = appname;
        this.pagename = pagename;
        
        clearRedirect();
    }
    
    /**
     * Adiciona container à visão.
     * @param container container
     */
    public final void add(Container container) {
        containers.add(container);
    }
    
    /**
     * 
     * @param name
     */
    public final void addInput(String name) {
        inputs.add(name);
    }
    
    /**
     * Adiciona elemento multipart.
     * @param elemento
     */
    public final void addMultipartElement(MultipartElement element) {
        mpelements.add(element);
    }
    
    /**
     * 
     */
    public final void clearInputs() {
        inputs.clear();
    }
    
    /**
     * 
     */
    public final void clearParameters() {
        parameters.clear();
    }
    
    /**
     * 
     */
    public final void clearPrintLines() {
        lines.clear();
    }
    
    /**
     * 
     */
    public final void clearRedirect() {
        rapp = null;
        rpage = null;
        messagetext = null;
        messagetype = null;
        reloadable = false;
        pagecall = false;
        dontpushpage = false;
    }
    
    /**
     * 
     */
    public final void disableHead() {
        disabledhead = true;
    }
    
    /**
     * 
     */
    public final void dontPushPage() {
        dontpushpage = true;
        pagecall = false;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public final boolean equals(Object object) {
    	View viewdata;
    	
    	if (object == this)
    		return true;
    	
    	if (!(object instanceof View))
    		return false;
    	
    	viewdata = (View)object;
    	if ((!appname.equals(viewdata.getAppName())) ||
    			(!pagename.equals(viewdata.getPageName())))
    		return false;
    	
    	return true;
    }
    
    /**
     * 
     * @param name
     * @param value
     */
    public final void export(String name, Object value) {
        if (parameters.containsKey(name))
            parameters.remove(name);
        
        parameters.put(name, value);
    }
    
    /**
     * Retorna elemento de um container por nome.
     * @param container container
     * @param name nome do elemento
     * @return Elemento
     */
    private final Element findElement(Container container, String name) {
        Element element_;
        String name_ = container.getHtmlName();
        
        if (name_.equals(name))
            return container;
        
        for (Element element : container.getElements()) {
            name_= element.getHtmlName();
            
            if (name_.equals(name))
                return element;
            
            if (element.isContainable()) {
                element_ = findElement((Container)element, name);
                if (element_ == null)
                    continue;
                
                return element_;
            }
        }
        
        return null;
    }
    
    /**
     * Retorna nome da aplicação.
     * @return nome
     */
    public final String getAppName() {
        return appname;
    }
    
    /**
     * Retorna containers.
     * @return array de containers
     */
    public final Container[] getContainers() {
        return containers.toArray(new Container[0]);
    }
    
    /**
     * 
     * @return
     */
    public final byte[] getContent() {
        return content;
    }
    
    /**
     * 
     * @return
     */
    public final String getContentType() {
        return contenttype;
    }
    
    /**
     * Retorna elemento por nome.
     * @param name nome
     * @return elemento
     */
    @SuppressWarnings("unchecked")
    public final <T extends Element> T getElement(String name) {
        Element element = null;
        
        for (Container container : containers) {
            element = findElement(container, name);
            if (element != null)
                break;
        }
        
        return (T)element;
    }
    
    /**
     * 
     * @return
     */
    public final String[] getExportable() {
        return parameters.keySet().toArray(new String[0]);
    }
    
    /**
     * 
     * @return
     */
    public final Element getFocus() {
        return elementfocus;
    }
    
    /**
     * 
     * @return
     */
    public final String[] getHeaderKeys() {
        return headervalues.keySet().toArray(new String[0]);
    }
    
    /**
     * 
     * @param key
     * @return
     */
    public final String getHeader(String key) {
        return headervalues.get(key);
    }
    
    /**
     * Retorna mapa com componentes de entrada de dados.
     * @return mapa de componentes.
     */
    public final String[] getInputs() {
        return inputs.toArray(new String[0]);
    }
    
    /**
     * 
     * @return
     */
    public final Locale getLocale() {
        return locale;
    }
    
    /**
     * Retorna fonte de mensagens.
     * @return fonte de mensagens
     */
    public final MessageSource getMessages() {
        return messages;
    }
    
    /**
     * @return the messagetype
     */
    public final Const getMessageType() {
        return messagetype;
    }
    
    /**
     * Retorna elementos multipart.
     * @return elementos
     */
    public final MultipartElement[] getMultipartElements() {
        return mpelements.toArray(new MultipartElement[0]);
    }
    
    /**
     * Retorna nome da página.
     * @return nome
     */
    public final String getPageName() {
        return pagename;
    }
    
    /**
     * Retorna valor do parâmetro da visão.
     * @param name nome
     * @return value valor
     */
    @SuppressWarnings("unchecked")
    public final <T> T getParameter(String name) {
        return (T)parameters.get(name);
    }
    
    /**
     * 
     * @return
     */
    public final String[] getPrintLines() {
        return lines.toArray(new String[0]);
    }
    
    /**
     * 
     * @return
     */
    public final String getRedirectedApp() {
        return rapp;
    }
    
    /**
     * 
     * @return
     */
    public final String getRedirectedPage() {
        return rpage;
    }
    
    /**
     * Retorna folha de estilos.
     * @return nome
     */
    public final Map<String, Map<String, String>> getStyleSheet() {
        return sheet;
    }
    
    /**
     * Retorna título da página.
     * @return título
     */
    public final String getTitle() {
        return title;
    }
    
    /**
     * 
     * @return
     */
    public final String getTranslatedMessage() {
        if (messagetext == null)
            return null;
        
        if (messages == null)
            return messagetext;
        else
            return messages.get(messagetext, messagetext);
    }
    
    /**
     * 
     * @return
     */
    public final boolean hasPageCall() {
        return pagecall;
    }
    
    /**
     * 
     * @return
     */
    public final boolean isHeadDisabled() {
        return disabledhead;
    }
    
    /**
     * 
     * @return
     */
    public final boolean isReloadableView() {
        return reloadable;
    }
    
    /**
     * 
     * @param messagetype
     * @param messagetext
     */
    public final void message(Const messagetype, String messagetext) {
        this.messagetype = messagetype;
        this.messagetext = messagetext;
    }
    
    /**
     * 
     * @param line
     */
    public final void print(String line) {
        lines.add(line);
    }
    
    /**
     * 
     * @param page
     */
    public final void redirect(String page) {
        redirect(null, page);
    }
    
    /**
     * 
     * @param app
     * @param page
     */
    public final void redirect(String app, String page) {
        rapp = app;
        rpage = page;
        
        if (!dontpushpage)
            pagecall = true;
    }
    
    /**
     * 
     * @param content
     */
    public final void setContent(byte[] content) {
        this.content = content;
    }
    
    /**
     * 
     * @param contenttype
     */
    public final void setContentType(String contenttype) {
        this.contenttype = contenttype;
    }
    
    /**
     * 
     * @param element
     */
    public final void setFocus(Element elementfocus) {
        this.elementfocus = elementfocus;
    }
    
    /**
     * 
     * @param key
     * @param value
     */
    public final void setHeader(String key, String value) {
        if (headervalues.containsKey(key))
            headervalues.remove(key);
        
        headervalues.put(key, value);
    }
    
    /**
     * 
     * @param locale
     */
    public final void setLocale(Locale locale) {
        this.locale = locale;
    }
    
    /**
     * Define fonte de mensagens.
     * @param messages fonte de mensagens
     */
    public final void setMessages(MessageSource messages) {
        this.messages = messages;
    }

    /**
     * 
     * @param reloadable
     */
    public final void setReloadableView(boolean reloadable) {
        this.reloadable = reloadable;
    }
    
    /**
     * Ajusta folha de estilos.
     * @param sheet nome
     */
    public final void setStyleSheet(Map<String, Map<String, String>> sheet) {
        this.sheet = sheet;
    }
    
    /**
     * Ajusta título da visão.
     * @param title título
     */
    public final void setTitle(String title) {
        this.title = title;
    }
}
