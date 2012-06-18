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
import java.sql.Time;
import java.util.Date;

/**
 * Implementação de elemento do dicionário de dados
 * 
 * Este elemento detém as características de um item.
 * 
 * @author Francisco Prates
 *
 */
public class DataElement implements Serializable {
    private static final long serialVersionUID = -2827176147542188319L;
    private String name;
    private int decimals;
    private int length;
    private int type;
    private boolean upcase;
    
    public Class<?> getClassType() {
        switch (type) {
        case DataType.BOOLEAN:
            return Boolean.class;
        case DataType.CHAR:
            return String.class;
        case DataType.DATE:
            return Date.class;
        case DataType.DEC:
            return Double.class;
        case DataType.NUMC:
            return Integer.class;
        case DataType.TIME:
            return Time.class;
        default:
            return null;
        }
    }
    
    /**
     * Retorna quantidade de casas decimais.
     * @return quantidade
     */
    public final int getDecimals() {
        return decimals;
    }
    
    /**
     * Retorna comprimento máximo do dado.
     * @return comprimento máximo
     */
    public final int getLength() {
        return length;
    }
    
    /**
     * Retorna nome do elemento.
     * @return nome
     */
    public final String getName() {
        return name;
    }
    
    /**
     * Retorna tipo do elemento.
     * @return tipo
     */
    public final int getType() {
        return type;
    }
    
    /**
     * Indica se um campo deve ter seu valor convertido
     * para maiúscula.
     * @return true, se valor deve ser convertido para maiúscula.
     */
    public final boolean isUpcase() {
    	return upcase;
    }
    
    /**
     * Define quantidade de casas decimais.
     * @param decimals quantidade
     */
    public final void setDecimals(int decimals) {
        this.decimals = decimals;
    }
    
    /**
     * Define comprimento do elemento.
     * @param length comprimento
     */
    public final void setLength(int length) {
        this.length = length;
    }
    
    /**
     * Define nome do elemento.
     * @param name
     */
    public final void setName(String name) {
        this.name = name;
    }
    
    /**
     * Define tipo do elemento.
     * @param type tipo
     */
    public final void setType(int type) {
        this.type = type;
    }
    
    /**
     * Define que um campo deve ter seu valor convertido
     * para maiúscula.
     * 
     * deve ser efetivo apenas para tipo caracter.
     * 
     * @param upcase true, para converte para maiúscula.
     */
    public final void setUpcase(boolean upcase) {
    	this.upcase = upcase;
    }
}
