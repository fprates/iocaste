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

/**
 * Implementação de tabela html.
 * 
 * @author Francisco Prates
 *
 */
public class Table extends AbstractContainer {
    private static final long serialVersionUID = -245959547368570624L;
    private String[] hdrnames;
    private int cols;
    private boolean header;
    
    public Table(Container container, int cols, String name) {
        super(container, Const.TABLE, name);
        this.cols = cols;
        hdrnames = new String[cols];
        header = true;
    }
    
    /**
     * Retorna títulos das colunas.
     * @return títulos
     */
    public final String[] getHeaderNames() {
        return hdrnames;
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
        hdrnames[col] = name;
    }
}
