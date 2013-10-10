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
    public static final byte ADD = 0;
    public static final byte REMOVE = 1;
    private boolean header, mark;
    private Map<String, TableColumn> columns;
    private List<TableItem> itens;
    private byte seltype;
    private RadioGroup group;
    private Container linecontrol;
    private String[] actions;
    private String text;
    private int topline, vlines;
    private Container container;
    
    public Table(View view, String name) {
        super(view, Const.TABLE, name);
        
        init();
    }
    
    public Table(Container container, String name) {
        super(container, Const.TABLE, name);
        
        this.container = container;
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
     * Adiciona linha na tabela.
     * @param item linha
     */
    public final void add(TableItem item) {
        itens.add(item);
        item.setLocale(getLocale());
    }
    
    /**
     * 
     * @param item
     * @param pos
     */
    public final void add(TableItem item, int pos) {
        itens.add(pos, item);
        item.setLocale(getLocale());
    }
    
    /**
     * Adiciona coluna à tabela.
     * @param column coluna
     */
    public void add(TableColumn column) {
        if (columns.containsKey(column.getName()))
            throw new RuntimeException("Table column has already exists.");
        
        columns.put(column.getName(), column);
    }
    
    /**
     * Obtem linha da tabela através de índice.
     * @param index índice
     * @return linha
     */
    public final TableItem get(int index) {
        return itens.get(index);
    }
    
    /**
     * - not ready, do not use - 
     * @param action
     * @return
     */
    public final String getAction(byte action) {
        return actions[action];
    }
    
    /**
     * Retorna dados da coluna especificado.
     * @param name nome da coluna
     * @return dados da coluna
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
    
    public final Container getContainer() {
        return container;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractContainer#getElements()
     */
    @Override
    public final Element[] getElements() {
        InputComponent input;
        SearchHelp sh;
        List<Element> elements = new ArrayList<>();
        
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
     * Retorna grupo de seleção, para seleção simples.
     * @return grupo de seleção.
     */
    public final RadioGroup getGroup() {
        return group;
    }
    
    /**
     * Retorna linhas da tabela.
     * @return linhas.
     */
    public final TableItem[] getItems() {
        return itens.toArray(new TableItem[0]);
    }
    
    /**
     * - not ready, do not use -
     * @return
     */
    public final Container getLineControl() {
        return linecontrol;
    }
    
    /**
     * Retorna tipo de seleção de linha: SINGLE, MULTIPLE
     * @return tipo de seleção.
     */
    public final byte getSelectionType() {
        return seltype;
    }
    
    /**
     * Retorna item especificado.
     * @param index índice
     * @return linha
     */
    public final TableItem getTableItem(int index) {
        return itens.get(index);
    }
    
    /**
     * 
     * @return
     */
    public final int getTopLine() {
        return topline;
    }
    
    /**
     * Retorna título da tabela
     * @return título
     */
    public final String getText() {
        return text;
    }
    
    /**
     * Retorna quantidade de linhas visíveis
     * @return
     */
    public final int getVisibleLines() {
        return vlines;
    }
    
    /**
     * Retorna status de exibição da header.
     * @return true, exibe header.
     */
    public final boolean hasHeader() {
        return header;
    }
    
    /**
     * Indica se exibe coluna de seleção.
     * @return true, se deve exibir coluna de seleção.
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
            column.setLength(item.getDataElement().getLength());
            
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
        itens = new ArrayList<>();
        columns = new LinkedHashMap<>();
        seltype = MULTIPLE;
        group = new RadioGroup(getName()+".mark");
        actions = new String[2];
        topline = 0;
        vlines = 0;
        
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
     * Retorna quantidade de linha da tabela.
     * @return quantidade de linhas.
     */
    public final int length() {
        return itens.size();
    }
    
    /**
     * Remove linha da tabela.
     * @param item linha
     */
    public final void remove(TableItem item) {
        itens.remove(item);
    }
    
    /**
     * - not ready, do not use - 
     * @param action
     * @param method
     */
    public final void setAction(byte action, String method) {
        actions[action] = method;
    }
    
    /**
     * Ajusta status de exibição da header.
     * @param header true, exibe header
     */
    public final void setHeader(boolean header) {
        this.header = header;
    }
    
    /**
     * - not ready, do not use -
     * @param linecontrol
     */
    public final void setLineControl(Container linecontrol) {
        this.linecontrol = linecontrol;
    }
    
    /**
     * Define que coluna de seleção de linhas deve ser exibida.
     * @param mark true, para exibir coluna de seleçao.
     */
    public final void setMark(boolean mark) {
        this.mark = mark;
    }
    
    /**
     * Ajusta o tipo de seleção de linhas.
     * @param seltype tipo de seleção.
     */
    public final void setSelectionType(byte seltype) {
        this.seltype = seltype;
    }
    
    /**
     * Ajusta título da tabela.
     * @param text título
     */
    public final void setText(String text) {
        this.text = text;
    }
    
    /**
     * 
     * @param topline
     */
    public final void setTopLine(int topline) {
        this.topline = topline;
    }
    
    /**
     * Define quantidade de linhas visíveis
     * @param vlines
     */
    public final void setVisibleLines(int vlines) {
        this.vlines = vlines;
    }
    
    /**
     * Retorna quantidade de colunas.
     * @return quantidade
     */
    public final int width() {
        return columns.size();
    }
}
