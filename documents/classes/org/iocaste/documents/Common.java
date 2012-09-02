package org.iocaste.documents;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;

public class Common {
    
	public static final int getModelItemLen(String name, Cache cache) {
	    return cache.mmodel.getModelItem(name).getDataElement().getLength();
	}
    
    /**
     * 
     * @param value
     * @return
     */
    public static final boolean isInitial(String value) {
        return (value == null || value.trim().length() == 0)? true : false;
    }
    
    /**
     * 
     * @param model
     * @param queries
     */
    public static final void parseQueries(DocumentModel model,
            Map<String, Map<String, String>> queries) {
        String fieldname;
        boolean iskey, setok = false;
        int k = 0;
        String tablename = model.getTableName();
        StringBuilder update = new StringBuilder("update ").
                append(tablename).append(" set ");
        StringBuilder insert = new StringBuilder("insert into ").
                append(tablename).append(" (");
        StringBuilder delete = new StringBuilder("delete from ").
                append(tablename);
        StringBuilder values = new StringBuilder(") values (");
        StringBuilder where = new StringBuilder(" where ");
        Map<String, String> queries_ = new HashMap<String, String>();
        
        for (DocumentModelItem modelitem : model.getItens()) {
            iskey = model.isKey(modelitem);
            
            if (k++ > 0) {
                insert.append(", ");
                values.append(", ");
                if (iskey) {
                    where.append(" and ");
                    setok = false;
                } else {
                    if (setok)
                        update.append(", ");
                    
                    setok = true;
                }
            }
            
            fieldname = modelitem.getTableFieldName();
            insert.append(fieldname);
            
            values.append("?");
            if (iskey)
                where.append(fieldname).append(" = ?");
            else
                update.append(fieldname).append(" = ?");
        }

        if (setok)
            update.append(where);
        
        insert.append(values).append(")");
        delete.append(where);
        
        queries_.put("insert", insert.toString());
        
        if (setok)
            queries_.put("update", update.toString());
        
        queries_.put("delete", delete.toString());
        
        fieldname = model.getName();
        if (queries.containsKey(fieldname))
            queries.remove(fieldname);
        
        queries.put(fieldname, queries_);
    }
}
