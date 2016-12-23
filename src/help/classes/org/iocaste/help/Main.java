package org.iocaste.help;

import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private Context context;
    
    @SuppressWarnings("unchecked")
    public final <T extends AbstractContext> T configOnly() {
        return (T)new Context();
    }
    
    @Override
    public final AbstractContext init(View view) {
        return configOnly();
    }
    
    public void main() {
        
    }
    
    public final void view() {
        Form form = new Form(context.view, "help.form");
        Text help = new Text(form, "not.yet");
        
        help.setText("Help not implemented. Back soon later.");
        
        context.view.setTitle("help");
    }
}

class Context extends AbstractContext {
    
}