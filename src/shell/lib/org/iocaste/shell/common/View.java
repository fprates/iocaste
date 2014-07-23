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
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Implementação de camada de visão
 * 
 * @author Francisco Prates
 *
 */
public class View implements Serializable {
    private static final long serialVersionUID = -8331879385859372046L;
    public static final boolean INITIALIZE = true;
    private byte[] content;
    private Element elementfocus;
    private String title, appname, pagename, actioncontrol;
    private String contenttype, rapp, rpage, messagetext;
    private MessageSource messages;
    private Set<String> initparams;
    private List<String> inputs, lines;
    private List<MultipartElement> mpelements;
    private List<Container> containers;
    private Map<String, Object> parameters;
    private Map<String, String> headervalues;
    private Map<String, Map<String, String>> sheet;
    private Map<String, Element> elements;
    private boolean reloadable, dontpushpage, pagecall, initialize, keepview;
    private Const messagetype;
    private Locale locale;
    
    public View(String appname, String pagename) {
        inputs = new ArrayList<>();
        lines = new ArrayList<>();
        parameters = new HashMap<>();
        headervalues = new HashMap<>();
        containers = new ArrayList<>();
        mpelements = new ArrayList<>();
        initparams = new HashSet<>();
        elements = new HashMap<>();
        
        this.appname = appname;
        this.pagename = pagename;
        
        clearRedirect();
    }
    
    /**
     * Adiciona container à visão.
     * @param container container
     */
    public final void add(Container container) {
        container.setView(this);
        containers.add(container);
        elements.put(container.getHtmlName(), container);
    }
    
    /**
     * Adiciona componente de entrada.
     * @param name componente de entrada
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
     * Limpa componentes de entrada.
     */
    public final void clearInputs() {
        inputs.clear();
    }
    
    /**
     * Limpa parâmetros da visão.
     */
    public final void clearExports() {
        parameters.clear();
    }
    
    /**
     * 
     */
    public final void clearInitExports() {
        for (String name : initparams)
            parameters.remove(name);
        initparams.clear();
    }
    
    public final void clearMultipartElements() {
        mpelements.clear();
    }
    
    /**
     * Limpa lista de saída.
     */
    public final void clearPrintLines() {
        lines.clear();
    }
    
    /**
     * Limpa dados de redirecionamento de visão.
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
     * Não salva página na pilha de chamada.
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
     * Exporta parâmetro para próxima visão.
     * @param name nome
     * @param value valor
     */
    public final void export(String name, Object value) {
        if (parameters.containsKey(name))
            parameters.remove(name);
        
        parameters.put(name, value);
    }
    
    public final String getActionControl() {
        return actioncontrol;
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
     * Retorna conteúdo raw.
     * @return conteúdo
     */
    public final byte[] getContent() {
        return content;
    }
    
    /**
     * Ajusta tipo de conteúdo.
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
        return (T)elements.get(name);
    }
    
    /**
     * 
     * @return
     */
    public final Map<String, Element> getElements() {
        return elements;
    }
    
    /**
     * Retorna parâmetros exportáveis.
     * @return nomes de parâmetros
     */
    public final String[] getExportable() {
        return parameters.keySet().toArray(new String[0]);
    }
    
    /**
     * Ajusta elemento a receber foco.
     * @return
     */
    public final Element getFocus() {
        return elementfocus;
    }
    
    /**
     * Retorna parâmetros a serem passados para setHeader() em HttpResponse.
     * @return nomes de parâmetros.
     */
    public final String[] getHeaderKeys() {
        return headervalues.keySet().toArray(new String[0]);
    }
    
    /**
     * Retorna valor de parâmetro para header em response.
     * @param key nome
     * @return valor
     */
    public final String getHeader(String key) {
        return headervalues.get(key);
    }
    
    /**
     * 
     * @return
     */
    public final String[] getInitParameters() {
        return initparams.toArray(new String[0]);
    }
    
    /**
     * Retorna mapa com componentes de entrada de dados.
     * @return mapa de componentes.
     */
    public final String[] getInputs() {
        return inputs.toArray(new String[0]);
    }
    
    /**
     * Retorna localização da visão.
     * @return localização.
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
     * @return tipo de mensagem.
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
     * Obtem conteúdo da lista de saída.
     * @return lista de saída
     */
    public final String[] getPrintLines() {
        return lines.toArray(new String[0]);
    }
    
    /**
     * Retorna aplicação redirecionada.
     * @return aplicação.
     */
    public final String getRedirectedApp() {
        return rapp;
    }
    
    /**
     * Retorna página redirecionada.
     * @return página
     */
    public final String getRedirectedPage() {
        return rpage;
    }
    
    /**
     * Retorna título da página.
     * @return título
     */
    public final String getTitle() {
        return title;
    }
    
    /**
     * Retorna mensagem de texto.
     * @return mensagem.
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
     * Indica se deve ser feito redirecionamento de página.
     * @return true, se for redirecionar.
     */
    public final boolean hasPageCall() {
        return pagecall;
    }

    
    /**
     * 
     * @param element
     */
    public final void index(Element element) {
        View view = element.getView();
        
        if ((view != this) && (view != null))
            throw new RuntimeException(
                    element.getHtmlName().concat(" view mismatch."));
        elements.put(element.getHtmlName(), element);
        element.setView(this);
    }
    
    /**
     * Indica se executou o procedimento de inicialização da visão.
     * @return true, se visão foi inicializada.
     */
    public final boolean isInitializable() {
        return initialize;
    }
    
    /**
     * Indica se foi solicitada recarga da visão.
     * @return
     */
    public final boolean isReloadableView() {
        return reloadable;
    }
    
    /**
     * 
     * @return
     */
    public final boolean keepView() {
        return keepview;
    }
    
    /**
     * Ajusta tipo e texto da mensagem.
     * @param messagetype tipo
     * @param messagetext texto
     */
    public final void message(Const messagetype, String messagetext) {
        this.messagetype = messagetype;
        this.messagetext = messagetext;
    }
    
    /**
     * Adiciona linha na lista de saída.
     * @param line
     */
    public final void print(String line) {
        lines.add(line);
    }
    
    /**
     * Redireciona visão.
     * Não inicializa a visão.
     * @param page página.
     */
    public final void redirect(String page) {
        redirect(null, page, false);
    }
    
    public final void redirect(String page, boolean initialize) {
        redirect(null, page, initialize);
    }
    
    /**
     * Redireciona aplicação e visão.
     * Não inicializa a visão.
     * @param app aplicação
     * @param page página
     */
    public final void redirect(String app, String page) {
        redirect(app, page, false);
    }
    
    /**
     * Redireciona aplicação e visão.
     * @param app aplicação
     * @param page página
     * @param initialize true, para inicializar a visão.
     */
    public final void redirect(String app, String page, boolean initialize) {
        rapp = app;
        rpage = page;
        this.initialize = initialize;
        
        if (!dontpushpage)
            pagecall = true;
    }
    
    /**
     * 
     * @param element
     */
    public final void remove(Element element) {
        elements.remove(element.getHtmlName());
    }
    
    /**
     * 
     * @param actioncontrol
     */
    public final void setActionControl(String actioncontrol) {
        this.actioncontrol = actioncontrol;
    }
    
    /**
     * Define conteúdo raw da página.
     * @param content conteúdo.
     */
    public final void setContent(byte[] content) {
        this.content = content;
    }
    
    /**
     * Define tipo de conteúdo da página.
     * @param contenttype tipo de conteúdo.
     */
    public final void setContentType(String contenttype) {
        this.contenttype = contenttype;
    }
    
    /**
     * Define foco da página.
     * @param element elemento com foco.
     */
    public final void setFocus(Element elementfocus) {
        this.elementfocus = elementfocus;
    }
    
    /**
     * Define parâmetro para header http.
     * @param key nome
     * @param value valor
     */
    public final void setHeader(String key, String value) {
        if (headervalues.containsKey(key))
            headervalues.remove(key);
        
        headervalues.put(key, value);
    }
    
    /**
     * 
     * @param initialize
     */
    public final void setInitialize(boolean initialize) {
        this.initialize = initialize;
    }
    
    /**
     * 
     * @param keepview
     */
    public final void setKeepView(boolean keepview) {
        this.keepview = keepview;
    }
    
    /**
     * Define localização da visão.
     * @param locale localização
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
     * @param name
     * @param value
     */
    public final void setParameter(String name, Object value) {
        if (initparams.contains(name))
            parameters.remove(name);
        else
            initparams.add(name);
        
        parameters.put(name, value);
    }
    
    /**
     * Ajusta recarga da visão.
     * @param reloadable true, para recarga da visão.
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
    
    public final StyleSheet styleSheetInstance() {
        return new StyleSheet(sheet);
    }
}
