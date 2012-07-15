package org.iocaste.datadict;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.HtmlTag;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    public Main() {
        export("install", "install");
    }
    
    /**
     * 
     * @param vdata
     */
    public final void add(View vdata) {
        Add.main(vdata, this);
    }
    
    /**
     * 
     * @param view
     */
    public final void addshitem(View view) {
        SHStructure.insert(view);
    }
    
    /**
     * 
     * @param view
     */
    public final void create(View view) {
        Selection.create(view, this);
    }
    
    /**
     * 
     * @param view
     */
    public final void delete(View view) {
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
    public final void deleteitem(View vdata) {
        Table itens = vdata.getElement("itens");
        
        for (TableItem item : itens.getItens())
            if (item.isSelected())
                itens.remove(item);
    }
    
    /**
     * 
     * @param view
     */
    public final void deleteshitem(View view) {
        Delete.shitem(view);
    }
    
    /**
     * 
     * @param view
     */
    public final void detailsupdate(View view) {
        if (ItemDetails.update(view, this))
            back(view);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void detailsview(View view) {
        ItemDetails.main(view, this);
    }
    
    /**
     * 
     * @param vdata
     */
    public final void generateclass(View vdata) {
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
    public final void itemdetails(View view) {
        ItemDetails.select(view);
    }
    
    /**
     * 
     * @param vdata
     */
    public final void list(View view) {
        Form container = new Form(view, "list");
        PageControl pagecontrol = new PageControl(container);
        String[] lines = view.getParameter("code");
        HtmlTag code = new HtmlTag(container, "code");
        
        pagecontrol.add("back");
        code.setLines(lines);
    }
    
    /**
     * 
     * @param view
     */
    public final void main(View view) {
        Selection.main(view, this);
    }
    
    /**
     * 
     * @param view
     */
    public final void rename(View view) {
        Rename.main(view);
    }
    
    /**
     * 
     * @param view
     */
    public final void renamedialog(View view) {
        Rename.dialog(view, this);
    }
    
    /**
     * 
     * @param view
     */
    public final void renameok(View view) {
        Rename.ok(view, this);
    }
    
    /**
     * 
     * @param vdata
     */
    public final void save(View view) {
        Save.main(view, this);
    }
    
    /**
     * 
     * @param view
     */
    public final void savesh(View view) {
        SHStructure.save(view, this);
    }
    
    /**
     * 
     * @param vdata 
     */
    public final void show(View view) {
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
     */
    public final void shstructure(View vdata) {
        SHStructure.main(vdata, this);
    }
    
    /**
     * 
     * @param vdata
     */
    public final void tbstructure(View vdata) {
        TableStructure.main(vdata, this);
    }
    
    /**
     * 
     * @param view
     */
    public final void update(View view) {
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