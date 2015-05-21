package org.quantic.template;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Const;
import org.quantic.externaltest.common.Template;

public class Bind extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        // carrega a biblioteca
        Template template = new Template(context.function);
        
        // exibe mensagem de status no cabeçalho
        message(Const.STATUS, template.getMessage());
    }

}
