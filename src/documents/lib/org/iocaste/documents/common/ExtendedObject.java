package org.iocaste.documents.common;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Objeto extendido.
 * 
 * Classes do cliente não podem ser serializadas através dos módulos
 * do sistema. Com objeto extendido, podemos intercâmbiar o conteúdo
 * das classes.
 * 
 * @author francisco.prates
 *
 */
public class ExtendedObject implements Comparable<ExtendedObject>,
      Serializable {
    private static final long serialVersionUID = -8700097929412206566L;
    private Map<DocumentModelItem, Object> values;
    private Map<String, DocumentModelItem> byname;
    private DocumentModel model;
    
    public ExtendedObject(DocumentModel model) {
        if (model == null)
            throw new RuntimeException("model not defined.");
        
        values = new HashMap<>();
        byname = new HashMap<>();
        
        for (DocumentModelItem item : model.getItens())
            byname.put(item.getName(), item);
        
        this.model = model;
    }

    @Override
    public int compareTo(ExtendedObject object) {
        int diff;
        String name;
        Object value1, value2;
        
        for (DocumentModelKey key : model.getKeys()) {
            name = key.getModelItemName();
            value1 = get(name);
            value2 = object.get(name);
            if (value1 == null && value2 == null)
                continue;
            
            if (value1 == null)
                return -1;
            
            if (value2 == null)
                return 1;
            
            diff = value1.toString().compareTo(value2.toString());
            if (diff == 0)
                continue;
            
            return diff;
        }
        return 0;
    }
    
    /**
     * Retorna valor de um item do objeto.
     * @param name nome do item
     * @return valor
     */
    public final <T> T get(String name) {
        return get(byname.get(name));
    }
    
    /**
     * Retorna valor de um item do objeto.
     * @param item item do modelo
     * @return valor
     */
    @SuppressWarnings("unchecked")
    public final <T> T get(DocumentModelItem item) {
        return (T)values.get(item);
    }
    
    public final byte getb(String name) {
        Object value = getNumericValue(name);
        
        if (value instanceof BigDecimal)
            return ((BigDecimal)value).byteValue();
        
        try {
            return (byte)value;
        } catch (ClassCastException e) {
            return Byte.parseByte(value.toString());
        }
    }
    
    /**
     * Retorna valor do campo especificado no formato double.
     * @param name nome do campo
     * @return valor do campo do tipo double
     */
    public final double getd(String name) {
        Object value = getNumericValue(name);
        
        if (!(value instanceof BigDecimal))
            return (double)value;
        
        return ((BigDecimal)value).doubleValue();
    }

    public final Date getdt(String name) {
        return (Date)get(name);
    }
    
    /**
     * Retorna valor do campo especificado no formato int.
     * @param name nome do campo
     * @return valor do campo do tipo int
     */
    public final int geti(String name) {
        Object value = getNumericValue(name);
        
        if (value instanceof BigDecimal)
            return ((BigDecimal)value).intValue();
        
        try {
            return (int)value;
        } catch (ClassCastException e) {
            return Integer.parseInt(value.toString());
        }
    }

    /**
     * Retorna valor do campo especificado no formato long.
     * @param name nome do campo
     * @return valor do campo do tipo long
     */
    public final long getl(String name) {
        Object value = getNumericValue(name);
        
        if (value instanceof BigDecimal)
            return ((BigDecimal)value).longValue();
        
        try {
            return (long)value;
        } catch (ClassCastException e) {
            return Long.parseLong(value.toString());
        }
    }
    
    /**
     * Retorna o modelo de documento associado.
     * @return modelo
     */
    public final DocumentModel getModel() {
        return model;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    private final Object getNumericValue(String name) {
        Object value;
        
        if (!byname.containsKey(name))
            throw new RuntimeException(new StringBuilder(name).
                    append(" isn't a valid field name for ").
                    append(model.getName()).toString());
        
        value = get(name);
        return (value == null)? 0 : value;
    }
    
    public final short getsh(String name) {
        Object value = getNumericValue(name);
        
        if (value instanceof BigDecimal)
            return ((BigDecimal)value).shortValue();
        
        try {
            return (short)value;
        } catch (ClassCastException e) {
            return Short.parseShort(value.toString());
        }
    }
    
    public final String getst(String name) {
        return (String)get(name);
    }
    
    /**
     * 
     * @param loose
     * @return
     */
    @SuppressWarnings("unchecked")
    private final <T> T newInstance(boolean loose) {
        String settername;
        Object value;
        Method method;
        Class<?> class_;
        T instance;
        
        try {
            class_ = Class.forName(model.getClassName());
            instance = (T)class_.newInstance();
            
            for (DocumentModelItem item : values.keySet()) {
                class_ = item.getDataElement().getClassType();
                settername = item.getSetterName();
                if (settername == null)
                    continue;
                try {
                    method = instance.getClass().getMethod(settername, class_);
                } catch (NoSuchMethodException e) {
                    if (loose)
                        continue;
                    throw e;
                }
                
                switch (class_.getName()) {
                case "byte":
                    value = getb(item.getName());
                    break;
                case "int":
                    value = geti(item.getName());
                    break;
                case "long":
                    value = getl(item.getName());
                    break;
                case "short":
                    value = getsh(item.getName());
                    break;
                default:
                    value = values.get(item);
                    break;
                }
                
                method.invoke(instance, value);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        return instance;
    }
    
    /**
     * Gera instancia baseada nos dados do objeto e classe associada.
     * 
     * Gera exceção se encontrar item não existente na classe associada.
     * @return instância da classe associada.
     */
    public final <T> T newInstance() {
        return newInstance(false);
    }
    
    /**
     * Gera instancia baseada nos dados do objeto e classe associada.
     * @param loose true, ignora campos que não existirem na classe.
     * @return instância da classe associada.
     */
    public final <T> T newLooseInstance() {
        return newInstance(true);
    }
    
    public final void setInstance(Object object) {
        Class<?> class_;
        Method method;
        Object value;
        String classname, getter;
        String modelclassname = model.getClassName();
        
        if ((object == null) || (modelclassname == null))
            return;
        
        class_ = object.getClass();
        classname = class_.getName();
        if (!model.getClassName().equals(classname))
            throw new RuntimeException(new StringBuilder(classname).
                    append(" isn't compatible with model class ").
                    append(modelclassname).toString());
        
        for (DocumentModelItem item : model.getItens()) {
            getter = item.getGetterName();
            if (getter == null)
                continue;
            try {
                method = class_.getMethod(getter);
                value = method.invoke(object);
            } catch (NoSuchMethodException | SecurityException |
                    IllegalAccessException | IllegalArgumentException |
                    InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            
            set(item, value);
        }
    }
    
    /**
     * Define valor do item do objeto extendido.
     * @param name nome do objeto
     * @param value valor
     */
    public final void set(String name, Object value) {
        set(byname.get(name), value);
    }
    
    /**
     * Define valor do item do objeto extendido.
     * @param item item de modelo
     * @param value valor
     */
    public final void set(DocumentModelItem item, Object value) {
        if (item == null)
            throw new RuntimeException("Invalid null model item key.");
        
        if (!model.contains(item))
            return;
        
        if (values.containsKey(item))
            values.remove(item);
        
        values.put(item, value);
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString() {
        String name = null;
        StringBuilder sb = new StringBuilder("{");
        
        for (DocumentModelItem modelitem : values.keySet()) {
            if (name != null)
                sb.append(", ");
            
            name = modelitem.getName();
            sb.append(name);
            sb.append("=");
            sb.append(values.get(modelitem));
        }
        
        return sb.append("}").toString();
    }
}
