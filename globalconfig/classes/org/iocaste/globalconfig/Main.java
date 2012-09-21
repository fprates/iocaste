package org.iocaste.globalconfig;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.View;

/**
 * 
 * @author francisco.prates
 *
 */
public class Main extends AbstractPage {

    public Main() {
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
     * 
     * @param view
     */
    public final void main(View view) {
        
    }
}
