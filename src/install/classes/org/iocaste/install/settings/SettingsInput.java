package org.iocaste.install.settings;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.StandardViewInput;
import org.iocaste.install.DBConfig;
import org.iocaste.kernel.common.DBNames;

public class SettingsInput extends StandardViewInput {
    private static final Map<String, Object> OPTIONS;
    
    static {
        OPTIONS = new LinkedHashMap<>();
        OPTIONS.put("keepbase", DBConfig.KEEP_BASE);
        OPTIONS.put("chngbase", DBConfig.CHANGE_BASE);
        OPTIONS.put("newbase", DBConfig.NEW_BASE);
    }
    
    @Override
    public final void init(PageBuilderContext context) {
        super.init(context);
        dflistset("dbinfo", "OPTIONS", OPTIONS);
        
        for (String key : DBNames.names.keySet())
            textset(key, key);
    }
}