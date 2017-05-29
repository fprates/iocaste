package org.iocaste.runtime.common.page;

import org.iocaste.runtime.common.application.Context;

public class StandardPageFactory {
    private Context context;
    
    public StandardPageFactory(Context context) {
        this.context = context;
    }
    
    public final void instance(String name, AbstractPage page) throws Exception
    {
        AbstractPage child;
//        Documents documents;
//        AbstractExtendedValidator validator;
//        Map<String, Validator> validators;
        page.set(context);
        page.execute();
        for (String key : page.getChildren()) {
            child = page.getChild(key);
            child.set(context);
            child.execute();
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
