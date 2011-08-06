package org.template;

import org.iocaste.shell.common.AbstractForm;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.ViewData;

public class TemplateForm extends AbstractForm {

    @Override
    protected void buildViews() {
        ViewData view = getViewInstance("main");
        StandardContainer container = new StandardContainer(null);
        Text text = new Text(container);
        
        text.setText("Aplicação Iocaste Template");
        view.setContainer(container);
    }
    
    public final void templateaction() {
        // implemente ações como nesse exemplo
    }
}
