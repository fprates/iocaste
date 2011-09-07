package org.iocaste.login;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Component;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataFormItem;
import org.iocaste.shell.common.Form;
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
        loginform.setModel(modelInstance());
        loginform.addAction("connect");
        
        vdata.setMessages(new MessageSource("/META-INF/message.properties"));
        vdata.setTitle("authentic");
        vdata.addContainer(form);
        
        vdata.setFocus((Component)vdata.getElement("login.username"));
        ((DataFormItem)vdata.getElement("login.secret")).setSecret(true);
    }
    
    /**
     * 
     * @param controldata
     * @param view
     * @throws Exception
     */
    public final void connect(ControlData controldata, ViewData view) throws Exception {
        DataForm form = (DataForm)view.getElement("login");
        Iocaste iocaste = new Iocaste(this);
        Login login = new Login();
        
        form.exportTo(login);
        
        if (iocaste.login(login.getUsername(), login.getSecret()))
            controldata.redirect("iocaste-tasksel", "main");
        else
            controldata.message(Const.ERROR, "invalid.login");
    }
    
    /**
     * 
     * @return
     */
    private final DocumentModel modelInstance() {
        DocumentModel model = new DocumentModel();
        
        model.setName("login");
        modelItemInstance(model, "username");
        modelItemInstance(model, "secret");
        
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
        model.add(item);
        
        return item;
    }
}
