/*
    DataElement.java, implementação de elemento do dicionário de dados.
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

package org.iocaste.documents.common;

import java.io.Serializable;

/**
 * Implementação de elemento do dicionário de dados
 * @author Francisco Prates
 *
 */
public class DataElement implements Serializable {
    private static final long serialVersionUID = -2827176147542188319L;
    private String name;
    private int decimals;
    private int length;
    private DataType type;
    
    /**
     * Retorna quantidade de casas decimais.
     * @return quantidade
     */
    public int getDecimals() {
        return decimals;
    }
    
    /**
     * Retorna comprimento máximo do dado.
     * @return comprimento máximo
     */
    public int getLength() {
        return length;
    }
    
    /**
     * Retorna nome do elemento.
     * @return nome
     */
    public String getName() {
        return name;
    }
    
    /**
     * Retorna tipo do elemento.
     * @return tipo
     */
    public DataType getType() {
        return type;
    }
    
    /**
     * Define quantidade de casas decimais.
     * @param decimals quantidade
     */
    public void setDecimals(int decimals) {
        this.decimals = decimals;
    }
    
    /**
     * Define comprimento do elemento.
     * @param length comprimento
     */
    public void setLength(int length) {
        this.length = length;
    }
    
    /**
     * Define nome do elemento.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Define tipo do elemento.
     * @param type tipo
     */
    public void setType(DataType type) {
        this.type = type;
    }
}
