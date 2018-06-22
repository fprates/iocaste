/*  
    Container.java, interface para container.
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

import java.util.Set;

import org.iocaste.shell.common.tooldata.ToolData;

/**
 * Interface fundamental para containeres.
 * 
 * Se seu componente agrupa outros elementos, deveria contemplar
 * essa interface.
 * 
 * @author Francisco de Assis Prates
 *
 */
public interface Container extends Element {
    
    /**
     * Adiciona elemento.
     * @param elemento.
     */
    public abstract void add(Element element);
    
    public abstract void add(ToolData child);
    
    /**
     * Remove elementos.
     */
    public abstract void clear();
    
    /**
     * 
     * @return
     */
    public abstract Container getContainer();
    
    /**
     * Retorna elemento por seu nome html.
     * @param name nome html.
     * @return
     */
    public abstract <T extends Element> T getElement(String name);

    /**
     * Retorna todos os elementos do container.
     * @return
     */
    public abstract <T extends Element> Set<T> getElements();
    
    /**
     * Retorna array com elementos.
     * @return elementos.
     */
    public abstract String[] getElementsNames();

    /**
     * Indica se container tem múltiplas linhas;
     * @return true, se componente de múltiplas linhas
     */
    public abstract boolean isMultiLine();
    
    public abstract boolean isVisible(Element element);
    
    /**
     * Remove elemento do container.
     * @param element elemento
     */
    public abstract void remove(Element element);
    
    /**
     * 
     * @return
     */
    public abstract int size();
    
}
