package org.iocaste.workbench;

import java.io.File;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.globalconfig.common.GlobalConfig;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.PageContext;
import org.iocaste.shell.common.View;
import org.iocaste.workbench.editor.Editor;
import org.iocaste.workbench.project.Project;
import org.iocaste.workbench.project.ProjectData;

/**
 * 
 * @author francisco.prates
 *
 */
public class Main extends AbstractPage {
    private Context context;
    
    public Main() {
        export("install", "install");
    }
    
    public final void activate() throws Exception {
        context.path = getRealPath("");
        Common.updateCurrentSource(context);
        Activation.start(context);
    }
    
    public final void addscreen() {
        Request.addscreen(context);
    }
    
    public final void createdefaultpackage() {
        Project.createDefaultPackage(context);
    }
    
    public final void createproject() {
        Project.create(context);
    }
    
    public final void defaultpackage() {
        Project.defaultPackage(context);
    }
    
    public final void editor() {
        Editor.render(context);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractPage#init(
     *     org.iocaste.shell.common.View)
     */
    @Override
    public final PageContext init(View view) {
        File file;
        Documents documents;

        context = new Context();
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
        context.installmodel = documents.getModel("ICSTPRJ_INSTALL");

        if (context.project == null) {
            context.project = new ProjectData();
            context.project.header = new ExtendedObject(context.editorhdrmodel);
        }
        
        return context;
    }
    
    /**
     * @param message
     * @return
     */
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    public final void loadproject() {
        Project.load(context);
    }
    
    public final void main() {
        Project.select(context);
    }
    
    public final void save() throws Exception {
        Common.updateCurrentSource(context);
        Editor.save(context);
        context.view.message(Const.STATUS, "project.saved");
    }
}
