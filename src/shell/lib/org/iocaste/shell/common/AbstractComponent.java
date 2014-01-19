/*  
    AbstractComponent.java, implementação abstrata de componente.
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

/**
 * Implementação abstrata de componente.
 * 
 * Componentes que não agrupam outros componentes deveriam
 * suceder este.
 * 
 * @author Francisco de Assis Prates
 *
 */
public abstract class AbstractComponent extends AbstractElement
      implements Component {
    private static final long serialVersionUID = -5327168368336946819L;
    private Container container;
    
    public AbstractComponent(Container container, Const type, String name) {
        super(type, name);
        
        this.container = container;
        
        if (container != null)
            container.add(this);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Component#getContainer()
     */
    @Override
    public final Container getContainer() {
        return container;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isContainable()
     */
    @Override
    public final boolean isContainable() {
        return false;
    }
}
