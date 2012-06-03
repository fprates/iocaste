package org.iocaste.exhandler;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.ViewData;

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
        messages.put("view-elements", "Elementos da página");
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
    public void main(ViewData view) {
        Container[] containers;
        Exception ex = view.getParameter("exception");
        ViewData exview = view.getParameter("exview");
        
        /*
         * exceçao
         */
        view.print(messages.get("exception"));
        printException(view, ex);
        
        /*
         * dados do programa interrompido
         */
        if (exview != null) {
            view.print(messages.get("view-info"));
            printOffensiveView(view, exview);
        }
        
        /*
         * pilha de chamadas
         */
        view.print(messages.get("stack-trace"));
        printStackTrace(view, ex);
        
        /*
         * elementos da visão
         */
        containers = exview.getContainers();
        if (containers.length > 0) {
            view.print(messages.get("view-elements"));
            for (Container container : containers)
                printViewContainer(view, container, "-");
        }
        
        view.setNavbarActionEnabled("home", true);
        view.setTitle(messages.get("exception-handler"));
    }
    
    /**
     * 
     * @param view
     * @param ex
     */
    private void printException(ViewData view, Exception ex) {
        String message;
        StringBuilder sb;
        
        while (ex.getCause() != null)
            ex = (Exception)ex.getCause();
        
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
    private void printOffensiveView(ViewData view, ViewData exview) {
        String[] keys;
        
        view.print(concatenate(messages.get("module"), ": ",
                checkunknown(exview.getAppName())));
        view.print(concatenate(messages.get("page"), ": ",
                checkunknown(exview.getPageName())));
        
        keys = view.getExportable();
        if (keys.length > 0) {
            view.print(messages.get("parameters"));
            
            for (String key : keys)
                view.print(concatenate("- ", key, ": ",
                        view.getParameter(key)));
        }
        
        view.print("");
    }
    
    /**
     * 
     * @param view
     * @param ex
     */
    private void printStackTrace(ViewData view, Exception ex) {
        while (ex.getCause() != null)
            ex = (Exception)ex.getCause();
        
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
    private void printViewContainer(ViewData view, Container container,
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
