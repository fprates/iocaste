package org.iocaste.sh;

import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.SearchHelpColumn;
import org.iocaste.documents.common.SearchHelpData;
import org.iocaste.documents.common.WhereClause;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class SearchHelpSave extends AbstractHandler {

    public final void items(Documents documents, SearchHelpData shdata,
            DocumentModel model) throws Exception {
        String shname;
        ExtendedObject object;
        Map<String, SearchHelpColumn> items;
        DocumentModelItem item;
        int i = 0;
        
        items = shdata.getItems();
        if (items.size() == 0)
            throw new IocasteException("sh has no columns itens.");

        shname = shdata.getName();
        object = new ExtendedObject(documents.getModel("SH_ITENS"));
        for (String key : items.keySet()) {
            item = model.getModelItem(key);
            if (item == null)
                throw new IocasteException("item %s.%s not found for sh %s",
                        model.getName(), key, shname);
            object.set("NAME", String.format("%s%03d", shname, i++));
            object.set("ITEM", item.getIndex());
            object.set("SEARCH_HELP", shname);
            if (documents.save(object) != 0)
                continue;
            throw new IocasteException(
                    "Error saving line of sh %s", object.getst("NAME"));
        }
    }
    
    public final void queries(Documents documents, DocumentModel shmodel,
            SearchHelpData shdata) throws Exception {
        List<WhereClause> wherelist;
        ExtendedObject object;
        DocumentModel model;
        int i;
        String field, shname;
        
        wherelist = shdata.getWhere();
        if (wherelist.size() > 0) {
            i = 0;
            shname = shdata.getName();
            model = documents.getModel("SH_QUERIES");
            object = new ExtendedObject(model);
            for (WhereClause where : wherelist) {
                field = where.getField();
                if (shmodel.getModelItem(field) == null)
                    throw new IocasteException("invalid query field %s for %s",
                            field, shname);
                object.set("ID", String.format("%s%03d", shname, i++));
                object.set("SEARCH_HELP", shname);
                object.set("FIELD", field);
                object.set("OPERATOR", where.getCondition());
                object.set("VALUE", where.getValue().toString());
                documents.save(object);
            }
        }
    }
    
    @Override
    public Object run(Message message) throws Exception {
        String shmodel, export, shname;
        Documents documents;
        Services function;
        ExtendedObject object;
        DocumentModel model;
        SearchHelpData shdata = message.get("shdata");
        
        function = getFunction();
        documents = new Documents(function);
        shmodel = shdata.getModel();
        if ((model = documents.getModel(shmodel)) == null)
            throw new IocasteException("%s is an invalid model.", shmodel);

        shname = shdata.getName();
        export = model.getModelItem(shdata.getExport()).getIndex();
        
        object = new ExtendedObject(documents.getModel("SEARCH_HELP"));
        object.set("NAME", shname);
        object.set("MODEL", shmodel);
        object.set("EXPORT", export);
        if (documents.save(object) == 0)
           throw new IocasteException ("Error saving header of sh %s.", shname);
        
        items(documents, shdata, model);
        queries(documents, model, shdata);
        
        function.cache.put(shname, shdata);
        return null;
    }
    
}