package org.iocaste.dataview;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    
    public Main() {
        export("install", "install");
    }
    
    /**
     * 
     * @param vdata
     * @throws Exception
     */
    public final void delete(ViewData vdata) throws Exception {
        Request.delete(vdata, this);
    }
    
    /**
     * 
     * @param vdata
     */
    public final void earlierpage(ViewData vdata) {
        
    }
    
    /**
     * 
     * @param vdata
     * @throws Exception
     */
    public final void edit(ViewData vdata) throws Exception {
        Request.edit(vdata, this);
    }
    
    /**
     * 
     * @param vdata
     * @throws Exception
     */
    public final void form(ViewData vdata) throws Exception {
        Response.form(vdata, this);
    }
    
    /**
     * 
     * @param vdata
     */
    public final void insert(ViewData vdata) {
        Request.insert(vdata);
    }
    
    /**
     * 
     * @param vdata
     * @throws Exception 
     */
    public final void insertcancel(ViewData vdata) throws Exception {
        back(vdata);
    }
    
    /**
     * 
     * @param vdata
     * @throws Exception
     */
    public final void insertitem(ViewData vdata) throws Exception {
        ViewData selectview = getView(vdata, "select");
        
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
    public final void insertnext(ViewData vdata) throws Exception {
        ViewData selectview = getView(vdata, "select");
        
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
    public final void main(ViewData vdata) {
        Response.main(vdata);
    }

    /**
     * 
     * @param vdata
     * @throws Exception
     */
    public final void save(ViewData vdata) throws Exception {
        Request.save(vdata, this);
    }
    
    /**
     * 
     * @param vdata
     * @throws Exception
     */
    public final void select(ViewData vdata) throws Exception {
        Response.select(vdata, this);
    }
    
    /**
     * 
     * @param vdata
     * @throws Exception
     */
    public final void show(ViewData vdata) throws Exception {
        Request.show(vdata, this);
    }
}
