package org.iocaste.documents.common;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ExtendedObject implements Serializable {
	private static final long serialVersionUID = -8700097929412206566L;
	private Map<DocumentModelItem, Object> values;
	private DocumentModel model;
	
	public ExtendedObject(DocumentModel model) {
		values = new HashMap<DocumentModelItem, Object>();
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
	 * @param item
	 * @return
	 */
	public final Object getValue(DocumentModelItem item) {
		return values.get(item);
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public final <T> T newInstance() throws Exception {
		Method method;
        Class<?> class_ = Class.forName(model.getClassName());
		T instance = (T)class_.newInstance();
        
        for (DocumentModelItem item : values.keySet()) {
    		method = instance.getClass().getMethod(
    				item.getSetterName(), item.getDataElement().
    				getClassType());
    		method.invoke(instance, values.get(item));
        }
        
        return instance;
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
