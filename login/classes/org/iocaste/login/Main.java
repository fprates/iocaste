package org.iocaste.login;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    
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
        new Button(form, "connect");
        
        for (Element element : loginform.getElements())
            if (element.isDataStorable())
                ((InputComponent)element).setObligatory(true);
                
        vdata.setMessages(new MessageSource("/META-INF/message.properties"));
        vdata.setTitle("authentic");
        vdata.addContainer(form);
        vdata.setFocus("username");
        
        ((DataItem)vdata.getElement("secret")).setSecret(true);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void connect(ViewData view) throws Exception {
        DataForm form = view.getElement("login");
        Iocaste iocaste = new Iocaste(this);
        Login login = form.getObject().newInstance();
        
        if (iocaste.login(login.getUsername(), login.getSecret())) {
            view.setReloadableView(true);
            view.redirect("iocaste-tasksel", "main");
        } else {
            view.message(Const.ERROR, "invalid.login");
        }

        ((DataItem)view.getElement("username")).setValue("");
        ((DataItem)view.getElement("secret")).setValue("");
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
        DataElement dataelement = new DataElement();
        
        dataelement.setName(name);
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
    private final DocumentModel modelInstance() {
        DocumentModelItem item;
        DocumentModel model = new DocumentModel();
        
        model.setName("login");
        model.setClassName(Login.class.getCanonicalName());
        
        item = modelItemInstance(model, "username", 0);
        item.setDataElement(dataElementInstance(
        		"CHAR12", DataType.CHAR, 12, DataType.UPPERCASE));
        
        item = modelItemInstance(model, "secret", 1);
        item.setDataElement(dataElementInstance(
        		"CHAR12", DataType.CHAR, 12, DataType.KEEPCASE));
        
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
        DocumentModelItem item = new DocumentModelItem();
        
        item.setDocumentModel(model);
        item.setName(name);
        item.setAttributeName(name);
        item.setIndex(index);
        
        model.add(item);
        
        return item;
    }
}
