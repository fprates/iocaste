package org.iocaste.exhandler;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    private Map<String, String> messages;
    
    public Main() {
        messages = new HashMap<String, String>();
        messages.put("unknown", "desconhecido");
        messages.put("page", "Página");
        messages.put("module", "Módulo");
        messages.put("exception-handler", "Erro durante execução");
        messages.put("view-info", "Programa");
        messages.put("stack-trace", "Pilha de chamada");
    }
    
    private final String checkunknown(String value) {
        return (value == null)? messages.get("unknown") : value;
    }
    
    private final String concatenate(Object... objects) {
        StringBuilder sb = new StringBuilder();
        
        for (Object object : objects)
            sb.append(object);
        
        return sb.toString();
    }
    
    public void main(ViewData view) {
        Exception ex = view.getParameter("exception");
        ViewData exview = view.getParameter("exview");
        
        if (exview != null) {
            view.print(messages.get("view-info"));
            printOffensiveView(view, exview);
        }
        
        view.print(messages.get("stack-trace"));
        printStackTrace(view, ex);
        
        view.setNavbarActionEnabled("back", true);
        view.setTitle(messages.get("exception-handler"));
    }
    
    private void printOffensiveView(ViewData view, ViewData exview) {
        view.print(concatenate(messages.get("module"), ": ",
                checkunknown(exview.getAppName())));
        view.print(concatenate(messages.get("page"), ": ",
                checkunknown(exview.getPageName())));
        view.print("");
    }
    
    private void printStackTrace(ViewData view, Exception ex) {
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
        
        for (StackTraceElement element : ex.getStackTrace())
            view.print(element.toString());
        
        view.print("");
    }
}
