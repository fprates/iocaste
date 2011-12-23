/*  
    AbstractControlComponent.java, implementação abstrata de controle.
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
 * Implementação abstrata de controle.
 * 
 * Se deseja criar um controle (tal como botões), pode desejar
 * fazê-lo a partir dessa implementação.
 * 
 * @author francisco.prates
 *
 */
public abstract class AbstractControlComponent extends AbstractComponent
        implements ControlComponent {
    private static final long serialVersionUID = -6444029817491608067L;
    private boolean cancellable, helpcontrol;
    private String action;
    
    public AbstractControlComponent(
            Container container, Const type, String name) {
        super(container, type, name);
        cancellable = false;
        helpcontrol = false;
        setAction(name);
    }

    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.ControlComponent#getAction()
     */
    @Override
    public final String getAction() {
        return action;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.ControlComponent#isCancellable()
     */
    @Override
    public final boolean isCancellable() {
        return cancellable;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isControlComponent()
     */
    @Override
    public final boolean isControlComponent() {
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
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.ControlComponent#isHelpControl()
     */
    @Override
    public final boolean isHelpControl() {
        return helpcontrol;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.ControlComponent#setAction(
     *     java.lang.String)
     */
    @Override
    public final void setAction(String action) {
        this.action = action;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.ControlComponent#setCancellable(boolean)
     */
    @Override
    public final void setCancellable(boolean cancellable) {
        this.cancellable = cancellable;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.ControlComponent#setHelpControl(boolean)
     */
    @Override
    public final void setHelpControl(boolean helpcontrol) {
        this.helpcontrol = helpcontrol;
    }
}
