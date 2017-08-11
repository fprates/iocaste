package org.iocaste.runtime.common.page;

import org.iocaste.runtime.common.application.Context;

public class StandardPageFactory {
    
    public final void instance(Context context, String name, AbstractPage page)
            throws Exception {
        AbstractPage child;
//        Documents documents;
//        AbstractExtendedValidator validator;
//        Map<String, Validator> validators;
        page.set(context);
        page.run();
        if (page.getActionHandler("back") == null)
            page.put("back", new Back());
        for (String key : page.getChildren()) {
            child = page.getChild(key);
            child.set(context);
            child.run();
        }
//        context.pageInstance(name);
//        documents = new Documents(context.function);
//        validators = view.getValidators();
//        for (String key : validators.keySet()) {
//            validator = (AbstractExtendedValidator)validators.get(key);
//            validator.setDocuments(documents);
//            validator.setContext(context);
//        }
    }
        
}
