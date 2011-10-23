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
import java.util.List;

import org.iocaste.documents.common.DocumentModel;

/**
 * Implementação de tabela html.
 * 
 * @author Francisco Prates
 *
 */
public class Table extends AbstractContainer {
    private static final long serialVersionUID = -245959547368570624L;
    private int cols;
    private boolean header;
    private boolean mark;
    private TableColumn[] columns;
    
    public Table(Container container, int cols, String name) {
        super(container, Const.TABLE, name);

        header = true;
        mark = false;
        
        if (cols == 0)
            return;
        
        prepare(cols, null);
    }
    
    /**
     * Retorna títulos das colunas.
     * @return títulos
     */
    public final TableColumn[] getColumns() {
        return columns;
    }
    
    /**
     * Retorna quantidade de colunas.
     * @return quantidade
     */
    public final int getWidth() {
        return cols;
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
        super.importModel(model);
        List<String> names = new ArrayList<String>();
        Element[] elements = getElements();
        
        for (Element element : elements) {
            if (element.getType() != Const.DATA_ITEM)
                continue;
            cols++;
            names.add(element.getName());
        }
        
        prepare(cols, names.toArray(new String[0]));
    }
    
    /**
     * 
     * @param cols
     */
    private final void prepare(int cols, String[] names) {
        TableColumn column;
        
        this.cols = cols + 1;
        columns = new TableColumn[this.cols];
        
        for (int k = 0; k < this.cols; k++) {
            column = new TableColumn();
            column.setVisible(true);
            column.setMark((k == 0)?true:false);
            
            columns[k] = column;
            if (k == 0 || names == null)
                continue;
            
            columns[k].setName(names[k-1]);
        }
    }
    
    /**
     * Ajusta status de exibição da header.
     * @param header true, exibe header
     */
    public final void setHeader(boolean header) {
        this.header = header;
    }
    
    /**
     * Ajusta título da coluna especificada.
     * @param col coluna
     * @param name título
     */
    public final void setHeaderName(int col, String name) {
        columns[col].setName(name);
    }
    
    /**
     * 
     * @param mark
     */
    public final void setMark(boolean mark) {
        this.mark = mark;
    }
    
    /**
     * Ajusta visibilidade da coluna.
     * @param col coluna
     * @param visible true, para coluna visível
     */
    public final void setVisibleColumn(int col, boolean visible) {
        columns[col].setVisible(visible);
    }
}
