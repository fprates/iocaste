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

import org.iocaste.documents.common.DataType;
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
    private boolean keyrequired;
    
    public DataForm(Container container, String name) {
        super(container, Const.DATA_FORM, name);
        
        keyrequired = false;
    }

    /**
     * 
     * @param item
     */
    public final void add(DataItem item) {
        super.add(item);
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
     * 
     * @param name
     * @return
     */
    public final DataItem get(String name) {
        return (DataItem)getElement(name);
    }
    
    /**
     * 
     * @return
     */
    public final ExtendedObject getObject() throws Exception {
    	InputComponent input;
    	ExtendedObject object = new ExtendedObject(getModel());
    	
        for (Element element: getElements()) {
        	if (!element.isDataStorable())
                continue;
        	
        	input = (InputComponent)element;
        	object.setValue(input.getName(), Shell.getInputValue(input));
        }
    	
    	return object;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractContainer#importModel(
     *     org.iocaste.documents.common.DocumentModel)
     */
    @Override
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
    
    /**
     * 
     * @param object
     */
    public final void setObject(ExtendedObject object) {
        DataItem item;
        String name;
        
        for (Element element : getElements()) {
            item = (DataItem)element;
            name = item.getName();
            
            switch (Shell.getDataElement(item).getType()) {
            case DataType.NUMC:
                if (item.isBooleanComponent())
                    item.setSelected(
                            ((Long)object.getValue(name) == 0)? false : true);
                else
                    item.setValue(Long.toString((Long)object.getValue(name)));
                break;
                
            default:
                item.setValue((String)object.getValue(name));
                break;
            }
        }
    }
}
