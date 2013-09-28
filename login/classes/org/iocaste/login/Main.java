package org.iocaste.login;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.PageContext;
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
    public final void authentic() {
        Response.authentic(context.view);
    }
    
    public final void changesecret() {
        Request.changesecret(context);
    }
    
    public final void changesecretform() {
        Response.changesecretform(context);
    }
    
    public final void connect() {
        Request.connect(context);
    }
    
    @Override
    public final PageContext init(View view) {
        context = new Context();
        
        return context;
    }
}
