package org.iocaste.datadict;

import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.HtmlTag;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    
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
        Add.shitem(view);
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
     * @param vdata
     * @throws Exception
     */
    public final void delete(ViewData vdata) throws Exception {
        Delete.main(vdata, this);
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
    public final void fkupdate(ViewData view) throws Exception {
        if (ForeignKey.updateReference(view, this))
            back(view);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void fkstructure(ViewData view) throws Exception {
        ForeignKey.main(view, this);
    }
    
    /**
     * 
     * @param view
     */
    public final void foreignkey(ViewData view) {
        ForeignKey.update(view);
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
     * @param vdata
     */
    public final void list(ViewData vdata) {
        Container container = new StandardContainer(null, "list");
        String[] lines = (String[])vdata.getParameter("code");
        HtmlTag code = new HtmlTag(container, "code");
        
        code.setLines(lines);
        
        vdata.addContainer(container);
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
    public final void saveshitem(ViewData view) throws Exception {
        Save.shitem(view, this);
    }
    
    /**
     * 
     * @param vdata
     * @throws Exception 
     */
    public final void show(ViewData vdata) throws Exception {
        Selection.show(vdata, this);
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
     * @param vdata
     * @throws Exception
     */
    public final void update(ViewData vdata) throws Exception {
        Selection.update(vdata, this);
    }
}