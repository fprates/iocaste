package org.iocaste.documents;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;

public class Modify {
    
    /**
     * 
     * @param object
     * @param function
     * @return
     * @throws Exception
     */
    public static final int init(ExtendedObject object, Function function)
            throws Exception {
        String query;
        Object value;
        int nrregs;
        DocumentModel model = object.getModel();
        List<Object> criteria = new ArrayList<Object>();
        List<Object> uargs = new ArrayList<Object>();
        List<Object> iargs = new ArrayList<Object>();
        Iocaste iocaste = new Iocaste(function);
        
        for (DocumentModelItem item : model.getItens()) {
            value = object.get(item);
            
            iargs.add(value);
            if (model.isKey(item))
                criteria.add(value);
            else
                uargs.add(value);
        }
        
        uargs.addAll(criteria);
        nrregs = 0;
        query = model.getQuery("update");
        if (query != null)
            nrregs = iocaste.update(query, uargs.toArray());
        else
            return 0;
        
        if (nrregs == 0)
            if (iocaste.update(model.getQuery("insert"), iargs.toArray()) == 0)
                    throw new IocasteException("Error on object insert");
        
        return 1;
    }

}
