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
    public static final byte MULTIPLE = 0;
    public static final byte SINGLE = 1;
    private boolean header, mark;
    private Map<String, TableColumn> columns;
    private List<TableItem> itens;
    private byte seltype;
    private RadioGroup group;
    
    public Table(ViewData view, String name) {
        super(view, Const.TABLE, name);
        
        init();
    }
    
    public Table(Container container, String name) {
        super(container, Const.TABLE, name);
        
        init();
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractContainer#add(
     *     org.iocaste.shell.common.Element)
     */
    @Override
    public final void add(Element element) { };
    
    /**
     * 
     * @param item
     */
    public final void add(TableItem item) {
        itens.add(item);
        item.setLocale(getLocale());
    }
    
    /**
     * 
     * @param column
     */
    public void add(TableColumn column) {
        if (columns.containsKey(column.getName()))
            throw new RuntimeException("Table column has already exists.");
        
        columns.put(column.getName(), column);
    }
    
    /**
     * 
     * @param index
     * @return
     */
    public final TableItem get(int index) {
        return itens.get(index);
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
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractContainer#getElements()
     */
    @Override
    public final Element[] getElements() {
        InputComponent input;
        SearchHelp sh;
        List<Element> elements = new ArrayList<Element>();
        
        for (TableItem item : itens)
            for (Element element : item.getElements()) {
                elements.add(element);
                
                if (!element.isDataStorable())
                    continue;
                
                input = (InputComponent)element;
                sh = input.getSearchHelp();
                if (sh == null)
                    continue;
                
                elements.add(sh);
            }
        
        return elements.toArray(new Element[0]);
    }
    
    /**
     * 
     * @return
     */
    public final RadioGroup getGroup() {
        return group;
    }
    
    /**
     * 
     * @return
     */
    public final TableItem[] getItens() {
        return itens.toArray(new TableItem[0]);
    }
    
    /**
     * 
     * @return
     */
    public final byte getSelectionType() {
        return seltype;
    }
    
    /**
     * 
     * @param index
     * @return
     */
    public final TableItem getTableItem(int index) {
        return itens.get(index);
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
            
            column = new TableColumn(this, name);
            column.setMark(false);
            column.setVisible(true);
            column.setModelItem(item);
            
            columns.put(name, column);
        }
        
        super.importModel(model);
    }
    
    /**
     * 
     */
    private final void init() {
        TableColumn column;
        
        header = true;
        mark = false;
        itens = new ArrayList<TableItem>();
        columns = new LinkedHashMap<String, TableColumn>();
        seltype = MULTIPLE;
        group = new RadioGroup(getName()+".mark");
        
        column = new TableColumn(this, "");
        column.setMark(true);
        column.setVisible(true);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractContainer#isMultiLine()
     */
    @Override
    public final boolean isMultiLine() {
        return true;
    }
    
    /**
     * 
     * @return
     */
    public final int length() {
        return itens.size();
    }
    
    /**
     * 
     * @param item
     */
    public final void remove(TableItem item) {
        itens.remove(item);
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
    
    /**
     * 
     * @param seltype
     */
    public final void setSelectionType(byte seltype) {
        this.seltype = seltype;
    }
    
    /**
     * Retorna quantidade de colunas.
     * @return quantidade
     */
    public final int width() {
        return columns.size();
    }
}
