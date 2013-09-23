/*  
    Component.java, interface para componente.
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
 * Interface fundamental para componentes.
 * 
 * @author Francisco de Assis Prates
 *
 */
public interface Component extends Element {

    /**
     * Retorna o container que contém este componente.
     * @return container.
     */
    public abstract Container getContainer();
    
    /**
     * Retorna texto do componente
     * @return
     */
    public abstract String getText();
    
    /**
     * Verifica permissão de tradução
     * @return true, se elemento for traduzível
     */
    public abstract boolean isTranslatable();
    
    /**
     * Ajusta texto do component
     * @param text
     */
    public abstract void setText(String text);
    
    /**
     * Ajusta permissão para tradução
     * @param translate
     */
    public abstract void setTranslatable(boolean translate);
}
