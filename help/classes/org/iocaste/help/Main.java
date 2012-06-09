package org.iocaste.help;

import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {

    public void main(View view) {
        
    }
    
    public final void view(View vdata) {
        Form form = new Form(vdata, "help.form");
        PageControl pagecontrol = new PageControl(form);
        Text help = new Text(form, "not.yet");
        
        pagecontrol.add("back");
        help.setText("Help not implemented. Back soon later.");
        
        vdata.setTitle("help");
    }
}
