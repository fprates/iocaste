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

/**
 * Implementação de formulário de dados.
 * 
 * @author Francisco de Assis Prates
 *
 */
public class DataForm extends AbstractContainer {
    private static final long serialVersionUID = -5059126959559630847L;
    private boolean keyrequired;
    private Map<String, String[]> groups;
    private List<String[]> lines;
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
    
    public final void addGroup(String group, String... fields) {
        groups.put(group, fields);
    }
    
    private static final void append(DataForm df, DocumentModelItem item) {
        String name;
        DataElement dataelement;
        DataItem dataitem;
        int length;
        
        dataelement = item.getDataElement();
        name = item.getName();
        
        switch (dataelement.getType()) {
        case DataType.BOOLEAN:
            dataitem = new DataItem(df, Const.CHECKBOX, name);
            break;
        default:
            dataitem = new DataItem(df, Const.TEXT_FIELD, name);
            break;
        }
        dataitem.setModelItem(item);
        length = dataelement.getLength();
        dataitem.setLength(length);
        dataitem.setVisibleLength(length);
        dataitem.setDataElement(dataelement);
        dataitem.setNSReference(df.getNSReference());
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

    public final String getNSReference() {
        return nsreference;
    }
    
    public static final DocumentModel importModel(DataForm df,
            String name, Function function) {
        DocumentModel model = new Documents(function).getModel(name);
        return importModel(df, model);
    }
    
    public static final DocumentModel importModel(DataForm df,
            DocumentModel model) {
        DocumentModelItem namespace;
        InputComponent nsfield;
        
        df.clear();
        namespace = model.getNamespace();
        if (namespace != null) {
            append(df, namespace);
            nsfield = df.get(namespace.getName());
            df.setNSReference(nsfield.getHtmlName());
            nsfield.setObligatory(true);
        }
        
        for (DocumentModelItem item : model.getItens())
            append(df, item);
        
        return model;
    }
    
    /**
     * 
     */
    private final void init() {
        keyrequired = false;
        setStyleClass("form");
        lines = new ArrayList<>();
        groups = new LinkedHashMap<>();
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
        if (nsreference == null)
            return false;
        test = getView().getElement(nsreference).getName();
        return name.equals(test);
    }
    
    /**
     * Define se chave é requerida.
     * @param keyrequired true, para chave obrigatória.
     */
    public final void setKeyRequired(boolean keyrequired) {
        this.keyrequired = keyrequired;
    }
    
    public final void setNS(Object value) {
        DataItem input = getView().getElement(nsreference);
        input.set(value);
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
}
