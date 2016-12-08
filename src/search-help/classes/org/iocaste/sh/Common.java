package org.iocaste.sh;

import java.util.List;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.documents.common.ValueRange;
import org.iocaste.documents.common.WhereClause;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.SearchHelp;

public class Common {
    
    /**
     * 
     * @param sh
     * @param function
     * @param criteria
     * @return
     */
    public static final ExtendedObject[] getResultsFrom(Documents documents,
            Context context, SearchHelp sh) {
        ValueRange range;
        List<WhereClause> wherelist;
        Query query;
        Object ns;
        String nsreference;

        nsreference = sh.getNSReference();
        ns = (nsreference != null)? ((InputComponent)context.control.getView().
                getElement(nsreference)).get() : null;
        
        query = new Query();
        query.setModel(context.model.getName());
        query.setNS(ns);
        if ((context.criteria == null) || (context.criteria.size() == 0))
            return documents.select(query);
        
        for (String name : context.criteria.keySet()) {
            range = context.criteria.get(name);
            query.andIn(name, range);
        }
        
        wherelist = query.getWhere();
        for (WhereClause criteria : sh.getCriteria())
            wherelist.add(criteria);
        
        return documents.select(query);
    }
}
