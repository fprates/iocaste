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
    private String title;
    private String focus;
    private MessageSource messages;
    private Map<String, InputComponent> inputs;
    private Map<String, Boolean> navbarstatus;
    private List<Element> mpelements;
    private List<Container> containers;
    private String sheet;
    private String appname;
    private String pagename;
    
    public ViewData(String appname, String pagename) {
        inputs = new HashMap<String, InputComponent>();
        navbarstatus = new HashMap<String, Boolean>();
        containers = new ArrayList<Container>();
        mpelements = new ArrayList<Element>();
        this.appname = appname;
        this.pagename = pagename;
    }
    
    /**
     * Adiciona container à visão.
     * @param container container
     */
    public final void addContainer(Container container) {
        containers.add(container);
    }
    
    /**
     * Adiciona elemento multipart.
     * @param elemento
     */
    public final void addMultipartElement(Element element) {
        mpelements.add(element);
    }
    
    /**
     * Retorna elemento de um container por nome.
     * @param container container
     * @param name nome do elemento
     * @return Elemento
     */
    private Element findElement(Container container, String name) {
        String name_ = container.getName();
        
        if (name_.equals(name))
            return container;
        
        for (Element element : container.getElements()) {
            name_= element.getName();
            
            if (name_.equals(name))
                return element;
            
            if (element.isContainable())
                return findElement((Container)element, name);
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
    public final Map<String, InputComponent> getInputs() {
        return inputs;
    }
    
    /**
     * Retorna fonte de mensagens.
     * @return fonte de mensagens
     */
    public final MessageSource getMessages() {
        return messages;
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
     * Retorna nome da página.
     * @return nome
     */
    public final String getPageName() {
        return pagename;
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
