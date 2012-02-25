package org.iocaste.datadict;

import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.HtmlTag;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.ViewData;
import org.iocaste.transport.common.Order;
import org.iocaste.transport.common.Transport;

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
     * @throws Exception
     */
    public final void create(ViewData view) throws Exception {
        Create.main(view, this);
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
        Table itens = (Table)vdata.getElement("itens");
        
        for (TableItem item : itens.getItens())
            if (item.isSelected())
                itens.remove(item);
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
     * @param vdata
     * @throws Exception
     */
    public final void save(ViewData view) throws Exception {
        Save.main(view, this);
    }
    
    /**
     * 
     * @param vdata
     * @throws Exception 
     */
    public final void show(ViewData vdata) throws Exception {
        Show.main(vdata, this);
    }
    
    /**
     * 
     * @param vdata
     */
    public final void structure(ViewData vdata) throws Exception {
        Structure.main(vdata, this);
    }
    
    /**
     * 
     * @param vdata
     */
    public final void transport(ViewData vdata) throws Exception {
        Transport transport = new Transport(this);
        Order order = new Order();
        
        transport.save(order);
      
        vdata.message(Const.STATUS, "object.transport.successful");
    }
    
    /**
     * 
     * @param vdata
     * @throws Exception
     */
    public final void update(ViewData vdata) throws Exception {
        Update.main(vdata, this);
    }
}