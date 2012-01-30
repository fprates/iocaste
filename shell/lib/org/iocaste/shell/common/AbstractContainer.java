/*  
    AbstractContainer.java, implementação abstrata de container.
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

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;

/**
 * Implementação abstrata para containers.
 * 
 * Se deseja criar um novo container, pode querer
 * fazê-lo a partir dessa implementação.
 * 
 * @author Francisco de Assis Prates
 *
 */
public abstract class AbstractContainer
    extends AbstractElement implements Container {
    private static final long serialVersionUID = 8676224931708725226L;
    private Map<String, Element> elements;
    private DocumentModel model;

    public AbstractContainer(Container container, Const type, String name) {
        super(type, name);
        elements = new LinkedHashMap<String, Element>();
        
        if (container != null)
            container.add(this);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Container#add(
     *    org.iocaste.shell.common.Element)
     */
    @Override
    public void add(Element element) {
        elements.put(element.getName(), element);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Container#clear()
     */
    @Override
    public final void clear() {
        if (elements.size() > 0)
            elements.clear();
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Container#getElement(java.lang.String)
     */
    public final Element getElement(String name) {
        return elements.get(name);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Container#getElements()
     */
    public Element[] getElements() {
        return elements.values().toArray(new Element[0]);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Container#getElementsNames()
     */
    @Override
    public final String[] getElementsNames() {
        return elements.keySet().toArray(new String[0]);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Container#getModel()
     */
    @Override
    public final DocumentModel getModel() {
        return model;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Container#importModel(
     *    org.iocaste.documents.common.DocumentModel)
     */
    @Override
    public void importModel(DocumentModel model) {
        this.model = model;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isContainable()
     */
    @Override
    public final boolean isContainable() {
        return true;
    }

    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isControlComponent()
     */
    public final boolean isControlComponent() {
        return false;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isDataStorable()
     */
    @Override
    public final boolean isDataStorable() {
        return false;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Container#remove(
     *     org.iocaste.shell.common.Element)
     */
    @Override
    public void remove(Element element) {
        if (element.isContainable())
            ((Container)element).clear();
        
        elements.remove(element.getName());
    }
}
