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
import java.util.Locale;
import java.util.Map;

/**
 * Interface fundamental para elementos.
 * 
 * A principal implementação é AbstractElement.
 * 
 * @author Francisco de Assis Prates
 *
 */
public interface Element extends Comparable<Element>, Serializable {
    
    /**
     * Adiciona evento html.
     * @param name nome do evento
     * @param value ação para evento
     */
    public abstract void addAttribute(String name, String value);
    
    /**
     * 
     */
    public abstract Map<String, String> getAttributes();
    
    /**
     * 
     * @return
     */
    public abstract EventHandler getEventHandler();
    
    /**
     * Retorna nomes dos eventos adicionados.
     * @return nomes
     */
    public abstract Map<String, String> getEvents();
    
    /**
     * Retorna nome html equivalente.
     * @return nome html
     */
    public abstract String getHtmlName();
    
    /**
     * Retorna localização
     * @return localização
     */
    public abstract Locale getLocale();
    
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
     * Retorna visão que contém elemento.
     * @return visão
     */
    public abstract View getView();
    
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
     * Indica se elemento é um controle.
     * @return true, se for um controle.
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
     * 
     * @return
     */
    public abstract boolean isMultipage();
    
    /**
     * Retorn true se elemento é renderizado remotamente.
     * @return true, se renderizado remotamente
     */
    public abstract boolean isRemote();
    
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
    
    public abstract void setEvent(String event, String js);
    
    /**
     * 
     * @param evhandler
     */
    public abstract void setEventHandler(EventHandler evhandler);
    
    /**
     * Define um nome html.
     * @param name nome html.
     */
    public abstract void setHtmlName(String name);
    
    /**
     * Ajusta nome da classe CSS do elemento.
     * @param style nome da classe CSS.
     */
    public abstract void setStyleClass(String style);
    
    /**
     * 
     * @param translatable
     */
    public abstract void setTranslatable(boolean translatable);
    
    /**
     * Define a visão do elemento.
     * @param view
     */
    public abstract void setView(View view);
    
    /**
     * Ajusta visibilidade do elemento.
     * @param visible true, para elemento visível.
     */
    public abstract void setVisible(boolean visible);
    
    /**
     * 
     * @param messages
     */
    public abstract void translate(MessageSource messages);
}
