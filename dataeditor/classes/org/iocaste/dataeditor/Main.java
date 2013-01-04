package org.iocaste.dataeditor;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private ExtendedObject[] itens;
    private DocumentModel model;
    private Const viewtype;
    
    public Main() {
        export("install", "install");
    }
    
    /**
     * 
     * @param vdata
     */
    public final void delete(View vdata) {
        Request.delete(vdata, this);
    }
    
    /**
     * 
     * @param view
     */
    public final void edit(View view) {
        Documents documents = new Documents(this);
        
        model = Common.getModelFromView(view, documents);
        if (model == null) {
            view.message(Const.ERROR, "invalid.model");
            return;
        }
        
        viewtype = Const.SINGLE;
        itens = Request.load(model.getName(), documents);
        view.redirect("select");
    }
    
    /**
     * 
     * @param vdata
     */
    public final void form(View vdata) {
        Response.form(vdata, model);
    }
    
    /**
     * 
     * @param vdata
     */
    public final void insert(View vdata) {
        Request.insert(vdata);
    }
    
    /**
     * 
     * @param vdata
     */
    public final void insertcancel(View vdata) {
        back(vdata);
    }
    
    /**
     * 
     * @param vdata
     */
    public final void insertitem(View vdata) {
        View selectview = getView(vdata, "select");
        
        Request.insertcommon(vdata, selectview, this);
        updateView(selectview);
        vdata.message(Const.STATUS, "insert.successful");
        back(vdata);
    }
    
    /**
     * 
     * @param vdata
     */
    public final void insertnext(View vdata) {
        View selectview = getView(vdata, "select");
        
        Request.insertnext(vdata, selectview, this);
        updateView(selectview);
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
     * @param vdata
     */
    public final void main(View vdata) {
        Response.main(vdata, this);
    }

    /**
     * 
     * @param vdata
     */
    public final void save(View vdata) {
        Request.save(vdata, model, this);
    }
    
    /**
     * 
     * @param vdata
     */
    public final void select(View view) {
        Response.select(view, this, model, itens, viewtype);
    }
}
