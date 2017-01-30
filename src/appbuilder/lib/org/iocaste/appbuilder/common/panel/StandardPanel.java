package org.iocaste.appbuilder.common.panel;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractExtendedValidator;
import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.appbuilder.common.navcontrol.StandardNavControlPage;
import org.iocaste.documents.common.Documents;
import org.iocaste.shell.common.Validator;

public class StandardPanel {
    private PageBuilderContext context;
    private AbstractPanelPage ncdesign;
    
    public StandardPanel(PageBuilderContext context) {
        this(context, new StandardNavControlPage());
    }

    public StandardPanel(PageBuilderContext context, AbstractPanelPage ncdesign)
    {
        this.context = context;
        this.ncdesign = ncdesign;
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
        AbstractPanelPage ncdesign;

        if (context.view == null)
            return;
        view = context.instance(name);
        view.set(new StandardPanelSpec());
        view.set(new StandardPanelConfig());
        view.set(new StandardPanelInput());
        view.set(extcontext);
        view.set(page);
        
        page.setViewContext(view);
        page.setName(name);
        try {
            page.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        ncdesign = page.getDesign();
        view.setDesign((ncdesign == null)? this.ncdesign : ncdesign);
        ncdesign = view.getDesign();
        try {
            if (ncdesign.getSpec() == null)
                ncdesign.execute();
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
