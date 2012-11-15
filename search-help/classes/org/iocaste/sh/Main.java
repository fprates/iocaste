package org.iocaste.sh;

import java.util.Map;

import org.iocaste.documents.common.ValueRange;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private Map<String, ValueRange> criteria;
    
    public Main() {
        export("install", "install");
    }
    
    /**
     * 
     * @param controldata
     * @param view
     */
    @Override
    public void back(View view) {
        String[] entry;
        entry = new Shell(this).popPage(view);
        
        view.redirect(entry[0], entry[1]);
        view.dontPushPage();
    }
    
    /**
     * 
     * @param vdata
     */
    public final void choose(View vdata) {
        updateView(Request.choose(vdata));
        back(vdata);
        vdata.setReloadableView(false);
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
    public void main(View view) {
        Response.main(view, this, criteria);
    }
    
    /**
     * 
     * @param view
     */
    public void search(View view) {
        criteria = Request.search(view, this);
    }
}
