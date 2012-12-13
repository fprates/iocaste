package org.iocaste.workbench;

import java.io.File;

import org.iocaste.globalconfig.common.GlobalConfig;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.View;

/**
 * 
 * @author francisco.prates
 *
 */
public class Main extends AbstractPage {
    private String repository;
    private boolean validrepo;
    private Project project;
    
    public Main() {
        export("install", "install");
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void activate(View view) throws Exception {
        Request.activate(view, project);
    }
    
    /**
     * 
     * @param view
     */
    public final void addscreen(View view) {
        Request.addscreen(view);
    }
    
    /**
     * 
     * @param view
     */
    public final void createproject(View view) {
        project = Request.createproject(view, this);
    }
    
    /**
     * 
     * @param view
     */
    public final void editor(View view) {
        Response.editor(view, validrepo, project);
    }
    
    public final void editscreen(View view) {
        Request.editscreen(view, project);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractPage#init(
     *     org.iocaste.shell.common.View)
     */
    @Override
    public final void init(View view) {
        File file;
        
        repository = new GlobalConfig(this).get("repository");
        file = new File(repository);
        
        if (file.isDirectory()) {
            validrepo = true;
            return;
        }
        
        validrepo = file.mkdir();
    }
    
    /**
     * @param message
     * @return
     */
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    /**
     * @param view
     */
    public final void main(View view) {
        Response.main(view);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void save(View view) throws Exception {
        Request.save(view, repository, project);
        view.message(Const.STATUS, "project.saved");
    }
    
    /**
     * 
     * @param view
     */
    public final void screeneditor(View view) {
        Response.screeneditor(view, project);
    }
}
