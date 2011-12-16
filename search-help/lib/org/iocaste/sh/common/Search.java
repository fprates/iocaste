package org.iocaste.sh.common;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.SearchHelp;

public class Search extends AbstractServiceInterface {
    private static final String SERVER_NAME =
            "/iocaste-search-help/service.html";
    public Search(Function function) {
        initService(function, SERVER_NAME);
    }
    
    public final ExtendedObject[] getResults(SearchHelp sh) throws Exception {
        Message message = new Message();
        
        message.setId("get_results");
        message.add("search_help", sh);
        
        return (ExtendedObject[])call(message);
    }

}
