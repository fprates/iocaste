package org.template;

import org.iocaste.shell.common.AbstractForm;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.ViewData;

public class TemplateForm extends AbstractForm {

    /**
     * Aplicação template: adapte o código à vontade
     */
    public TemplateForm() {
        ViewData view = new ViewData();
        StandardContainer container = new StandardContainer(null);
        Text text = new Text(container);
        
        text.setText("Aplicação Iocaste Template");
        
        addView("main", view);
    }
    
    public final void templateaction() {
        // implemente ações como nesse exemplo
    }
}
