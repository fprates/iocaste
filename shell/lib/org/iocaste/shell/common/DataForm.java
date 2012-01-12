/*  
    DataForm.java, implementação de formulário de dados.
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

import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;

/**
 * Implementação de formulário de dados.
 * 
 * @author Francisco de Assis Prates
 *
 */
public class DataForm extends AbstractContainer {
    private static final long serialVersionUID = -5059126959559630847L;
    private List<String> actions;
    private boolean keyrequired;
    
    public DataForm(Container container, String name) {
        super(container, Const.DATA_FORM, name);
        
        actions = new ArrayList<String>();
        keyrequired = false;
    }
    
    /**
     * Adiciona uma ação ao formulário.
     * @param action ação
     */
    public final void addAction(String action) {
        Button button = new Button(this, action);
        
        button.setSubmit(true);
        button.setStyleClass("submit");
        button.setCancellable(false);
        
        actions.add(action);
    }
    
    /**
     * 
     * @param action
     */
    public final void addExitAction(String action) {
        Button button = new Button(this, action);
        
        button.setSubmit(true);
        button.setStyleClass("submit");
        button.setCancellable(true);
        
        actions.add(action);
    }
    
    /**
     * 
     */
    public final void clearInputs() {
        InputComponent input;
        
        for (Element element : getElements()) {
            if (!element.isDataStorable())
                continue;
            
            input = (InputComponent)element;
            input.setValue(null);
        }
    }
    
    /**
     * Retorna ações associadas ao formulário.
     * @return ações
     */
    public final String[] getActions() {
        return actions.toArray(new String[0]);
    }
    
    /**
     * 
     * @return
     */
    public final ExtendedObject getObject() {
    	InputComponent input;
    	ExtendedObject object = new ExtendedObject(getModel());
    	
        for (Element element: getElements()) {
        	if (!element.isDataStorable())
                continue;
        	
        	input = (InputComponent)element;
        	object.setValue(input.getModelItem(), input.getValue());
        }
    	
    	return object;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final String getValue(String name) {
        Element element = getElement(name);
        
        if (element.getType() != Const.DATA_ITEM)
            return null;
        
        return ((DataItem)element).getValue();
    }
    
    public final void importModel(DocumentModel model) {
        DataItem dataitem;
        
        clear();
        
        for (DocumentModelItem item : model.getItens()) {
            dataitem = new DataItem(this, Const.TEXT_FIELD, item.getName());
            dataitem.setModelItem(item);
        }
        
        super.importModel(model);
    }
    
    /**
     * 
     * @return
     */
    public final boolean isKeyRequired() {
        return keyrequired;
    }
    
    /**
     * 
     * @param keyrequired
     */
    public final void setKeyRequired(boolean keyrequired) {
        this.keyrequired = keyrequired;
    }
}
