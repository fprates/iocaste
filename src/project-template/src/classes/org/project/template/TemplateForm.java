package org.project.template;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.View;

/**
 * Template par módulo interno do iocaste.
 * @author francisco.prates
 *
 */
public class TemplateForm extends AbstractPage {
    private Context context;
    
    public TemplateForm() {
        export("install", "install");
    }
    
    /**
     * Rotina chamada pelo gerenciador de pacotes,
     * para retorna informações de instalação do programa.
     * @param message
     * @return
     */
    public final InstallData install(Message message) {
        return Install.init();
    }

    /**
     * Esta rotina é executada quando o botão em "main" for clicado
     * @param view
     * @return
     */
    public final void bind() {
        // carrega a biblioteca
        Template template = new Template(this);
        
        // exibe mensagem de status no cabeçalho
        context.function.message(Const.STATUS, template.getMessage());
    }
    
    @Override
    public final AbstractContext init(View view) {
        context = new Context();
        return context;
    }
    /**
     * Este é o ponto de entrada do programa
     * @param view
     */
    public final void main() {
        Text text;
        // cria um formulário html
        Form container = new Form(context.view, "main");
        
        /*
         * gera a barra do cabeçalho padrão da aplicação;
         * adiciona um link no cabeçalho para retornar a página inicial
         */
        PageControl pagecontrol = new PageControl(container);
        pagecontrol.add("home");

        
        // inclui um componente de texto na visão
        text = new Text(container, "info");
        text.setText("server.test");
        
        // inclui um botão. retorna para uma rotina com o mesmo nome do botão
        new Button(container, "bind");
        
        // ajusta o título da visão
        context.view.setTitle("iocaste-template");
    }
}

class Context extends AbstractContext {
    
}
