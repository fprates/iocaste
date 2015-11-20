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

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;

/**
 * Implementação de formulário de dados.
 * 
 * @author Francisco de Assis Prates
 *
 */
public class DataForm extends AbstractContainer {
    private static final long serialVersionUID = -5059126959559630847L;
    private boolean keyrequired;
    private int columns;
    private List<String[]> lines;
    private DocumentModel model;
    private String nsreference;
    
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
     * Adiciona campo à visão.
     * @param item
     */
    public final void add(DataItem item) {
        super.add(item);
    }
    
    /**
     * Configura disposição das colunas em configuração de múltiplas colunas.
     * @param columns
     */
    public final void addLine(String... entries) {
        if (entries.length != columns)
            throw new RuntimeException("Invalid number of columns.");
        
        lines.add(entries);
    }
    
    private final void append(DocumentModelItem item) {
        String name;
        DataElement dataelement;
        DataItem dataitem;
        int length;
        
        dataelement = item.getDataElement();
        name = item.getName();
        
        switch (dataelement.getType()) {
        case DataType.BOOLEAN:
            dataitem = new DataItem(this, Const.CHECKBOX, name);
            break;
        default:
            dataitem = new DataItem(this, Const.TEXT_FIELD, name);
            break;
        }
        dataitem.setModelItem(item);
        length = dataelement.getLength();
        dataitem.setLength(length);
        dataitem.setVisibleLength(length);
        dataitem.setDataElement(dataelement);
        dataitem.setNSReference(nsreference);
    }
    
    /**
     * Limpa valores do formulário.
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
     * Retorna item do formulário.
     * @param name nome do item
     * @return item do formulário
     */
    public final DataItem get(String name) {
        return getElement(name);
    }
    
    /**
     * Retorna quantidade de colunas.
     * @return quantidade
     */
    public final int getColumns() {
        return columns;
    }
    
    /**
     * Obtem linhas para configuração de múltiplas colunas.
     * @return
     */
    public final List<String[]> getLines() {
        return lines;
    }
    
    /**
     * 
     * @return
     */
    public final DocumentModel getModel() {
        return model;
    }
    
    /**
     * Retorna objeto extendido equivalente.
     * @return objeto extendido
     */
    public final ExtendedObject getObject() {
        String name;
    	InputComponent input;
    	ExtendedObject object = new ExtendedObject(model);
    	
    	if (nsreference != null) {
    	    input = getView().getElement(nsreference);
            object.setNS(input.get());
    	}
        for (Element element: getElements()) {
        	if (!element.isDataStorable())
                continue;
        	
        	input = (InputComponent)element;
        	name = input.getName();
            if (model.getModelItem(name) == null)
                continue;
            
        	object.set(name, input.get());
        }
    	
    	return object;
    }
    
    public final void importModel(String name, Function function) {
        DocumentModel model = new Documents(function).getModel(name);
        importModel(model);
    }
    
    public final void importModel(DocumentModel model) {
        DocumentModelItem namespace;
        InputComponent nsfield;
        
        clear();
        namespace = model.getNamespace();
        if (namespace != null) {
            append(namespace);
            nsfield = get(namespace.getName());
            nsreference = nsfield.getHtmlName();
            nsfield.setObligatory(true);
        }
        
        for (DocumentModelItem item : model.getItens())
            append(item);
        
        this.model = model;
    }

    /**
     * 
     */
    private final void init() {
        keyrequired = false;
        setStyleClass("form");
        columns = 0;
        lines = new ArrayList<>();
    }
    
    /**
     * Indica se a chave é requerida.
     * @return true, se a chave é requerida.
     */
    public final boolean isKeyRequired() {
        return keyrequired;
    }
    
    /**
     * Ajusta quantidade de colunas.
     * @param columns quantidade
     */
    public final void setColumns(int columns) {
        this.columns = columns;
    }
    
    /**
     * Define se chave é requerida.
     * @param keyrequired true, para chave obrigatória.
     */
    public final void setKeyRequired(boolean keyrequired) {
        this.keyrequired = keyrequired;
    }
    
    /**
     * Lê a partir de objeto extendido.
     * @param object object extendido.
     */
    public final void setObject(ExtendedObject object) {
        DataItem item;
        String name;
        
        for (Element element : getElements()) {
            if (!element.isDataStorable())
                continue;
            
            item = (DataItem)element;
            name = item.getName();
            item.set(object.getNS(), object.get(name));
        }
    }
    
    /**
     * 
     * @param nsreference
     */
    public final void setNSReference(String nsreference) {
        DataItem item;
        
        this.nsreference = nsreference;
        
        for (Element element : getElements()) {
            if (!element.isDataStorable())
                continue;
            item = (DataItem)element;
            item.setNSReference(nsreference);
        }
    }
    
    /**
     * 
     * @param fields
     */
    public final void show(String... fields) {
        for (Element element : getElements())
            element.setVisible(false);
        
        for (String field : fields)
            get(field).setVisible(true);
    }
}
