package org.iocaste.datadict;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.HtmlTag;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    public Main() {
        export("install", "install");
    }
    
    /**
     * 
     * @param vdata
     */
    public final void add(ViewData vdata) throws Exception {
        Add.main(vdata, this);
    }
    
    /**
     * 
     * @param view
     */
    public final void addshitem(ViewData view) {
        SHStructure.insert(view);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void create(ViewData view) throws Exception {
        Selection.create(view, this);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void delete(ViewData view) throws Exception {
        int op = Common.getTpObjectValue(view);
        
        switch (op) {
        case Common.TABLE:
            Selection.deletetb(view, this);
            break;
        case Common.SH:
            Selection.deletesh(view, this);
            break;
        }
    }
    
    /**
     * 
     * @param vdata
     */
    public final void deleteitem(ViewData vdata) {
        Table itens = vdata.getElement("itens");
        
        for (TableItem item : itens.getItens())
            if (item.isSelected())
                itens.remove(item);
    }
    
    /**
     * 
     * @param view
     */
    public final void deleteshitem(ViewData view) {
        Delete.shitem(view);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void detailsupdate(ViewData view) throws Exception {
        if (ItemDetails.update(view, this))
            back(view);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void detailsview(ViewData view) throws Exception {
        ItemDetails.main(view, this);
    }
    
    /**
     * 
     * @param vdata
     */
    public final void generateclass(ViewData vdata) {
        CodeGeneration.main(vdata);
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
     * @param view
     */
    public final void itemdetails(ViewData view) {
        ItemDetails.select(view);
    }
    
    /**
     * 
     * @param vdata
     */
    public final void list(ViewData vdata) {
        Container container = new StandardContainer(vdata, "list");
        String[] lines = vdata.getParameter("code");
        HtmlTag code = new HtmlTag(container, "code");
        
        code.setLines(lines);
        
        vdata.setNavbarActionEnabled("back", true);
    }
    
    /**
     * 
     * @param view
     */
    public final void main(ViewData view) throws Exception {
        Selection.main(view, this);
    }
    
    /**
     * 
     * @param view
     */
    public final void rename(ViewData view) {
        Rename.main(view);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void renamedialog(ViewData view) throws Exception {
        Rename.dialog(view, this);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void renameok(ViewData view) throws Exception {
        Rename.ok(view, this);
    }
    
    /**
     * 
     * @param vdata
     * @throws Exception
     */
    public final void save(ViewData view) throws Exception {
        Save.main(view, this);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void savesh(ViewData view) throws Exception {
        SHStructure.save(view, this);
    }
    
    /**
     * 
     * @param vdata
     * @throws Exception 
     */
    public final void show(ViewData view) throws Exception {
        int op = Common.getTpObjectValue(view);
        
        switch (op) {
        case Common.TABLE:
            Selection.readtb(view, this);
            break;
        case Common.SH:
            Selection.readsh(view, this);
            break;
        }
        
        view.export("mode", Common.SHOW);
    }
    
    /**
     * 
     * @param vdata
     * @throws Exception
     */
    public final void shstructure(ViewData vdata) throws Exception {
        SHStructure.main(vdata, this);
    }
    
    /**
     * 
     * @param vdata
     */
    public final void tbstructure(ViewData vdata) throws Exception {
        TableStructure.main(vdata, this);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void update(ViewData view) throws Exception {
        int op = Common.getTpObjectValue(view);
        
        switch (op) {
        case Common.TABLE:
            Selection.readtb(view, this);
            break;
        case Common.SH:
            Selection.readsh(view, this);
            break;
        }
        
        view.export("mode", Common.UPDATE);
    }
}