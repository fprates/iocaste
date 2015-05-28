package org.iocaste.shell.common;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;

public class GetViewData extends AbstractHandler {
    private String msgsource;
    private MessageSource messages;
    public ViewState state;
    public AbstractContext context;
    public Map<String, ViewCustomAction> customactions;
    public Map<String, CustomView> customviews;
    public Map<String, List<String>> validables;
    public Map<String, Validator> validators;
    
    public GetViewData() {
        messages = new MessageSource();
    }
    
    public final void addMessages(Map<Object, Object> messages) {
        this.messages.addMessages(messages);
    }
    
    private final void fillTranslations(View view) {
        String text;
        Map<String, Element> elements;
        
        elements = view.getElements();
        for (String name : elements.keySet())
            elements.get(name).translate(messages);
        
        text = view.getTitle();
        if (text == null)
            return;
        text = messages.get(text);
        if (text == null)
            return;
        view.setTitle(text);
    }
    
    @Override
    public Object run(Message message) throws Exception {
        Object[] viewreturn;
        Method method;
        CustomView customview;
        View view = message.get("view");
        boolean initializable = message.getbool("init");
        AbstractPage page = getFunction();
        Iocaste iocaste = new Iocaste(page);
        Locale locale = iocaste.getLocale();
        
        state.parameters = message.get("parameters");
        state.protocol = message.get("protocol");
        state.port = message.geti("port");
        state.servername = message.get("servername");
        state.keepview = false;
        state.reloadable = false;
        state.rapp = null;
        state.rpage = null;
        state.contenttype = null;
        state.pagecall = false;
        state.dontpushpage = false;
        state.headervalues.clear();
        if (context != null)
            context.view = view;
        
        view.setLocale(locale);
        if (initializable) {
            customactions.clear();
            customviews.clear();
            validators.clear();
            validables.clear();
            context = page.init(view);
            context.view = view;
            context.function = page;
        }
        
        customview = customviews.get(view.getPageName());
        if (customview != null) {
            customview.execute(context);
        } else {
            method = page.getClass().getMethod(view.getPageName());
            method.invoke(page);
        }
        
        if ((messages.size() == 0) || (msgsource != null)) {
            /*
             * há alguma chance que getViewData() tenha sido chamada
             * a partir de um ticket, que nesse caso teria a localização
             * definida (provavelmente) apenas depois da chamada da visão.
             */
            if (locale == null) {
                locale = iocaste.getLocale();
                view.setLocale(locale);
                for (Container container : view.getContainers())
                    setLocaleForElement(container, view.getLocale());
            }
            
            messages.loadFromApplication(view.getAppName(), locale, page);
            if (msgsource != null)
                messages.loadFromApplication(msgsource, locale, page);
        }
        
        if (messages.size() > 0)
            fillTranslations(view);
        
        state.parameters.clear();
        viewreturn = new Object[3];
        viewreturn[0] = view;
        viewreturn[1] = state.headervalues;
        viewreturn[2] = state.contenttype;
        return viewreturn;
    }

    public final void setMessageSource(String source) {
        msgsource = source;
    }
    
    private void setLocaleForElement(Element element, Locale locale) {
        Container container;
        
        element.setLocale(locale);
        if (!element.isContainable())
            return;
        
        container = (Container)element;
        for (Element element_ : container.getElements())
            setLocaleForElement(element_, locale);
    }

}
