package org.iocaste.login;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.ViewData;

public class LoginForm extends AbstractPage {
    
    /**
     * 
     * @param view
     */
    public final void authentic(ViewData vdata) throws Exception {
        Container form = new Form(null, "main");
        DataForm loginform = new DataForm(form, "login");
        
        /*
         * não podemos utilizar getModel() de Documents aqui para recuperar
         * o modelo "login". ainda não estamos autenticados pelo iocaste.
         */
        loginform.importModel(modelInstance());
        loginform.addAction("connect");
        
        for (Element element : loginform.getElements())
            if (element.isDataStorable())
                ((InputComponent)element).setObligatory(true);
                
        vdata.setMessages(new MessageSource("/META-INF/message.properties"));
        vdata.setTitle("authentic");
        vdata.addContainer(form);
        vdata.setFocus("login.username");
        
        ((DataItem)vdata.getElement("login.secret")).setSecret(true);
    }
    
    /**
     * 
     * @param controldata
     * @param view
     * @throws Exception
     */
    public final void connect(ControlData controldata, ViewData view)
            throws Exception {
        DataForm form = (DataForm)view.getElement("login");
        Iocaste iocaste = new Iocaste(this);
        Login login = form.getObject().newInstance();
        
        if (iocaste.login(login.getUsername(), login.getSecret())) {
            controldata.setReloadableView(true);
            controldata.redirect("iocaste-tasksel", "main");
        } else {
            controldata.message(Const.ERROR, "invalid.login");
        }
    }
    
    /**
     * 
     * @param name
     * @param datatype
     * @param length
     * @return
     */
    private final DataElement dataElementInstance(
            String name, int datatype, int length) {
        DataElement dataelement = new DataElement();
        
        dataelement.setName(name);
        dataelement.setType(datatype);
        dataelement.setLength(length);
        dataelement.setDecimals(0);
        
        return dataelement;
    }
    
    /**
     * 
     * @return
     */
    private final DocumentModel modelInstance() {
        DocumentModelItem item;
        DocumentModel model = new DocumentModel();
        DataElement char12 = dataElementInstance("CHAR12", DataType.CHAR, 12);
        
        model.setName("login");
        model.setClassName(Login.class.getCanonicalName());
        
        item = modelItemInstance(model, "username");
        item.setDataElement(char12);
        
        item = modelItemInstance(model, "secret");
        item.setDataElement(char12);
        
        return model;
    }
    
    /**
     * 
     * @param model
     * @param name
     * @return
     */
    private final DocumentModelItem modelItemInstance(
            DocumentModel model, String name) {
        DocumentModelItem item = new DocumentModelItem();
        
        item.setDocumentModel(model);
        item.setName(name);
        item.setAttributeName(name);
        
        model.add(item);
        
        return item;
    }
}
