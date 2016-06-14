package org.iocaste.shell.common;

import java.lang.reflect.Method;
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
    
    public final void addMessages(Map<String, String> messages) {
        this.messages.addMessages(messages);
    }
    
    private final void fillTranslations(View view) {
        String text;
        Map<String, Element> elements;
        ViewTitle title;
        
        elements = view.getElements();
        for (String name : elements.keySet())
            elements.get(name).translate(messages);
        
        title = view.getTitle();
        if (title.text == null)
            return;
        text = messages.get(title.text);
        if (text == null)
            return;
        if ((title.args == null || title.args.length == 0))
            view.setTitle(text, title.args);
        else
            view.setTitle(String.format(text, title.args));
    }
    
    @Override
    public Object run(Message message) throws Exception {
        Object[] viewreturn;
        Method method;
        CustomView customview;
        View view = message.get("view");
        boolean initializable = message.getbl("init");
        AbstractPage page = getFunction();
        Iocaste iocaste = new Iocaste(page);
        Locale locale = iocaste.getLocale();
        
        state.parameters = message.get("parameters");
        state.protocol = message.get("protocol");
        state.port = message.geti("port");
        state.servername = message.get("servername");
        state.keepview = state.reloadable = false;
        state.rapp = state.rpage = state.contenttype = null;
        state.contentencoding = null;
        state.pagecall = state.dontpushpage = false;
        state.headervalues.clear();
        if (context != null)
            context.view = view;
        
        view.setLocale(locale);
        if (initializable) {
            customactions.clear();
            customviews.clear();
            context = page.init(view);
            context.view = view;
            context.function = page;
            context.function.unregisterValidators();
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
        viewreturn = new Object[4];
        viewreturn[0] = view;
        viewreturn[1] = state.headervalues;
        viewreturn[2] = state.contenttype;
        viewreturn[3] = state.contentencoding;
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
    
    public final void setMessages(MessageSource messages) {
        this.messages = messages;
    }

}
