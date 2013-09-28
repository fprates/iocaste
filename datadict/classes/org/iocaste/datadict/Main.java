package org.iocaste.datadict;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.HtmlTag;
import org.iocaste.shell.common.PageContext;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private Context context;
    
    public Main() {
        export("install", "install");
    }
    
    public final void add() {
        Add.main(context);
    }
    
    public final void addshitem() {
        SHStructure.insert(context);
    }
    
    public final void create() {
        Selection.create(context);
    }
    
    public final void delete() {
        int op = Common.getTpObjectValue(context.view);
        
        switch (op) {
        case Common.TABLE:
            Selection.deletetb(context);
            break;
        case Common.SH:
            Selection.deletesh(context);
            break;
        }
    }
    
    public final void deleteitem() {
        Table itens = context.view.getElement("itens");
        
        for (TableItem item : itens.getItems())
            if (item.isSelected())
                itens.remove(item);
    }
    
    public final void deleteshitem() {
        Delete.shitem(context.view);
    }
    
    public final void detailsupdate() {
        if (ItemDetails.update(context))
            back();
    }
    
    public final void detailsview() {
        ItemDetails.main(context);
    }
    
    public final void generateclass() {
        CodeGeneration.main(context);
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final InstallData install(Message message) {
        return Install.self();
    }
    
    @Override
    public final PageContext init(View view) {
        context = new Context();
        
        return context;
    }
    
    public final void itemdetails() {
        ItemDetails.select(context);
    }
    
    public final void list() {
        Form container = new Form(context.view, "list");
        PageControl pagecontrol = new PageControl(container);
        HtmlTag codelist = new HtmlTag(container, "codelist");
        
        pagecontrol.add("back");
        codelist.setLines(context.code.toArray(new String[0]));
    }
    
    /**
     * 
     */
    public final void main() {
        Selection.main(context);
    }
    
    public final void rename() {
        Rename.main(context);
    }
    
    public final void renamedialog() {
        Rename.dialog(context);
    }
    
    public final void renameok() {
        Rename.ok(context);
    }
    
    public final void save() {
        Save.main(context);
    }
    
    public final void savesh() {
        SHStructure.save(context);
    }
    
    public final void show() {
        int op = Common.getTpObjectValue(context.view);
        
        switch (op) {
        case Common.TABLE:
            Selection.readtb(context);
            break;
        case Common.SH:
            Selection.readsh(context);
            break;
        }
        
        context.mode = Common.SHOW;
    }
    
    public final void shstructure() {
        SHStructure.main(context);
    }
    
    public final void tbstructure() {
        TableStructure.main(context);
    }
    
    /**
     * 
     * @param view
     */
    public final void update() {
        int op = Common.getTpObjectValue(context.view);
        
        switch (op) {
        case Common.TABLE:
            Selection.readtb(context);
            break;
        case Common.SH:
            Selection.readsh(context);
            break;
        }
        
        context.mode = Common.UPDATE;
    }
}