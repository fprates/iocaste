package org.iocaste.coreutils;

import org.iocaste.shell.common.AbstractForm;
import org.iocaste.shell.common.ViewData;

public class MainForm extends AbstractForm {

    public MainForm() {
        ViewData view = new ViewData();
        // inicialize os componente da tela aqui
        addView("main", view);
    }
    
    public final void submit() {
        // implemente ações como nesse exemplo
    }
}
