/*  
    Table.java, implementação de tabela html.
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

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;

/**
 * Implementação de tabela html.
 * 
 * @author Francisco Prates
 *
 */
public class Table extends AbstractContainer {
    private static final long serialVersionUID = -245959547368570624L;
    private boolean header, mark;
    private Map<String, TableColumn> columns;
    private List<String> itens;
    
    public Table(Container container, String name) {
        super(container, Const.TABLE, name);
        TableColumn column = new TableColumn();
        
        header = true;
        mark = false;
        itens = new ArrayList<String>();
        columns = new LinkedHashMap<String, TableColumn>();
        
        column.setName("");
        column.setMark(true);
        column.setVisible(true);
        
        columns.put(name, column);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractContainer#add(
     *     org.iocaste.shell.common.Element)
     */
    @Override
    public void add(Element element) {
        super.add(element);
        
        if (element.getType() == Const.TABLE_ITEM)
            itens.add(element.getName());
    }
    
    public void addColumn(String name) {
        TableColumn column;
        
        if (columns.containsKey(name))
            throw new RuntimeException("Table column has already exists.");
        
        column = new TableColumn();
        column.setName(name);
        column.setMark(false);
        column.setVisible(true);
        
        columns.put(name, column);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final TableColumn getColumn(String name) {
        return columns.get(name);
    }
    
    /**
     * Retorna títulos das colunas.
     * @return títulos
     */
    public final TableColumn[] getColumns() {
        return columns.values().toArray(new TableColumn[0]);
    }
    
    /**
     * 
     * @return
     */
    public final int getLength() {
        return itens.size();
    }
    
    /**
     * 
     * @return
     */
    public final TableItem[] getSelected() {
        TableItem item;
        List<TableItem> itens = new ArrayList<TableItem>();
        
        for (Element element : getElements()) {
            if (element.getType() != Const.TABLE_ITEM)
                continue;
            
            item = (TableItem)element;
            if (item.isSelected())
                itens.add(item);
        }
        
        return itens.toArray(new TableItem[0]);
    }
    
    /**
     * 
     * @param index
     * @return
     */
    public final TableItem getTableItem(int index) {
        return (TableItem)getElement(itens.get(index));
    }
    
    /**
     * 
     * @param item
     * @param name
     * @return
     */
    public String getValue(TableItem item, String name) {
        Element element = getElement(item.getComplexName(name));
        
        if (!element.isDataStorable())
            return null;
        
        return ((InputComponent)element).getValue();
    }
    
    /**
     * Retorna quantidade de colunas.
     * @return quantidade
     */
    public final int getWidth() {
        return columns.size();
    }
    
    /**
     * Retorna status de exibição da header.
     * @return true, exibe header.
     */
    public final boolean hasHeader() {
        return header;
    }
    
    /**
     * 
     * @return
     */
    public final boolean hasMark() {
        return mark;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractContainer#importModel(
     *     org.iocaste.documents.common.DocumentModel)
     */
    @Override
    public final void importModel(DocumentModel model) {
        TableColumn column;
        String name;
        
        for (DocumentModelItem item : model.getItens()) {
            name = item.getName();
            
            column = new TableColumn();
            column.setName(name);
            column.setMark(false);
            column.setVisible(true);
            column.setModelItem(item);
            
            columns.put(name, column);
        }
        
        super.importModel(model);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractContainer#remove(
     *     org.iocaste.shell.common.Element)
     */
    @Override
    public final void remove(Element element) {
        TableItem tableitem;
        
        if (element.getType() != Const.TABLE_ITEM)
            return;
        
        tableitem = (TableItem)element;
        for (String name : tableitem.getElementNames())
            super.remove(getElement(name));
        
        itens.remove(element.getName());
        
        super.remove(element);
    }
    
    /**
     * Ajusta status de exibição da header.
     * @param header true, exibe header
     */
    public final void setHeader(boolean header) {
        this.header = header;
    }
    
    /**
     * 
     * @param mark
     */
    public final void setMark(boolean mark) {
        this.mark = mark;
    }
}
