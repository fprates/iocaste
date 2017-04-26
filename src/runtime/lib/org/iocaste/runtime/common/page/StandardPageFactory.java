package org.iocaste.runtime.common.page;

import java.util.Map;

import org.iocaste.runtime.common.application.Context;

public class StandardPageFactory {
    private Context context;
    
    public StandardPageFactory(Context context) {
        this.context = context;
    }
    
    public final void instance(String name, AbstractPage page) throws Exception
    {
//        ViewContext view;
//        Documents documents;
//        AbstractExtendedValidator validator;
//        Map<String, Validator> validators;
    	
        page.execute();
        for (String key : page.getChildren())
        	page.getChild(key).execute();
//        
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
