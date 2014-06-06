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
 * <p>Data element implementation.</p>
 * 
 * <p>It stores the technical specs of a document model item.</p>
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
    
    /**
     * Get the attribute type. Attribute type is the java primitive
     * correspondent of some Iocaste data types.
     * @return attribute type.
     */
    public final int getAttributeType() {
        return atype;
    }
    
    /**
     * Get the class representation of a data type.
     * @return Java Class<?>
     */
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
     * Get the number of decimals positions.
     * @return decimal positions number
     */
    public final int getDecimals() {
        return decimals;
    }
    
    /**
     * Get the data length.
     * @return length
     */
    public final int getLength() {
        return length;
    }
    
    /**
     * Get the element name.
     * @return name
     */
    public final String getName() {
        return name;
    }
    
    /**
     * Get the element type.
     * @return type
     */
    public final int getType() {
        return type;
    }
    
    /**
     * Dummy elements are references for real data elements.
     * It holds no technical specs. It just points to the
     * real element.
     * @return true, if this is a dummy element.
     */
    public final boolean isDummy() {
        return dummy;
    }
    
    /**
     * @return true, if the value might be converted upcase.
     */
    public final boolean isUpcase() {
    	return upcase;
    }
    
    /**
     * <p>Set the attribute type.</p>
     * <p>Attribute type is the java primitive
     * correspondent of some Iocaste data types.</p>
     * @param type attribute type
     */
    public final void setAttributeType(int type) {
        atype = type;
    }
    
    /**
     * Define the number of decimals positions.
     * @param decimals decimals positions.
     */
    public final void setDecimals(int decimals) {
        this.decimals = decimals;
    }
    
    /**
     * Dummy elements are references for real data elements.
     * It holds no technical specs. It just points to the
     * real element.
     * @param dummy true, if this is a dummy element
     */
    public final void setDummy(boolean dummy) {
        this.dummy = dummy;
    }
    
    /**
     * Set the data length.
     * @param length length
     */
    public final void setLength(int length) {
        this.length = length;
    }
    
    /**
     * Set the data type.
     * @param type type
     */
    public final void setType(int type) {
        this.type = type;
    }
    
    /**
     * @param upcase true, if the value might be converted upcase.
     */
    public final void setUpcase(boolean upcase) {
    	this.upcase = upcase;
    }
}
