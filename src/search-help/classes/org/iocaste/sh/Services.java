package org.iocaste.sh;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.documents.common.SearchHelpData;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {
    public Map<String, SearchHelpData> cache;
    
    public Services() {
        cache = new HashMap<>();

        export("assign", "assign");
        export("get", new SearchHelpLoad());
        export("remove", new SearchHelpRemove());
        export("save", new SearchHelpSave());
        export("unassign", "unassign");
        export("update", new SearchHelpUpdate());
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final int assign(Message message) {
        DocumentModelItem item = message.get("model_item");
        Documents documents = new Documents(this);
        DocumentModel shref = documents.getModel("SH_REFERENCE");
        ExtendedObject reference = new ExtendedObject(shref);
        
        reference.set("MODEL_ITEM", Documents.getComposedName(item));
        reference.set("SEARCH_HELP", item.getSearchHelp());
        
        return documents.save(reference);
    }
    
    /**
     * 
     * @param model
     * @param item
     * @return
     */
    public final String composeName(String model, Object item) {
        return new StringBuilder(model).append(".").
                append(item).toString();
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final int unassign(Message message) {
        String shname = message.getst("name");
        Query query = new Query("delete");
        
        query.setModel("SH_REFERENCE");
        query.andEqual("SEARCH_HELP", shname);
        return new Documents(this).update(query);
    }
}
