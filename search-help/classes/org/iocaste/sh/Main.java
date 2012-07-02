package org.iocaste.sh;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    
    public Main() {
        export("install", "install");
    }
    
    /**
     * 
     * @param controldata
     * @param view
     * @throws Exception 
     */
    @Override
    public void back(View view) throws Exception {
        String[] entry = new Shell(this).popPage(view);
        view.redirect(entry[0], entry[1]);
        view.dontPushPage();
    }
    
    /**
     * 
     * @param vdata
     * @throws Exception
     */
    public final void choose(View vdata) throws Exception {
        updateView(Request.choose(vdata));
        back(vdata);
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public void main(View view) throws Exception {
        Response.main(view, this);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public void search(View view) throws Exception {
        Request.search(view, this);
    }
}
