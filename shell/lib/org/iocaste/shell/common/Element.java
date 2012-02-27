/*  
    Element.java, interface para elementos.
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

/**
 * Interface fundamental para elementos.
 * 
 * @author Francisco de Assis Prates
 *
 */
public interface Element extends Comparable<Element>, Serializable {
    
    /**
     * 
     * @param name
     * @param value
     */
    public abstract void addAttribute(String name, String value);
    
    /**
     * 
     * @param name
     * @return
     */
    public abstract String getAttribute(String name);
    
    /**
     * 
     * @return
     */
    public abstract String[] getAttributeNames();
    
    /**
     * 
     * @return
     */
    public abstract String getHtmlName();
    
    /**
     * Retorna nome do elemento.
     * @return nome do elemento.
     */
    public abstract String getName();
    
    /**
     * Retorna nome da classe CSS do elemento.
     * @return nome da classe CSS.
     */
    public abstract String getStyleClass();
    
    /**
     * Retorna tipo do elemento html.
     * @return tipo do elemento html.
     */
    public abstract Const getType();

    /**
     * 
     * @return
     */
    public abstract ViewData getView();
    
    /**
     * Retorna true se for componente que suporta
     * conteúdo multipart.
     * @return true, se conteúdo multipart.
     */
    public abstract boolean hasMultipartSupport();
    
    /**
     * Retorna true se elemento pode conter outros.
     * @return true, se for um container.
     */
    public abstract boolean isContainable();
    
    /**
     * 
     * @return
     */
    public abstract boolean isControlComponent();
    
    /**
     * Retorna true se elemento armazena conteúdo.
     * @return true, se armazena dados.
     */
    public abstract boolean isDataStorable();
    
    /**
     * Retorna true se elemento está habilitado.
     * @return true, se habilitado
     */
    public abstract boolean isEnabled();
    
    /**
     * Retorna true se elemento é visível.
     * @return true, se visível.
     */
    public abstract boolean isVisible();
    
    /**
     * Habilita funcionalidade do elemento.
     * @param enabled true, para habilitar elemento
     */
    public abstract void setEnabled(boolean enabled);
    
    /**
     * 
     * @param name
     */
    public abstract void setHtmlName(String name);
    
    /**
     * Ajusta nome da classe CSS do elemento.
     * @param style nome da classe CSS.
     */
    public abstract void setStyleClass(String style);
    
    /**
     * 
     * @param view
     */
    public abstract void setView(ViewData view);
    
    /**
     * Ajusta visibilidade do elemento.
     * @param visible true, para elemento visível.
     */
    public abstract void setVisible(boolean visible);
}
