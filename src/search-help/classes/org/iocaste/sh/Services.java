package org.iocaste.sh;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.SearchHelpData;
import org.iocaste.protocol.AbstractFunction;

public class Services extends AbstractFunction {
    public Map<String, SearchHelpData> cache;
    
    public Services() {
        cache = new HashMap<>();

        export("assign", new SearchHelpAssign());
        export("get", new SearchHelpLoad());
        export("remove", new SearchHelpRemove());
        export("save", new SearchHelpSave());
        export("unassign", new SearchHelpUnassign());
        export("update", new SearchHelpUpdate());
    }
}
