package org.iocaste.exhandler;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private Map<String, String> messages;
    
    public Main() {
        messages = new HashMap<String, String>();
        messages.put("unknown", "desconhecido");
        messages.put("page", "Página");
        messages.put("module", "Módulo");
        messages.put("exception-handler", "Erro durante execução");
        messages.put("view-info", "Visão afetada");
        messages.put("stack-trace", "Pilha de chamadas");
        messages.put("parameters", "Parâmetros");
        messages.put("exception", "Exceção");
        messages.put("view-elements", "Elementos da visão afetada");
        messages.put("no.view.information", "Sem informações da visão\n");
    }
    
    /**
     * 
     * @param value
     * @return
     */
    private final String checkunknown(String value) {
        return (value == null)? messages.get("unknown") : value;
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
    
    /**
     * 
     * @param view
     */
    public void main(View view) {
        Container[] containers;
        PageControl pagecontrol;
        Exception ex = view.getParameter("exception");
        View exview = view.getParameter("exview");
        Form form = new Form(view, "main");
        
        pagecontrol = new PageControl(form);
        pagecontrol.add("home");
        
        /*
         * exceção
         */
        view.print(messages.get("exception"));
        printException(view, ex);
        
        /*
         * dados do programa interrompido
         */
        view.print(messages.get("view-info"));
        if (exview == null)
            view.print(messages.get("no.view.information"));
        else
            printOffensiveView(view, exview);
        
        /*
         * pilha de chamadas
         */
        view.print(messages.get("stack-trace"));
        printStackTrace(view, ex);
        
        /*
         * elementos da visão
         */
        view.print(messages.get("view-elements"));
        if (exview == null) {
            view.print(messages.get("no.view.information"));
        } else {
            containers = exview.getContainers();
            if (containers.length > 0)
                for (Container container : containers)
                    printViewContainer(view, container, "-");
        }
        
        view.setTitle(messages.get("exception-handler"));
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
        
        view.print(concatenate(messages.get("module"), ": ",
                checkunknown(exview.getAppName())));
        view.print(concatenate(messages.get("page"), ": ",
                checkunknown(exview.getPageName())));
        
        keys = exview.getExportable();
        if (keys.length > 0) {
            view.print(messages.get("parameters"));
            
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
