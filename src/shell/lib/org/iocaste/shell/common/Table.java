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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

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
    public static final byte HEAD = 0;
    public static final byte LINE = 1;
    public static final byte HEADER_CELL = 2;
    public static final byte TABLE_CELL = 3;
    public static final byte TABLE_LINE = 4;
    public static final byte BORDER = 5;
    
    private boolean header, mark;
    private Map<String, TableColumn> columns;
    private Map<String, TableContextItem> context;
    private Map<Byte, String> styles;
    private byte seltype;
    private RadioGroup group;
    private String text;
    
    public Table(View view, String name) {
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
    public final void add(Element element) {
        if (element.getType() == Const.TABLE_ITEM)
            super.add(element);
        else
            element.setView(getView());
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
    
    public final TableContextItem addContextItem(String action) {
        TableContextItem ctxitem = new TableContextItem();
        context.put(action, ctxitem);
        return ctxitem;
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
    
    public final Map<String, TableContextItem> getContextItems() {
        return context;
    }
    
    /**
     * Retorna grupo de seleção, para seleção simples.
     * @return grupo de seleção.
     */
    public final RadioGroup getGroup() {
        return group;
    }
    
    /**
     * 
     * @return
     */
    public Set<TableItem> getItems() {
        return getElements();
    }
    
    /**
     * Retorna tipo de seleção de linha: SINGLE, MULTIPLE
     * @return tipo de seleção.
     */
    public final byte getSelectionType() {
        return seltype;
    }
    
    public final String getStyleClass(byte classid) {
        return styles.get(classid);
    }
    
    /**
     * Retorna título da tabela
     * @return título
     */
    public final String getText() {
        return text;
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
    
    /**
     * 
     */
    private final void init() {
        TableColumn column;
        
        header = true;
        columns = new LinkedHashMap<>();
        context = new LinkedHashMap<>();
        styles = new HashMap<>();
        styles.put(HEAD, "table_head");
        styles.put(LINE, "table_line");
        styles.put(HEADER_CELL, "table_header");
        styles.put(TABLE_CELL, "table_cell");
        styles.put(TABLE_LINE, "table_line");
        styles.put(BORDER, null);
        seltype = MULTIPLE;
        group = new RadioGroup(this, getName().concat(".mark"));
        
        column = new TableColumn(this, "mark");
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
     * Ajusta status de exibição da header.
     * @param header true, exibe header
     */
    public final void setHeader(boolean header) {
        this.header = header;
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
    
    public final void setStyleClass(byte classid, String style) {
        styles.put(classid, style);
    }
    
    /**
     * Ajusta título da tabela.
     * @param text título
     */
    public final void setText(String text) {
        this.text = text;
    }
    
    @Override
    public final void translate(MessageSource messages) {
        TableContextItem ctxitem;
        TableColumn column;
        String text;
        
        for (String name : columns.keySet()) {
            column = columns.get(name);
            text = column.getText();
            if (text == null) {
                column.setText(messages.get(name));
            } else {
                text = messages.get(text);
                if (text != null)
                    column.setText(text);
            }
        }
        
        for (String name : context.keySet()) {
            ctxitem = context.get(name);
            if (ctxitem.text == null)
                ctxitem.text = messages.get(ctxitem.htmlname);
        }
    }
    
    /**
     * Retorna quantidade de colunas.
     * @return quantidade
     */
    public final int width() {
        return columns.size();
    }
}
