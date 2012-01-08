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
import java.util.Map;

/**
 * Implementação de camada de visão
 * 
 * @author Francisco Prates
 *
 */
public class ViewData implements Serializable {
    private static final long serialVersionUID = -8331879385859372046L;
    private String title, focus, sheet, appname, pagename;
    private String rapp, rpage, messagetext;
    private MessageSource messages;
    private List<String> inputs, exports;
    private Map<String, Boolean> navbarstatus;
    private List<Element> mpelements;
    private List<Container> containers;
    private Map<String, Object> parameters;
    private Container nbcontainer;
    private boolean disabledhead, reloadable, pagecall;
    private Const messagetype;
    
    public ViewData(String appname, String pagename) {
        inputs = new ArrayList<String>();
        exports = new ArrayList<String>();
        navbarstatus = new HashMap<String, Boolean>();
        parameters = new HashMap<String, Object>();
        containers = new ArrayList<Container>();
        mpelements = new ArrayList<Element>();
        nbcontainer = new StandardContainer(null, "navbar");
        containers.add(nbcontainer);
        disabledhead = false;
        
        this.appname = appname;
        this.pagename = pagename;
        
        clearRedirect();
    }
    
    /**
     * Adiciona container à visão.
     * @param container container
     */
    public final void addContainer(Container container) {
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
    public final void addMultipartElement(Element element) {
        mpelements.add(element);
    }
    
    /**
     * 
     * @param name
     * @param value
     */
    public final void addParameter(String name, Object value) {
        parameters.put(name, value);
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
    
    public final void clearRedirect() {
        rapp = null;
        rpage = null;
        messagetext = null;
        messagetype = null;
        reloadable = false;
        pagecall = false;
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
        pagecall = false;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public final boolean equals(Object object) {
    	ViewData viewdata;
    	
    	if (object == this)
    		return true;
    	
    	if (!(object instanceof ViewData))
    		return false;
    	
    	viewdata = (ViewData)object;
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
        exports.add(name);
        addParameter(name, value);
    }
    
    /**
     * Retorna elemento de um container por nome.
     * @param container container
     * @param name nome do elemento
     * @return Elemento
     */
    private final Element findElement(Container container, String name) {
        Element element_;
        String name_ = container.getName();
        
        if (name_.equals(name))
            return container;
        
        for (Element element : container.getElements()) {
            name_= element.getName();
            
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
     * Retorna elemento por nome.
     * @param name nome
     * @return elemento
     */
    public final Element getElement(String name) {
        Element element = null;
        
        for (Container container : containers) {
            element = findElement(container, name);
            if (element != null)
                break;
        }
        
        return element;
    }
    
    /**
     * 
     * @return
     */
    public final String[] getExportable() {
        return exports.toArray(new String[0]);
    }
    
    /**
     * Retorna nome do componente com foco.
     * @return nome do componente
     */
    public final String getFocus() {
        return focus;
    }
    
    /**
     * Retorna mapa com componentes de entrada de dados.
     * @return mapa de componentes.
     */
    public final String[] getInputs() {
        return inputs.toArray(new String[0]);
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
    public final Element[] getMultipartElements() {
        return mpelements.toArray(new Element[0]);
    }
    
    /**
     * Retorna status dos elementos da barra de navegação.
     * @return status dos elementos
     */
    public final Map<String, Boolean> getNavbarStatus() {
        return navbarstatus;
    }
    
    /**
     * 
     * @return
     */
    public final Container getNavBarContainer() {
        return nbcontainer;
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
    public final Object getParameter(String name) {
        return parameters.get(name);
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
     * Retorna nome da folha de estilos.
     * @return nome
     */
    public final String getStyleSheet() {
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
     * @param app
     * @param page
     */
    public final void redirect(String app, String page) {
        rapp = app;
        rpage = page;
        
        pagecall = true;
    }
    
    /**
     * Ajusta elemento com foco.
     * @param focus nome do elemento
     */
    public final void setFocus(String focus) {
        this.focus = focus;
    }
    
    /**
     * Define fonte de mensagens.
     * @param messages fonte de mensagens
     */
    public final void setMessages(MessageSource messages) {
        this.messages = messages;
    }
    
    /**
     * Habilita ação da barra de navegação.
     * @param name nome da ação
     * @param enabled true, para habilitar ação
     */
    public final void setNavbarActionEnabled(String name, boolean enabled) {
        if (navbarstatus.containsKey(name))
            navbarstatus.remove(name);
        
        navbarstatus.put(name, enabled);
    }

    /**
     * 
     * @param reloadable
     */
    public final void setReloadableView(boolean reloadable) {
        this.reloadable = reloadable;
    }
    
    /**
     * Ajusta nome da folha de estilos.
     * @param sheet nome
     */
    public final void setStyleSheet(String sheet) {
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
