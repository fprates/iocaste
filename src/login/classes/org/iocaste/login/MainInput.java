package org.iocaste.login;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class MainInput extends AbstractViewInput {
    private static final Map<String, Object> LANGUAGES;
    
    static {
        LANGUAGES = new LinkedHashMap<>();
        LANGUAGES.put("Português (Brasil)", "pt_BR");
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        dfset("login", extcontext.object);
    }

    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
        dflistset("login", "LOCALE", LANGUAGES);
    }

}
