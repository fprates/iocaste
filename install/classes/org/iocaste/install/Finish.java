package org.iocaste.install;

import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.View;

public class Finish {
    private static final String[] LINES = {
        "A instalação do Iocaste foi concluída com sucesso.",
        "Você será redirecionado à tela de conexão.",
        "O usuário é \"ADMIN\", e a senha é \"iocaste\".",
        "Para prosseguir, clique em \"Continuar\"."
    };
    
    public static final void render(View view) {
        Form container = new Form(view, "main");
        
        for (int i = 0; i < LINES.length; i++)
            new Text(container, Integer.toString(i)).setText(LINES[i]);
        
        view.setTitle("finish");
        new Link(container, "continue", "/iocaste-shell").setAbsolute(true);
    }
}
