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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.iocaste.protocol.IocasteException;

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
    private String appname, pagename;
    private List<String> lines;
    private Set<String> containers;
    private List<HeaderLink> links;
    private Object[][] stylesheet, styleconst;
    private Map<String, Element> elements;
    private Locale locale;
    private ViewTitle title;
    
    public View(String appname, String pagename) {
        lines = new ArrayList<>();
        containers = new LinkedHashSet<>();
        elements = new HashMap<>();
        title = new ViewTitle();
        this.appname = appname;
        this.pagename = pagename;
    }
    
    /**
     * Adiciona container à visão.
     * @param container container
     */
    public final void add(Container container) {
        String htmlname = container.getHtmlName();
        container.setView(this);
        containers.add(htmlname);
        elements.put(htmlname, container);
    }
    
    public final void add(HeaderLink link) {
        if (links == null)
            links = new ArrayList<>();
        links.add(link);
    }
    
    public final void clear() {
        containers.clear();
        elements.clear();
        lines.clear();
    }
    
    /**
     * Limpa lista de saída.
     */
    public final void clearPrintLines() {
        lines.clear();
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
        Container[] containers = new Container[this.containers.size()];
        int i = 0;
        for (String name : this.containers)
            containers[i++] = (Container)elements.get(name);
        return containers;
    }
    
    /**
     * Retorna conteúdo raw.
     * @return conteúdo
     */
    public final byte[] getContent() {
        return content;
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
     * Ajusta elemento a receber foco.
     * @return
     */
    public final Element getFocus() {
        return elementfocus;
    }
    
    public final List<HeaderLink> getLinks() {
        return links;
    }
    
    /**
     * Retorna localização da visão.
     * @return localização.
     */
    public final Locale getLocale() {
        return locale;
    }
    
    /**
     * Retorna nome da página.
     * @return nome
     */
    public final String getPageName() {
        return pagename;
    }
    
    /**
     * Obtem conteúdo da lista de saída.
     * @return lista de saída
     */
    public final String[] getPrintLines() {
        return lines.toArray(new String[0]);
    }

    public final Object[][] getStyleConstants() {
        return styleconst;
    }
    
    public final Object[][] getStyleSheet() {
        return stylesheet;
    }
    
    /**
     * Retorna título da página.
     * @return título
     */
    public final ViewTitle getTitle() {
        return title;
    }
    
    /**
     * 
     * @param element
     */
    public final void index(Element element) {
        View view = element.getView();
        
        if ((view != this) && (view != null))
            throw new IocasteException(
                    "%s view mismatch.", element.getHtmlName());
        elements.put(element.getHtmlName(), element);
        element.setView(this);
    }
    
    /**
     * Adiciona linha na lista de saída.
     * @param line
     */
    public final void print(String line) {
        lines.add((line == null)? "" : line);
    }
    
    public final void println(String line) {
        print(line);
        lines.add("\n");
    }
    
    /**
     * 
     * @param element
     */
    public final void remove(Element element) {
        String htmlname = element.getHtmlName();
        elements.remove(htmlname);
        containers.remove(htmlname);
    }
    
    /**
     * Define conteúdo raw da página.
     * @param content conteúdo.
     */
    public final void setContent(byte[] content) {
        this.content = content;
    }
    
    /**
     * Define foco da página.
     * @param element elemento com foco.
     */
    public final void setFocus(Element elementfocus) {
        this.elementfocus = elementfocus;
    }
    
    /**
     * Define localização da visão.
     * @param locale localização
     */
    public final void setLocale(Locale locale) {
        this.locale = locale;
    }
    
    public final void setStyleConst(Object[][] styleconst) {
        this.styleconst = styleconst;
    }
    
    public final void setStyleSheet(Object[][] stylesheet) {
        this.stylesheet = stylesheet;
    }
    
    /**
     * Ajusta título da visão.
     * @param title título
     */
    public final void setTitle(String text, Object... args) {
        title.text = text;
        title.args = args;
    }
}
