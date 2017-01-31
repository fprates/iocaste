package org.iocaste.exhandler;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;

public class MainInput extends AbstractViewInput {
    
    /**
     * 
     * @param value
     * @return
     */
    private final String checkunknown(Context extcontext, String value) {
        return (value == null)? extcontext.messages.get("unknown") : value;
    }
    
    /**
     * 
     * @param objects
     * @return
     */
    private final String concatenate(Object... objects) {
        StringBuilder sb = new StringBuilder();
        
        for (Object object : objects)
            sb.append(object);
        
        return sb.toString();
    }

    @Override
    protected void execute(PageBuilderContext context) {
        Container[] containers;
        Context extcontext;
        List<String> text = new ArrayList<>();
        
        extcontext = getExtendedContext();
        text.add(extcontext.messages.get("exception"));
        printException(text, extcontext.ex);
        
        /*
         * dados do programa interrompido
         */
        text.add(extcontext.messages.get("view-info"));
        if (extcontext.exview == null)
            text.add(extcontext.messages.get("no.view.information"));
        else
            printOffensiveView(text, extcontext);
        
        /*
         * pilha de chamadas
         */
        text.add(extcontext.messages.get("stack-trace"));
        printStackTrace(text, extcontext.ex);
        
        /*
         * elementos da visão
         */
        text.add(extcontext.messages.get("view-elements"));
        if (extcontext.exview == null) {
            text.add(extcontext.messages.get("no.view.information"));
        } else {
            containers = extcontext.exview.getContainers();
            if (containers.length > 0)
                for (Container container : containers)
                    printViewContainer(text, container, "-");
        }
        
        print(text);
        context.view.setTitle(extcontext.messages.get("exception-handler"));
    }

    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }
    
    /**
     * 
     * @param view
     * @param ex
     */
    private void printException(List<String> text, Throwable ex) {
        String message;
        StringBuilder sb;
        
        while (ex.getCause() != null)
            ex = ex.getCause();
        
        sb = new StringBuilder(ex.getClass().getName());
        message = ex.getMessage();
        if (message != null)
            sb.append(": ").append(message);
        
        text.add(sb.toString());
        text.add("");
    }
    
    /**
     * 
     * @param view
     * @param exview
     */
    private void printOffensiveView(List<String> text, Context extcontext) {
        text.add(concatenate(extcontext.messages.get("module"), ": ",
                checkunknown(extcontext, extcontext.exview.getAppName())));
        text.add(concatenate(extcontext.messages.get("page"), ": ",
                checkunknown(extcontext, extcontext.exview.getPageName())));
        text.add("");
    }
    
    /**
     * 
     * @param view
     * @param ex
     */
    private void printStackTrace(List<String> text, Throwable ex) {
        while (ex.getCause() != null)
            ex = ex.getCause();
        
        for (StackTraceElement element : ex.getStackTrace())
            text.add(element.toString());
        
        text.add("");
    }
    
    private void printViewContainer(
            List<String> text, Container container, String level) {
        InputComponent input;
        String level_ = level + "-";
        
        text.add(concatenate(level, " ", container.getName()));
        
        for (Element element : container.getElements()) {
            if (element == null) {
                text.add("null element");
                continue;
            }
            
            if (element.isContainable()) {
                printViewContainer(text, (Container)element, level_);
                continue;
            }
            
            if (element.isDataStorable()) {
                input = (InputComponent)element;
                text.add(concatenate(level_, " ", input.getName(), ": ",
                        (input.isSecret())? "***" : input.get()));
                continue;
            }
            
            text.add(concatenate(level_, " ", element.getName()));
        }
    }

}
