package org.iocaste.install;

import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.View;

public class Welcome {
    private static final String[] LINES = {
            "Bem-vindo ao utilitário de instalação do Iocaste.",
            "Ao prosseguir, esse utilitário ajustará o sistema para a " +
            "configuração inicial.",
            "Nesse processo, todos os dados serão perdidos.",
            "Para continuar, clique no botão \"Continuar\""
    };

    public final void action(View view) {
        view.redirect("DBCONFIG");
    }
    
    public final void render(View view) {
        Form container = new Form(view, "main");
        
        for (int i = 0; i < LINES.length; i++)
            new Text(container, Integer.toString(i)).setText(LINES[i]);
        
        new Button(container, "continue");
        new Parameter(container, "nextstage").set("DBCONFIG");
        
        view.setTitle("iocaste-install");
    }
}
