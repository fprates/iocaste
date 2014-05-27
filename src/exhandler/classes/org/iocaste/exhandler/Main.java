package org.iocaste.exhandler;

import java.util.HashMap;

import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private Context context;
    
    /**
     * 
     * @param value
     * @return
     */
    private final String checkunknown(String value) {
        return (value == null)? context.messages.get("unknown") : value;
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
    
    public final AbstractContext init(View view) {
        context = new Context();
        context.messages = new HashMap<>();
        context.messages.put("unknown", "desconhecido");
        context.messages.put("page", "Página");
        context.messages.put("module", "Módulo");
        context.messages.put("exception-handler", "Erro durante execução");
        context.messages.put("view-info", "Visão afetada");
        context.messages.put("stack-trace", "Pilha de chamadas");
        context.messages.put("parameters", "Parâmetros");
        context.messages.put("exception", "Exceção");
        context.messages.put("view-elements", "Elementos da visão afetada");
        context.messages.put("no.view.information",
                "Sem informações da visão\n");
        
        return context;
    }
    /**
     * 
     * @param view
     */
    public void main() {
        Container[] containers;
        PageControl pagecontrol;
        Exception ex = context.view.getParameter("exception");
        View exview = context.view.getParameter("exview");
        Form form = new Form(context.view, "main");
        
        pagecontrol = new PageControl(form);
        pagecontrol.add("home");
        
        /*
         * exceção
         */
        context.view.print(context.messages.get("exception"));
        printException(context.view, ex);
        
        /*
         * dados do programa interrompido
         */
        context.view.print(context.messages.get("view-info"));
        if (exview == null)
            context.view.print(context.messages.get("no.view.information"));
        else
            printOffensiveView(context.view, exview);
        
        /*
         * pilha de chamadas
         */
        context.view.print(context.messages.get("stack-trace"));
        printStackTrace(context.view, ex);
        
        /*
         * elementos da visão
         */
        context.view.print(context.messages.get("view-elements"));
        if (exview == null) {
            context.view.print(context.messages.get("no.view.information"));
        } else {
            containers = exview.getContainers();
            if (containers.length > 0)
                for (Container container : containers)
                    printViewContainer(context.view, container, "-");
        }
        
        context.view.setTitle(context.messages.get("exception-handler"));
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
    private void printOffensiveView(View view, View exview) {
        String[] keys;
        
        view.print(concatenate(context.messages.get("module"), ": ",
                checkunknown(exview.getAppName())));
        view.print(concatenate(context.messages.get("page"), ": ",
                checkunknown(exview.getPageName())));
        
        keys = exview.getExportable();
        if (keys.length > 0) {
            view.print(context.messages.get("parameters"));
            
            for (String key : keys)
                view.print(concatenate("- ", key, ": ",
                        exview.getParameter(key)));
        }
        
        view.print("");
    }
    
    /**
     * 
     * @param view
     * @param ex
     */
    private void printStackTrace(View view, Throwable ex) {
        while (ex.getCause() != null)
            ex = ex.getCause();
        
        for (StackTraceElement element : ex.getStackTrace())
            view.print(element.toString());
        
        view.print("");
    }
    
    /**
     * 
     * @param view
     * @param container
     * @param level
     */
    private void printViewContainer(View view, Container container,
            String level) {
        InputComponent input;
        String level_ = level + "-";
        
        view.print(concatenate(level, " ", container.getName()));
        
        for (Element element : container.getElements()) {
            if (element.isContainable()) {
                printViewContainer(view, (Container)element, level_);
                continue;
            }
            
            if (element.isDataStorable()) {
                input = (InputComponent)element;
                view.print(concatenate(level_, " ", input.getName(), ": ",
                        (input.isSecret())? "***" : input.get()));
                continue;
            }
            
            view.print(concatenate(level_, " ", element.getName()));
        }
    }
}
