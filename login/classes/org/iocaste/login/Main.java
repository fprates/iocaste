package org.iocaste.login;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private Context context;
    
    public Main() {
        DocumentModelItem item;
        DataElement element;

        element = new DataElement("SECRET");
        element.setType(DataType.CHAR);
        element.setLength(12);
        element.setUpcase(false);
        
        context = new Context();
        context.function = this;
        context.chgscrtmodel = new DocumentModel("CHANGE_SECRET");
        
        item = new DocumentModelItem("SECRET");
        item.setDataElement(element);
        context.chgscrtmodel.add(item);
        
        item = new DocumentModelItem("CONFIRM");
        item.setDataElement(element);
        context.chgscrtmodel.add(item);
    }
    /**
     * 
     * @param view
     */
    public final void authentic(View view) {
        Response.authentic(view);
    }
    
    public final void changesecret(View view) {
        Request.changesecret(view, context);
    }
    
    public final void changesecretform(View view) {
        Response.changesecretform(view, context);
    }
    
    /**
     * 
     * @param view
     */
    public final void connect(View view) {
        Request.connect(view, this);
    }
}
