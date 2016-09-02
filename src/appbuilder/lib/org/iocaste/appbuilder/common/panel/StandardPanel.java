package org.iocaste.appbuilder.common.panel;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractExtendedValidator;
import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.documents.common.Documents;
import org.iocaste.shell.common.Validator;

public class StandardPanel {
    private PageBuilderContext context;
    
    public StandardPanel(PageBuilderContext context) {
        this.context = context;
    }

    public final void instance(String name, AbstractPanelPage page) {
        instance(name, page, null);
    }

    public final void instance(
            String name, AbstractPanelPage page, ExtendedContext extcontext) {
        ViewContext view;
        Documents documents;
        AbstractExtendedValidator validator;
        Map<String, Validator> validators;
        
        view = context.instance(name);
        view.set(new StandardPanelSpec(page));
        view.set(new StandardPanelConfig(page));
        view.set(new StandardPanelInput(page));
        view.set(extcontext);
        
        page.setViewContext(view);
        try {
            page.execute();
            view.set(page.getDesign());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        if (extcontext != null)
            extcontext.pageInstance(name);

        documents = new Documents(context.function);
        validators = view.getValidators();
        for (String key : validators.keySet()) {
            validator = (AbstractExtendedValidator)validators.get(key);
            validator.setDocuments(documents);
            validator.setContext(context);
        }   
    }
        
}
