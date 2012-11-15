package org.iocaste.documents.common;

import java.io.Serializable;
import java.lang.reflect.Method;
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
public class ExtendedObject implements Serializable {
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
    
    /**
     * Retorna o modelo de documento associado.
     * @return modelo
     */
    public final DocumentModel getModel() {
        return model;
    }
    
    /**
     * Retorna valor de um item do objeto.
     * @param name nome do item
     * @return valor
     */
    public final <T> T getValue(String name) {
        return getValue(byname.get(name));
    }
    
    /**
     * Retorna valor de um item do objeto.
     * @param item item do modelo
     * @return valor
     */
    @SuppressWarnings("unchecked")
    public final <T> T getValue(DocumentModelItem item) {
        return (T)values.get(item);
    }
    
    /**
     * 
     * @param loose
     * @return
     */
    @SuppressWarnings("unchecked")
    private final <T> T newInstance(boolean loose) {
        Method method;
        Class<?> class_;
        T instance;
        
        try {
            class_ = Class.forName(model.getClassName());
            instance = (T)class_.newInstance();
            
            for (DocumentModelItem item : values.keySet()) {
                try {
                    method = instance.getClass().getMethod(
                            item.getSetterName(), item.getDataElement().
                            getClassType());
                } catch (NoSuchMethodException e) {
                    if (loose)
                        continue;
                    
                    throw e;
                }
                
                method.invoke(instance, values.get(item));
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
     * @throws Exception
     */
    public final <T> T newLooseInstance() throws Exception {
        return newInstance(true);
    }
    
    /**
     * Define valor do item do objeto extendido.
     * @param name nome do objeto
     * @param value valor
     */
    public final void setValue(String name, Object value) {
        setValue(byname.get(name), value);
    }
    
    /**
     * Define valor do item do objeto extendido.
     * @param item item de modelo
     * @param value valor
     */
    public final void setValue(DocumentModelItem item, Object value) {
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
