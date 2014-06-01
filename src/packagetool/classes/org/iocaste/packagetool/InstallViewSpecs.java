package org.iocaste.packagetool;

import java.util.List;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.packagetool.common.ViewSpec;
import org.iocaste.packagetool.common.ViewSpecItem;

public class InstallViewSpecs {

    public static final void delete(String name, Documents documents) {
        Query query = new Query("delete");
        
        query.setModel("APP_BUILDER_VIEWS");
        query.andEqual("APP_NAME", name);
        documents.update(query);
    }
    
    public static final void init(List<ViewSpec> specs, State state) {
        int c = 0;
        DocumentModel model = state.documents.getModel("APP_BUILDER_VIEWS");
        
        for (ViewSpec spec : specs)
            for (ViewSpecItem item : spec.getItems())
                c = installViewSpecItem(item, model, state, c);
        
        Registry.add(state.pkgname, "APP_BUILDER_VIEWS", state);
    }
    
    private static final int installViewSpecItem(
            ViewSpecItem item, DocumentModel model, State state, int c) {
        ExtendedObject object;
        String key;
        
        c++;
        key = new StringBuilder(state.pkgname).
                append(String.format("%04d", c)).toString().toUpperCase();
        
        object = new ExtendedObject(model);
        object.set("VIEW_ID", key);
        object.set("APP_NAME", state.pkgname);
        object.set("VIEW_NAME", item.getView());
        object.set("CONTAINER", item.getParent());
        object.set("NAME", item.getName());
        object.set("TYPE", item.getType());
        state.documents.save(object);
        
        for (ViewSpecItem child : item.getItems())
            c = installViewSpecItem(child, model, state, c);
        
        return c;
    }
}
