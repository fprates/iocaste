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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.tooldata.Context;
import org.iocaste.shell.common.tooldata.ElementViewContext;
import org.iocaste.shell.common.tooldata.ToolData;
import org.iocaste.shell.common.tooldata.ToolDataElement;
import org.iocaste.shell.common.tooldata.ViewSpecItem.TYPES;

/**
 * Implementação de formulário de dados.
 * 
 * @author Francisco de Assis Prates
 *
 */
public class DataForm extends ToolDataElement {
    private static final long serialVersionUID = -5059126959559630847L;
    private boolean keyrequired;
    private Map<String, String[]> groups;
    private List<String[]> lines;
    private Context viewctx;
    
    /**
     * 
     * @param view
     * @param name
     */
    public DataForm(View view, String name) {
        this(new ElementViewContext(
                view, null, TYPES.DATA_FORM, name), name);
    }
    
    /**
     * 
     * @param container
     * @param name
     */
    public DataForm(Container container, String name) {
        this(new ElementViewContext(
                null, container, TYPES.DATA_FORM, name), name);
    }
    
    public DataForm(Context viewctx, String name) {
        super(viewctx, Const.DATA_FORM, name);
        keyrequired = false;
        lines = new ArrayList<>();
        groups = new LinkedHashMap<>();
        this.viewctx = viewctx;
    }
    
    public final void addGroup(String group, String... fields) {
        groups.put(group, fields);
    }
    
    private final void append(DocumentModelItem item) {
        String name;
        ToolData itemtooldata;
        Context itemviewctx;
        DataElement dataelement;
        DataItem dataitem;
        int length, vlength;
        
        dataelement = item.getDataElement();
        name = item.getName();
        
        switch (dataelement.getType()) {
        case DataType.BOOLEAN:
            dataitem = new DataItem(this, Const.CHECKBOX, name);
            break;
        default:
            itemtooldata = viewctx.get(getName()).instance(name);
            itemviewctx = new ElementViewContext(
                    itemtooldata, this, TYPES.DUMMY);
            dataitem = new DataItem(itemviewctx,
                (itemtooldata.componenttype == null)?
                    Const.TEXT_FIELD : itemtooldata.componenttype, name);
            break;
        }
        
        dataitem.setModelItem(item);
        length = dataelement.getLength();
        vlength = dataitem.getVisibleLength();
        dataitem.setLength((length == 0)? length = 20 : length);
        dataitem.setVisibleLength((vlength == 0)? length : vlength);
        dataitem.setDataElement(dataelement);
        dataitem.setNSReference(getNSReference());
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
    
    public final Map<String, String[]> getGroups() {
        return groups;
    }
    
    /**
     * Obtem linhas para configuração de múltiplas colunas.
     * @return
     */
    public final List<String[]> getLines() {
        return lines;
    }
    
    public final DocumentModel importModel(String name, Function function) {
        DocumentModel model = new Documents(function).getModel(name);
        return importModel(model);
    }
    
    public final DocumentModel importModel(DocumentModel model) {
        DocumentModelItem namespace;
        InputComponent nsfield;
        
        clear();
        namespace = model.getNamespace();
        if (namespace != null) {
            append(namespace);
            nsfield = get(namespace.getName());
            setNSReference(nsfield.getHtmlName());
            nsfield.setObligatory(true);
        }
        
        for (DocumentModelItem item : model.getItens())
            append(item);
        
        return model;
    }
    
    @Override
    public final boolean isContainable() {
        return true;
    }
    
    /**
     * Indica se a chave é requerida.
     * @return true, se a chave é requerida.
     */
    public final boolean isKeyRequired() {
        return keyrequired;
    }
    
    public final boolean isNSReference(String name) {
        String test;
        if (tooldata.nsdata == null)
            return false;
        test = getView().getElement(tooldata.nsdata).getName();
        return name.equals(test);
    }
    
    /**
     * Define se chave é requerida.
     * @param keyrequired true, para chave obrigatória.
     */
    public final void setKeyRequired(boolean keyrequired) {
        this.keyrequired = keyrequired;
    }
}
