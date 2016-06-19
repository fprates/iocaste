package org.iocaste.login;

import org.iocaste.appbuilder.common.AbstractExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;

public class Context extends AbstractExtendedContext {
    private DataElement username, secret;
    public DocumentModel loginmodel, chgscrtmodel;
    public String uname;
    public ExtendedObject object;
    
    public Context(PageBuilderContext context) {
        super(context);
        username = dataElementInstance(
                "USERNAME", DataType.CHAR, 12, DataType.UPPERCASE);
        secret = dataElementInstance(
                "SECRET", DataType.CHAR, 12, DataType.KEEPCASE);
        loginmodel = loginmodel();
        chgscrtmodel = chgscrtmodel();
    }

    private final DocumentModel chgscrtmodel() {
        DocumentModelItem item;
        DocumentModel model;
        
        model = new DocumentModel("CHANGE_SECRET");

        item = modelItemInstance(model, "SECRET", 0);
        item.setDataElement(secret);
        
        item = modelItemInstance(model, "CONFIRM", 1);
        item.setDataElement(secret);
        return model;
    }
    
    /**
     * 
     * @param name
     * @param datatype
     * @param length
     * @return
     */
    private final DataElement dataElementInstance(
            String name, int datatype, int length, boolean upcase) {
        DataElement dataelement = new DataElement(name);
        
        dataelement.setType(datatype);
        dataelement.setLength(length);
        dataelement.setDecimals(0);
        dataelement.setUpcase(upcase);
        
        return dataelement;
    }
    
    /**
     * 
     * @return
     */
    private final DocumentModel loginmodel() {
        DocumentModelItem item;
        DocumentModel model = new DocumentModel("LOGIN");
        
        item = modelItemInstance(model, "USERNAME", 0);
        item.setDataElement(username);
        
        item = modelItemInstance(model, "SECRET", 1);
        item.setDataElement(secret);
        
        item = modelItemInstance(model, "LOCALE", 2);
        item.setDataElement(dataElementInstance(
                "CHAR5", DataType.CHAR, 5, DataType.KEEPCASE));
        
        return model;
    }
    
    /**
     * 
     * @param model
     * @param name
     * @return
     */
    private final DocumentModelItem modelItemInstance(
            DocumentModel model, String name, int index) {
        DocumentModelItem item = new DocumentModelItem(name);
        
        item.setDocumentModel(model);
        item.setAttributeName(name);
        item.setIndex(index);
        
        model.add(item);
        
        return item;
    }
}
