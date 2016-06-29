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
        
        export("complex_model_update", new ComplexModelUpdate());
        export("create_complex_model", new CreateCModel());
        protect("create_data_element", new CreateDataElement());
        export("create_model", new CreateModel());
        export("create_models", new CreateModels());
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
    
    private final StringBuilder initUpdateStatement(String tablename) {
        return new StringBuilder("update ").
                append(tablename).append(" set ");
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
        boolean iskey, keystarted, istarted, ustarted;
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
        update = null;
        insert = new StringBuilder("insert into ").
                append(tablename).append(" (");
        delete = new StringBuilder("delete from ").append(tablename);
        values = new StringBuilder(") values (");
        where = new StringBuilder(" where ");
        queries = new HashMap<>();
        ustarted = istarted = keystarted = false;
        for (DocumentModelItem modelitem : items) {
            if (modelitem == null) {
                System.err.println(new StringBuilder("null item for model ").
                        append(model.getName()).
                        append(". ignoring it.").toString());
                continue;
            }
            
            if (istarted) {
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
                keystarted = true;
            } else {
                if (update == null)
                    update = initUpdateStatement(tablename);
                if (ustarted)
                    update.append(", ");
                else
                    ustarted = true;
                update.append(fieldname).append(" = ?");
            }
            istarted = true;
        }

        ns = model.getNamespace();
        if (ns != null) {
            fieldname = ns.getTableFieldName();
            insert.append(", ").append(fieldname);
            if (update == null) {
                update = initUpdateStatement(tablename);
                ustarted = true;
            } else {
                update.append(", ");
            }
            update.append(fieldname).append(" = ?");
            where.append(" and ").append(fieldname).append(" = ?");
            values.append(", ?");
        }
        
        insert.append(values).append(")");
        queries.put("insert", insert.toString());
        delete.append(where);
        queries.put("delete", delete.toString());
        if (update != null) {
            update.append(where);
            queries.put("update", update.toString());
        }
        
        return queries;
    }
}
