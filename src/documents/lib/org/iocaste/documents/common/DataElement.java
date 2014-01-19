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
    private int decimals, length, type, atype;
    private boolean upcase, dummy;
    
    public DataElement(String name) {
        this.name = name;
        dummy = false;
        atype = -1;
    }
    
    public final int getAttributeType() {
        return atype;
    }
    
    public final Class<?> getClassType() {  
        switch (type) {
        case DataType.BOOLEAN:
            return boolean.class;
        case DataType.CHAR:
            return String.class;
        case DataType.DATE:
            return Date.class;
        case DataType.DEC:
            return double.class;
        case DataType.NUMC:
            switch (atype) {
            case DataType.BYTE:
                return byte.class;
            case DataType.INT:
                return int.class;
            case DataType.LONG:
                return long.class;
            case DataType.SHORT:
                return short.class;
            default:
                return int.class;
            }
        case DataType.TIME:
            return Time.class;
        }
        
        return null;
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
     * 
     * @return
     */
    public final boolean isDummy() {
        return dummy;
    }
    
    /**
     * Indica se um campo deve ter seu valor convertido
     * para maiúscula.
     * @return true, se valor deve ser convertido para maiúscula.
     */
    public final boolean isUpcase() {
    	return upcase;
    }
    
    public final void setAttributeType(int type) {
        atype = type;
    }
    
    /**
     * Define quantidade de casas decimais.
     * @param decimals quantidade
     */
    public final void setDecimals(int decimals) {
        this.decimals = decimals;
    }
    
    /**
     * 
     * @param dummy
     */
    public final void setDummy(boolean dummy) {
        this.dummy = dummy;
    }
    
    /**
     * Define comprimento do elemento.
     * @param length comprimento
     */
    public final void setLength(int length) {
        this.length = length;
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
