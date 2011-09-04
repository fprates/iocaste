package org.project.template;

import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.ViewData;

public class TemplateForm extends AbstractPage {

    public void main(ViewData view) {
        StandardContainer container = new StandardContainer(null, null);
        Text text = new Text(container, null);
        
        text.setText("Aplicação Iocaste Template");
        view.setContainer(container);
    }
    
    public final void templateaction(ControlData controldata, ViewData view) {
        // implemente ações como nesse exemplo
    }
}
