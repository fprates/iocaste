package org.iocaste.help;

import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {

    public void main(ViewData view) {
        
    }
    
    public final void view(ViewData vdata) {
        Container form = new Form(vdata, "help.form");
        Text help = new Text(form, "not.yet");
        
        help.setText("Help not implemented. Back soon later.");
        
        vdata.setTitle("help");
        vdata.setNavbarActionEnabled("back", true);
    }
}
