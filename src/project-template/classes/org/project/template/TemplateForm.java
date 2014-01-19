package org.project.template;

import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.ViewData;

public class TemplateForm extends AbstractPage {

    public void main(ViewData view) {
        Container container = new Form(null, "main");
        Text text = new Text(container, null);
        
        text.setText("Aplicação Iocaste Template");
        view.setTitle("iocaste-project-template");
        view.addContainer(container);
    }
    
    public final void templateaction(ControlData controldata, ViewData view) {
        // implemente ações como nesse exemplo
    }
}
