package org.iocaste.runtime.common.page;

import org.iocaste.runtime.common.application.Context;

public class StandardPageFactory {
    
    public final void instance(Context context, String name, AbstractPage page)
            throws Exception {
//        Documents documents;
//        AbstractExtendedValidator validator;
//        Map<String, Validator> validators;
        String defaultpage = context.getPageName();
        
        context.setPageName(name);
        page.set(context);
        page.run();
        for (String key : page.getChildren())
            instance(context, name, page.getChild(key));
//        context.pageInstance(name);
//        documents = new Documents(context.function);
//        validators = view.getValidators();
//        for (String key : validators.keySet()) {
//            validator = (AbstractExtendedValidator)validators.get(key);
//            validator.setDocuments(documents);
//            validator.setContext(context);
//        }
        context.setPageName(defaultpage);
    }
        
}
