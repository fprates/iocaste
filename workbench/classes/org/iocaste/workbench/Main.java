package org.iocaste.workbench;

import java.io.File;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
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
    private Context context;
    
    public Main() {
        context = new Context();
        context.function = this;
        export("install", "install");
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void activate(View view) throws Exception {
        context.view = view;
        context.path = getRealPath("");
        Common.updateCurrentSource(context);
        Activation.start(context);
    }
    
    /**
     * 
     * @param view
     */
    public final void addscreen(View view) {
        context.view = view;
        Request.addscreen(context);
    }
    
    /**
     * 
     * @param view
     */
    public final void createproject(View view) {
        context.view = view;
        Request.createproject(context);
    }
    
    /**
     * 
     * @param view
     */
    public final void editor(View view) {
        context.view = view;
        Response.editor(context);
    }
    
    /**
     * 
     * @param view
     */
    public final void editscreen(View view) {
        context.view = view;
        Request.editscreen(context);
    }
    
    /**
     * 
     * @param view
     */
    public final void editsource(View view) {
//        context.view = view;
//        Request.editsource(context);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractPage#init(
     *     org.iocaste.shell.common.View)
     */
    @Override
    public final void init(View view) {
        File file;
        Documents documents;
        
        context.repository = new GlobalConfig(this).get("repository");
        
        file = new File(context.repository);
        context.validrepo = (file.isDirectory())? true : file.mkdir();
        
        documents = new Documents(this);
        context.editorhdrmodel = documents.getModel("ICSTPRJ_EDITOR_HEADER");
        context.projectmodel = documents.getModel("ICSTPRJ_HEADER");
        context.projectnamemodel = documents.getModel("ICSTPRJ_PROJECT_NAMES");
        context.packagemodel = documents.getModel("ICSTPRJ_PACKAGES");
        context.sourcemodel = documents.getModel("ICSTPRJ_SOURCES");
        context.srccodemodel = documents.getModel("ICSTPRJ_SRCCODE");

        if (context.project == null) {
            context.project = new Project();
            context.project.header = new ExtendedObject(context.editorhdrmodel);
        }
    }
    
    /**
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
    public final void loadproject(View view) {
        context.view = view;
        Request.loadproject(context);
    }
    
    /**
     * @param view
     */
    public final void main(View view) {
        context.view = view;
        Response.main(context);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void save(View view) throws Exception {
        context.view = view;
        Common.updateCurrentSource(context);
        Request.save(context);
        view.message(Const.STATUS, "project.saved");
    }
    
    /**
     * 
     * @param view
     */
    public final void screeneditor(View view) {
        context.view = view;
        Response.screeneditor(context);
    }
}
