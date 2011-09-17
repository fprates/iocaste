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

import java.util.LinkedHashSet;
import java.util.Set;

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
    private Set<Element> elements;

    public AbstractContainer(Container container, Const type, String name) {
        super(type, name);
        elements = new LinkedHashSet<Element>();
        
        if (container != null)
            container.add(this);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Container#add(org.iocaste.shell.common.Element)
     */
    @Override
    public final void add(Element element) {
        elements.add(element);
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
     * @see org.iocaste.shell.common.Container#getElements()
     */
    @Override
    public final Element[] getElements() {
        return elements.toArray(new Element[0]);
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
     * @see org.iocaste.shell.common.Element#isDataStorable()
     */
    @Override
    public final boolean isDataStorable() {
        return false;
    }
}
