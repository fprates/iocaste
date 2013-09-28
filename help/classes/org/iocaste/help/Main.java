package org.iocaste.help;

import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.PageContext;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private Context context;
    
    @Override
    public final PageContext init(View view) {
        context = new Context();
        
        return context;
    }
    
    public void main() {
        
    }
    
    public final void view() {
        Form form = new Form(context.view, "help.form");
        PageControl pagecontrol = new PageControl(form);
        Text help = new Text(form, "not.yet");
        
        pagecontrol.add("back");
        help.setText("Help not implemented. Back soon later.");
        
        context.view.setTitle("help");
    }
}

class Context extends PageContext {
    
}