package org.iocaste.sh;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.SearchHelp;

public class Common {
    
    /**
     * 
     * @param sh
     * @param function
     * @param criteria
     * @return
     * @throws Exception
     */
    public static final ExtendedObject[] getResultsFrom(SearchHelp sh,
            Function function, Object... criteria)
                    throws Exception {
        String[] itens;
        Documents documents = new Documents(function);
        StringBuilder sb = new StringBuilder("from ").
                append(sh.getModelName());
        
        if (criteria == null || criteria.length == 0) {
            return documents.select(sb.toString());
        } else {
            itens = sh.getItens();
            sb.append(" where");
            for (int i = 0; i < criteria.length; i++) {
                sb.append(" ").
                        append(itens[i]).
                        append(" in ?");
                if (i < (criteria.length - 1))
                    sb.append(" and");
            }
            return documents.select(sb.toString(), criteria);
        }
    }
}
