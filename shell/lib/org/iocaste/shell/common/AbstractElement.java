/*  
    AbstractElement.java, implementação abstrata de componente.
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

import java.util.HashMap;
import java.util.Map;

/**
 * Implementação abstrata de elemento.
 * 
 * Todos os componentes html devem, em algum momento, suceder este.
 * Os métodos são muito básicos; convém utilizar as implementações
 * posteriores (como AbstractComponent ou AbstractContainer).
 * 
 * @author Francisco de Assis Prates
 *
 */
public abstract class AbstractElement implements Element {
    private static final long serialVersionUID = -4565295670850530184L;
    private Const type;
    private String name, htmlname, style;
    private boolean enabled, visible;
    private ViewData view;
    private Map<String, String> attributes;
    
    public AbstractElement(Const type, String name) {
        if (name == null)
            throw new RuntimeException("Component name is obligatory");
        
        this.type = type;
        this.name = name;
        htmlname = name;
        style = "";
        enabled = true;
        visible = true;
        
        attributes = new HashMap<String, String>();
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#addAttribute(
     *     java.lang.String, java.lang.String)
     */
    @Override
    public final void addAttribute(String name, String value) {
        attributes.put(name, value);
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public final int compareTo(Element element) {
        if (equals(element))
            return 0;
        
        return name.compareTo(element.getName());
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(Object object) {
        AbstractElement element;
        
        if (object == this)
            return true;
        
        if (!(object instanceof AbstractElement))
            return false;
        
        element = (AbstractElement)object;
        if (!name.equals(element.getHtmlName()))
            return false;
        
        return true;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#getAttribute(java.lang.String)
     */
    @Override
    public final String getAttribute(String name) {
        return attributes.get(name);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#getAttributeNames()
     */
    @Override
    public final String[] getAttributeNames() {
        return attributes.keySet().toArray(new String[0]);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#getHtmlName()
     */
    @Override
    public final String getHtmlName() {
        return htmlname;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#getName()
     */
    @Override
    public final String getName() {
        return name;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#getStyleClass()
     */
    @Override
    public final String getStyleClass() {
        return style;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#getType()
     */
    @Override
    public final Const getType() {
        return type;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#getView()
     */
    @Override
    public final ViewData getView() {
        return view;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode() {
        return name.hashCode();
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#hasMultipartSupport()
     */
    @Override
    public boolean hasMultipartSupport() {
        return false;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isEnabled()
     */
    @Override
    public final boolean isEnabled() {
        return enabled;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isVisible()
     */
    @Override
    public final boolean isVisible() {
        return visible;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#setEnabled(boolean)
     */
    @Override
    public final void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#setHtmlName(java.lang.String)
     */
    @Override
    public void setHtmlName(String htmlname) {
        this.htmlname = htmlname;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#setStyleClass(java.lang.String)
     */
    @Override
    public final void setStyleClass(String style) {
        this.style = style;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#setView(
     *     org.iocaste.shell.common.ViewData)
     */
    @Override
    public final void setView(ViewData view) {
        this.view = view;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#setVisible(boolean)
     */
    @Override
    public final void setVisible(boolean visible) {
        this.visible = visible;
    }
}
