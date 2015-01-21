package org.iocaste.shell.common;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;

public class GetViewData extends AbstractHandler {
    private String msgsource;
    public ViewState state;
    public AbstractContext context;
    public Map<String, ViewCustomAction> customactions;
    public Map<String, CustomView> customviews;
    public Map<String, List<String>> validables;
    public Map<String, Validator> validators;
    
    @Override
    public Object run(Message message) throws Exception {
        Object[] viewreturn;
        MessageSource messages;
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
        state.pagecall = false;
        state.dontpushpage = false;
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
        
        if (view.getMessages() == null) {
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
            
            messages = new MessageSource();
            messages.loadFromApplication(view.getAppName(), locale, page);
            if (msgsource != null)
                messages.loadFromApplication(msgsource, locale, page);
            view.setMessages(messages);
        }
        
        state.parameters.clear();
        viewreturn = new Object[2];
        viewreturn[0] = view;
        if (state.headervalues.size() > 0) {
            viewreturn[1] = new HashMap<>(state.headervalues);
            state.headervalues.clear();
        } else {
            viewreturn[1] = state.headervalues;
        }
        
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
