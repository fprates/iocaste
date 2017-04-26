package org.iocaste.protocol.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;

public class Tools {
	public enum TYPE {
		HASH
	};
    
    public static final Object convertValue(
            String value, DataElement dataelement, Locale locale) {
        NumberFormat numberformat;
        DateFormat dateformat;
        
        switch(dataelement.getType()) {
        case DataType.DEC:
            if (isInitial(value))
                return 0d;
            
            try {
                numberformat = NumberFormat.getNumberInstance(locale);
                return numberformat.parse(value).doubleValue();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        case DataType.DATE:
            if (isInitial(value))
                return null;
            
            try {
                dateformat = DateFormat.getDateInstance(DateFormat.MEDIUM,
                        locale);
                return dateformat.parse(value);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        case DataType.TIME:
            if (isInitial(value))
                return null;
            
            try {
                dateformat = DateFormat.getTimeInstance(DateFormat.MEDIUM,
                        locale);
                return dateformat.parse(value);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        case DataType.BYTE:
        case DataType.INT:
        case DataType.LONG:
        case DataType.SHORT:
        case DataType.NUMC:
            if (isInitial(value))
                return new BigDecimal(0);
            
            return new BigDecimal(value);
        case DataType.CHAR:
            if (isInitial(value))
                return null;
            
            if (dataelement.isUpcase())
                return value.toUpperCase();
            
            return value;
        default:
            return null;
        }
    }
    
    /**
     * Verifica se um campo do objeto extendido é inicial.
     * @param object Objeto extendido
     * @param name Nome do campo
     * @return true, se o valor do campo do objeto é inicial.
     */
    public static final boolean isInitial(ExtendedObject object, String name) {
        Object value = object.get(name);
        DataElement element = object.getModel().getModelItem(name).
                getDataElement();
        return isInitial(element, value);
    }
    
    /**
     * Verifica que a valor String é inicial
     * @param value String
     * @return true, se a String é nula ou tiver comprimento 0.
     */
    public static final boolean isInitial(String value) {
        if (value == null)
            return true;
        else
            return (value.trim().length() == 0)? true : false;
    }
    
    /**
     * Verifica se um valor do tipo do elemento de dados é inicial.
     * @param element Elemento de dados
     * @param value Valor
     * @return true, se o valor é considerado inicial.
     */
    public static final boolean isInitial(DataElement element, Object value) {
        if (element == null)
            return isInitial((String)value);
        
        if (value == null)
            return true;
        
        switch (element.getType()) {
        case DataType.BOOLEAN:
            return !(boolean)value;
            
        case DataType.NUMC:
            if (value instanceof BigDecimal)
                return (((BigDecimal)value).longValue() == 0l);
            else
                return (((Number)value).longValue() == 0l);
            
        case DataType.DEC:
            return (((Number)value).doubleValue() == 0);

        default:
            return (value == null)? true : isInitial(value.toString());
        }
    }
    
    /**
     * Verifica se objeto extendido é inicial
     * @param object objeto extendido
     * @return true, se todos os campos do objeto forem iniciais.
     */
    public static final boolean isInitial(ExtendedObject object) {
        return isInitialIgnoring(object, null);
    }
    
    public static final boolean isInitialIgnoring(
            ExtendedObject object, Set<String> ignore) {
        DocumentModel model;
        
        if (object == null)
            return true;
        
        model = object.getModel();
        for (DocumentModelItem item : model.getItens()) {
            if ((ignore != null) && ignore.contains(item.getName()))
                continue;
        
            if (!isInitial(item.getDataElement(), object.get(item)))
                return false;
        }
        
        return true;
    }
	
    public static final Object[][] toArray(Map<?,?> map) {
    	int i = 0;
    	Object[][] items = new Object[map.size()][2];
    	for (Object key : map.keySet()) {
    		items[i][0] = key;
    		items[i++][1] = map.get(key);
    	}
    	return items;
    }
    
    @SuppressWarnings("unchecked")
	public static final <K, L> Map<K, L> toMap(TYPE type, Object[][] values) {
    	Map<K, L> map = null;
    	switch (type) {
    	case HASH:
    		map = new HashMap<>();
    		break;
    	}
    	for (int i = 0; i < values.length; i++)
    		map.put((K)values[i][0], (L)values[i][1]);
    	return (Map<K,L>)map;
    }
    
    @SuppressWarnings("unchecked")
	public static final <K> Set<K> toSet(TYPE type, Object[] values) {
    	Set<K> set = null;
    	switch (type) {
    	case HASH:
    		set = new HashSet<>();
    		break;
    	}
    	for (int i = 0; i < values.length; i++)
    		set.add((K)values[i]);
    	return (Set<K>)set;
    }
    
    /**
     * 
     * @param value
     * @param element
     * @param locale
     * @param boolconvert
     * @return
     */
    public static final String toString(Object value, DataElement element,
            Locale locale, boolean boolconvert) {
        if (element == null)
            return (value == null)? "" : value.toString();
        
        return toString(value, element.getType(), element.getDecimals(), locale,
                boolconvert);
    }
    
    public static final String toString(Object value, int type, int dec,
            Locale locale, boolean boolconvert) {
        DateFormat dateformat;
        NumberFormat numberformat;
        
        switch (type) {
        case DataType.DEC:
            numberformat = NumberFormat.getNumberInstance(locale);
            numberformat.setMaximumFractionDigits(dec);
            numberformat.setMinimumFractionDigits(dec);
            numberformat.setGroupingUsed(true);
            return numberformat.format((value == null)? 0 : value);
            
        case DataType.NUMC:
            return (value == null)? "0" : value.toString();
            
        case DataType.DATE:
            if (value == null)
                return "";
            
            dateformat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
            return dateformat.format(value);
            
        case DataType.BOOLEAN:
            if (boolconvert)
                return ((Boolean)value)? "on" : "off";
            else
                return Boolean.toString((Boolean)value);
            
        case DataType.TIME:
            if (value == null)
                return "";
            
            dateformat = DateFormat.getTimeInstance(DateFormat.MEDIUM, locale);
            return dateformat.format(value);
            
        default:
            return (value == null)? "" : value.toString();
        }
    }
}
