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
     * @throws Exception
     */
    public final void delete(View vdata) throws Exception {
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
     * @throws Exception
     */
    public final void edit(View vdata) throws Exception {
        Request.edit(vdata, this);
    }
    
    /**
     * 
     * @param vdata
     * @throws Exception
     */
    public final void form(View vdata) throws Exception {
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
     * @throws Exception 
     */
    public final void insertcancel(View vdata) throws Exception {
        back(vdata);
    }
    
    /**
     * 
     * @param vdata
     * @throws Exception
     */
    public final void insertitem(View vdata) throws Exception {
        View selectview = getView(vdata, "select");
        
        Request.insertcommon(vdata, selectview, this);
        updateView(selectview);
        vdata.message(Const.STATUS, "insert.successful");
        back(vdata);
    }
    
    /**
     * 
     * @param vdata
     * @throws Exception
     */
    public final void insertnext(View vdata) throws Exception {
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
     * @throws Exception
     */
    public final void main(View vdata) throws Exception {
        Response.main(vdata, this);
    }

    /**
     * 
     * @param vdata
     * @throws Exception
     */
    public final void save(View vdata) throws Exception {
        Request.save(vdata, this);
    }
    
    /**
     * 
     * @param vdata
     * @throws Exception
     */
    public final void select(View vdata) throws Exception {
        Response.select(vdata, this);
    }
    
    /**
     * 
     * @param vdata
     * @throws Exception
     */
    public final void show(View vdata) throws Exception {
        Request.show(vdata, this);
    }
}
