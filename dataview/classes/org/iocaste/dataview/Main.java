package org.iocaste.dataview;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    
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
     * @param vdata
     */
    public final void earlierpage(View vdata) {
        
    }
    
    /**
     * 
     * @param vdata
     */
    public final void edit(View vdata) {
        Request.load(vdata, this, Common.UPDATE);
    }
    
    /**
     * 
     * @param vdata
     */
    public final void form(View vdata) {
        Response.form(vdata, this);
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
        return Install.self();
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
        Request.save(vdata, this);
    }
    
    /**
     * 
     * @param vdata
     */
    public final void select(View vdata) {
        Response.select(vdata, this);
    }
    
    /**
     * 
     * @param vdata
     */
    public final void show(View vdata) {
        Request.load(vdata, this, Common.DISPLAY);
    }
}
