package org.iocaste.kernel.documents;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.kernel.common.AbstractFunction;
import org.iocaste.kernel.database.Database;

public class Documents extends AbstractFunction {
    public Cache cache;
    public Database database;
    
    public Documents() {
        cache = new Cache(this);
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
//        export("is_locked", new IsLocked());
//        export("lock", new Lock());
        export("modify", new ModifyDocument());
        export("remove_complex_model", new RemoveComplexModel());
        export("remove_model", new RemoveModel());
//        export("remove_number_factory", new RemoveNumberFactory());
        export("save_document", new SaveDocument());
        export("save_complex_document", new SaveComplexDocument());
        export("select_document", new SelectDocument());
        export("select_to_map", new SelectToMap());
//        export("unlock", new Unlock());
        export("update_document", new UpdateDocument());
//        export("update_m", new UpdateMultiple());
//        export("update_model", new UpdateModel());
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
    public final void parseQueries(DocumentModel model) {
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
        if (cache.queries.containsKey(fieldname))
            cache.queries.remove(fieldname);
        
        cache.queries.put(fieldname, queries_);
    }
}
