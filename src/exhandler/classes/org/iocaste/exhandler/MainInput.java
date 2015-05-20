package org.iocaste.exhandler;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.View;

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
        
        extcontext = getExtendedContext();
        print(extcontext.messages.get("exception"));
        printException(context.view, extcontext.ex);
        
        /*
         * dados do programa interrompido
         */
        print(extcontext.messages.get("view-info"));
        if (extcontext.exview == null)
            print(extcontext.messages.get("no.view.information"));
        else
            printOffensiveView(extcontext);
        
        /*
         * pilha de chamadas
         */
        print(extcontext.messages.get("stack-trace"));
        printStackTrace(extcontext.ex);
        
        /*
         * elementos da visão
         */
        print(extcontext.messages.get("view-elements"));
        if (extcontext.exview == null) {
            print(extcontext.messages.get("no.view.information"));
        } else {
            containers = extcontext.exview.getContainers();
            if (containers.length > 0)
                for (Container container : containers)
                    printViewContainer(container, "-");
        }
        
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
    private void printException(View view, Throwable ex) {
        String message;
        StringBuilder sb;
        
        while (ex.getCause() != null)
            ex = ex.getCause();
        
        sb = new StringBuilder(ex.getClass().getName());
        message = ex.getMessage();
        if (message != null)
            sb.append(": ").append(message);
        
        view.print(sb.toString());
        view.print("");
    }
    
    /**
     * 
     * @param view
     * @param exview
     */
    private void printOffensiveView(Context extcontext) {
        print(concatenate(extcontext.messages.get("module"), ": ",
                checkunknown(extcontext, extcontext.exview.getAppName())));
        print(concatenate(extcontext.messages.get("page"), ": ",
                checkunknown(extcontext, extcontext.exview.getPageName())));
        print("");
    }
    
    /**
     * 
     * @param view
     * @param ex
     */
    private void printStackTrace(Throwable ex) {
        while (ex.getCause() != null)
            ex = ex.getCause();
        
        for (StackTraceElement element : ex.getStackTrace())
            print(element.toString());
        
        print("");
    }
    
    /**
     * 
     * @param view
     * @param container
     * @param level
     */
    private void printViewContainer(Container container, String level) {
        InputComponent input;
        String level_ = level + "-";
        
        print(concatenate(level, " ", container.getName()));
        
        for (Element element : container.getElements()) {
            if (element.isContainable()) {
                printViewContainer((Container)element, level_);
                continue;
            }
            
            if (element.isDataStorable()) {
                input = (InputComponent)element;
                print(concatenate(level_, " ", input.getName(), ": ",
                        (input.isSecret())? "***" : input.get()));
                continue;
            }
            
            print(concatenate(level_, " ", element.getName()));
        }
    }

}
