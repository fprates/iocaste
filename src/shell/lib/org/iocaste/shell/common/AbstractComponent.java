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
    private String container, text;
    private Object[] args;
    
    public AbstractComponent(View view, Const type, String name) {
        super(type, name);
        view.index(this);
    }
    
    public AbstractComponent(Container container, Const type, String name) {
        super(type, name);
        if (container == null)
            throw new RuntimeException(
                    name.concat(" had an undefined container."));
        this.container = container.getHtmlName();
        container.add(this);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Component#getContainer()
     */
    @Override
    public final Container getContainer() {
        return getView().getElement(container);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Component#getText()
     */
    @Override
    public final String getText() {
        return text;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Component#getTextArgs()
     */
    @Override
    public final Object[] getTextArgs() {
        return args;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isContainable()
     */
    @Override
    public final boolean isContainable() {
        return false;
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.AbstractElement#setHtmlName(
     *    java.lang.String)
     */
    @Override
    public void setHtmlName(String htmlname) {
        Container container;
        View view = getView();
        
        super.setHtmlName(htmlname);
        container = view.getElement(this.container);
        if (container == null)
            view.index(this);
        else
            container.add(this);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Component#setText(
     *    java.lang.String, java.lang.Object[])
     */
    public final void setText(String text, Object... args) {
        this.text = text;
        this.args = args;
    }
    
    @Override
    public void translate(MessageSource messages) {
        String message;
        
        if (!isTranslatable())
            return;
        
        message = getTranslation(messages, getName());
        if (message == null)
            return;
        
        this.text = message;
    }
    
    protected final String getTranslation(MessageSource messages, String name) {
        String message;
        
        message = messages.get(getName());
        if (message != null)
            return (args == null)? message : String.format(message, args);
        if (this.text == null)
            return null;
        message = messages.get(text);
        return (args == null || args.length == 0)?
                message : String.format(message, args);
    }
}
