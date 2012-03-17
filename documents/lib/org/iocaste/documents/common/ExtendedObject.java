package org.iocaste.documents.common;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ExtendedObject implements Serializable {
	private static final long serialVersionUID = -8700097929412206566L;
	private Map<DocumentModelItem, Object> values;
	private Map<String, DocumentModelItem> byname;
	private DocumentModel model;
	
	public ExtendedObject(DocumentModel model) {
		values = new HashMap<DocumentModelItem, Object>();
		byname = new HashMap<String, DocumentModelItem>();
		
		for (DocumentModelItem item : model.getItens())
		    byname.put(item.getName(), item);
		
		this.model = model;
	}
	
	/**
	 * 
	 * @return
	 */
	public final DocumentModel getModel() {
	    return model;
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public final Object getValue(String name) {
	    return getValue(byname.get(name));
	}
	
	/**
	 * 
	 * @param item
	 * @return
	 */
	public final Object getValue(DocumentModelItem item) {
		return values.get(item);
	}

	/**
	 * 
	 * @param loose
	 * @return
	 * @throws Exception
	 */
    @SuppressWarnings("unchecked")
	private final <T> T newInstance(boolean loose) throws Exception {
        Method method;
        Class<?> class_ = Class.forName(model.getClassName());
        T instance = (T)class_.newInstance();
        
        for (DocumentModelItem item : values.keySet()) {
            try {
                method = instance.getClass().getMethod(
                        item.getSetterName(), item.getDataElement().
                        getClassType());
            } catch (NoSuchMethodException e) {
                if (loose)
                    continue;
                else
                    throw e;
            }
            
            method.invoke(instance, values.get(item));
        }
        
        return instance;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public final <T> T newInstance() throws Exception {
	    return newInstance(false);
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public final <T> T newLooseInstance() throws Exception {
	    return newInstance(true);
	}
	
	/**
	 * 
	 * @param name
	 * @param value
	 */
	public final void setValue(String name, Object value) {
	    setValue(byname.get(name), value);
	}
	
	/**
	 * 
	 * @param item
	 * @param value
	 */
	public final void setValue(DocumentModelItem item, Object value) {
	    if (!model.contains(item))
	        return;
	    
	    if (values.containsKey(item))
	        values.remove(item);
	    
	    values.put(item, value);
	}
}
