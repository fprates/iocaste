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

import org.iocaste.documents.common.DocumentModel;

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
    
    /**
     * Remove elementos.
     */
    public abstract void clear();
    
    /**
     * Retorna elemento por seu nome html.
     * @param name nome html.
     * @return
     */
    public abstract Element getElement(String name);

    /**
     * Retorna todos os elementos do container.
     * @return
     */
    public abstract Element[] getElements();
    
    /**
     * Retorna array com elementos.
     * @return elementos.
     */
    public abstract String[] getElementsNames();

    /**
     * Retorna modelo de documento importado.
     * @return modelo de documento
     */
    public abstract DocumentModel getModel();
    
    /**
     * Importa características do modelo de documento.
     * @param model modelo de documento
     */
    public abstract void importModel(DocumentModel model);

    /**
     * Indica se container tem múltiplas linhas;
     * @return true, se componente de múltiplas linhas
     */
    public abstract boolean isMultiLine();
    
    /**
     * Remove elemento do container.
     * @param element elemento
     */
    public abstract void remove(Element element);
}
