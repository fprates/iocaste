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
    private boolean keyrequired;
    private byte columns;
    private List<String[]> lines;
    
    /**
     * 
     * @param view
     * @param name
     */
    public DataForm(View view, String name) {
        super(view, Const.DATA_FORM, name);
        init();
    }
    
    /**
     * 
     * @param container
     * @param name
     */
    public DataForm(Container container, String name) {
        super(container, Const.DATA_FORM, name);
        init();
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
     * @param columns
     */
    public final void addLine(String... entries) {
        if (entries.length != columns)
            throw new RuntimeException("Invalid number of columns.");
        
        lines.add(entries);
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
            input.set(null);
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
    public final byte getColumns() {
        return columns;
    }
    
    /**
     * 
     * @return
     */
    public final List<String[]> getLines() {
        return lines;
    }
    
    /**
     * 
     * @return
     */
    public final ExtendedObject getObject() {
        String name;
    	InputComponent input;
    	DocumentModel model = getModel();
    	ExtendedObject object = new ExtendedObject(model);
    	
        for (Element element: getElements()) {
        	if (!element.isDataStorable())
                continue;
        	
        	input = (InputComponent)element;
        	name = input.getName();
            if (model.getModelItem(name) == null)
                continue;
            
        	object.setValue(name, input.get());
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
     */
    private final void init() {
        keyrequired = false;
        setStyleClass("form");
        columns = 0;
        lines = new ArrayList<String[]>();
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
     * @param columns
     */
    public final void setColumns(byte columns) {
        this.columns = columns;
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
            if (!element.isDataStorable())
                continue;
            
            item = (DataItem)element;
            name = item.getName();
            
            item.set(object.getValue(name));
        }
    }
}
