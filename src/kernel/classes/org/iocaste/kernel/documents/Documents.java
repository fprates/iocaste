package org.iocaste.kernel.documents;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.kernel.config.Config;
import org.iocaste.kernel.database.Database;
import org.iocaste.protocol.AbstractFunction;

public class Documents extends AbstractFunction {
    public Cache cache;
    public Database database;
    public Config config;
    public Map<String, Set<LockEntry>> lockcache;
    
    public Documents() {
        cache = new Cache(this);
        lockcache = new HashMap<>();
        
        export("clear_namespace", new ClearNamespace());
        export("create_complex_model", new CreateCModel());
        protect("create_data_element", new CreateDataElement());
        export("create_model", new CreateModel());
        export("create_namespace", new CreateNameSpace());
        export("create_number_factory", new CreateNumberFactory());
        export("delete_document", new DeleteDocument());
        export("delete_complex_document", new DeleteComplexDocument());
        export("get_complex_document", new GetComplexDocument());
        export("get_complex_model", new GetComplexModel());
        export("get_data_element", new GetDataElement());
        export("get_next_number", new GetNextNumber());
        export("get_object", new GetObject());
        export("get_document_model", new GetDocumentModel());
        protect("insert_data_element", new InsertDataElement());
        export("is_locked", new IsLocked());
        export("lock", new Lock());
        export("modify", new ModifyDocument());
        export("remove_complex_model", new RemoveComplexModel());
        export("remove_model", new RemoveModel());
        export("remove_namespace", new RemoveNameSpace());
        export("remove_number_factory", new RemoveNumberFactory());
        export("save_document", new SaveDocument());
        export("save_complex_document", new SaveComplexDocument());
        export("select_document", new SelectDocument());
        export("select_to_map", new SelectToMap());
        export("set_namespace", new SetNamespace());
        export("unlock", new Unlock());
        export("update_document", new UpdateDocument());
        export("update_m", new UpdateMultiple());
        export("update_model", new UpdateModel());
    }
    
    public final int getModelItemLen(String name) {
        return cache.mmodel.getModelItem(name).getDataElement().getLength();
    }
    
    public final String getQuery(
            String sessionid, DocumentModel model, String id) {
        Map<String, String> queries;
        Map<String, Map<String, String>> nsqueries;
        String name = model.getName();
        
        nsqueries = cache.nsqueries.get(sessionid);
        if ((nsqueries != null) && nsqueries.containsKey(name)) {
            queries = nsqueries.get(name);
            if (queries != null)
                return queries.get(id);
        }
        
        return cache.queries.get(name).get(id);
    }
    
    /**
     * 
     * @param value
     * @return
     */
    public static final boolean isInitial(String value) {
        return (value == null || value.trim().length() == 0);
    }
    
    public final Map<String, String> parseQueries(
            NamespaceEntry ns, DocumentModel model) {
        String fieldname, nsvalue;
        boolean iskey, setok;
        int k;
        String tablename;
        StringBuilder update, insert, delete, values, where;
        Map<String, String> queries;

        tablename = model.getTableName();
        if (ns != null) {
            nsvalue = ns.value.toString();
            tablename = new StringBuilder(nsvalue).append(tablename).toString();
        }
        
        update = new StringBuilder("update ").append(tablename).append(" set ");
        insert = new StringBuilder("insert into ").
                append(tablename).append(" (");
        delete = new StringBuilder("delete from ").append(tablename);
        values = new StringBuilder(") values (");
        where = new StringBuilder(" where ");
        queries = new HashMap<>();
        
        setok = false;
        k = 0;
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
        
        queries.put("insert", insert.toString());
        
        if (setok)
            queries.put("update", update.toString());
        
        queries.put("delete", delete.toString());
        return queries;
    }
}

class NamespaceEntry {
    public String id;
    public Object value;
    public Map<String, Map<String, String>> queries;
    
    public NamespaceEntry(String id, Object value) {
        this.id = id;
        this.value = value;
        queries = new HashMap<>();
    }
}