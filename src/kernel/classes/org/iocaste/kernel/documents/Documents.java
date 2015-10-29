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
        
        export("create_complex_model", new CreateCModel());
        protect("create_data_element", new CreateDataElement());
        export("create_model", new CreateModel());
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
        export("remove_number_factory", new RemoveNumberFactory());
        export("save_document", new SaveDocument());
        export("save_documents", new SaveDocuments());
        export("save_complex_document", new SaveComplexDocument());
        export("select_document", new SelectDocument());
        export("select_to_map", new SelectToMap());
        export("unlock", new Unlock());
        export("update_document", new UpdateDocument());
        export("update_m", new UpdateMultiple());
        export("update_model", new UpdateModel());
    }
    
    public final int getModelItemLen(String name) {
        return cache.mmodel.getModelItem(name).getDataElement().getLength();
    }
    
    /**
     * 
     * @param value
     * @return
     */
    public static final boolean isInitial(String value) {
        return (value == null || value.trim().length() == 0);
    }
    
    /**
     * 
     * @param model
     * @param queries
     */
    public final Map<String, String> parseQueries(DocumentModel model) {
        DocumentModelItem ns;
        String fieldname;
        boolean iskey, keystarted, setok, started;
        DocumentModelItem[] items;
        String tablename;
        StringBuilder update, insert, delete, values, where;
        Map<String, String> queries;
        
        try {
            items = model.getItens();
        } catch (Exception e) {
            /*
             * corrupted model. don't generate queries.
             */
            return null;
        }

        tablename = model.getTableName();
        update = new StringBuilder("update ").append(tablename).append(" set ");
        insert = new StringBuilder("insert into ").
                append(tablename).append(" (");
        delete = new StringBuilder("delete from ").append(tablename);
        values = new StringBuilder(") values (");
        where = new StringBuilder(" where ");
        queries = new HashMap<>();
        started = keystarted = setok = false;
        for (DocumentModelItem modelitem : items) {
            if (modelitem == null) {
                System.err.println(new StringBuilder("null item for model ").
                        append(model.getName()).
                        append(". ignoring it.").toString());
                continue;
            }
            
            if (started) {
                insert.append(", ");
                values.append(", ");
            }

            iskey = model.isKey(modelitem);
            if (keystarted && iskey)
                where.append(" and ");
            
            fieldname = modelitem.getTableFieldName();
            insert.append(fieldname);
            values.append("?");
            if (iskey) {
                where.append(fieldname).append(" = ?");
                keystarted = started = true;
            } else {
                if (started)
                    update.append(", ");
                else
                    started = true;
                update.append(fieldname).append(" = ?");
            }
        }

        ns = model.getNamespace();
        if (ns != null) {
            fieldname = ns.getTableFieldName();
            insert.append(", ").append(fieldname);
            update.append(", ").append(fieldname).append(" = ?");
            where.append(" and ").append(fieldname).append(" = ?");
            values.append(", ?");
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
