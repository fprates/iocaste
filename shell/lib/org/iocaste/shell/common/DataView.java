/*  
    DataView.java, implementação de visão de manutenção de tabelas.
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
 * Implementação de visão de manutenção de tabelas.
 * 
 * @author Francisco Prates
 *
 */
public class DataView extends AbstractContainer {
    private static final long serialVersionUID = -3746898860195865249L;
    private Object[] itens;
    private int pagelines;
    private Const mode;
    
    public DataView(Container container, String name) {
        super(container, Const.DATA_VIEW, name);
        pagelines = 0;
    }
    
    /**
     * Retorna entradas à serem exibidas.
     * @return conjunto de entradas
     */
    public final Object[] getItens() {
        return itens;
    }
    
    /**
     * Retorna modo de visão (simples ou detalhe).
     * @return modo de visão.
     */
    public final Const getMode() {
        return mode;
    }
    
    /**
     * Retorna quantidade de linhas por página.
     * @return linhas por página
     */
    public final int getPageLines() {
        return pagelines;
    }
    
    /**
     * Define entradas à serem exibidos.
     * @param itens conjunto de entradas
     */
    public final void setItens(Object[] itens) {
        this.itens = itens;
    }
    
    /**
     * Ajusta modo de visão (simples ou detalhe).
     * @param mode modo de visão
     */
    public final void setMode(Const mode) {
        this.mode = mode;
    }
    
    /**
     * Ajusta quantidade de linhas por página.
     * @param pagelines número de linhas
     */
    public final void setPageLines(int pagelines) {
        this.pagelines = pagelines;
    }
}
