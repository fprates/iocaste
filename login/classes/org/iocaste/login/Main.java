package org.iocaste.login;

import java.util.Properties;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.packagetool.common.PackageTool;
import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private static final String[] PACKAGES = new String[] {
            "iocaste-packagetool",
            "iocaste-tasksel",
            "iocaste-setup",
            "iocaste-search-help"
    };
    
    /**
     * 
     * @param view
     */
    public final void authentic(View vdata) {
        String name;
        InputComponent input;
        MessageSource messages;
        Form form = new Form(vdata, "main");
        new PageControl(form);
        DataForm loginform = new DataForm(form, "login");
        
        /*
         * não podemos utilizar getModel() de Documents aqui para recuperar
         * o modelo "login". ainda não estamos autenticados pelo iocaste.
         */
        loginform.importModel(modelInstance());
        new Button(form, "connect").setSubmit(true);
        
        for (Element element : loginform.getElements()) {
            if (!element.isDataStorable())
                continue;
            
            input = (InputComponent)element;
            name = input.getName();
            if (name.equals("LOCALE")) {
                input.set("pt_BR");
                input.setObligatory(false);
                continue;
            }
            
            if (name.equals("USERNAME"))
                vdata.setFocus(element);
            
            input.setObligatory(true);
        }
        
        messages = new MessageSource();
        messages.setMessages(getMessages());
        
        vdata.setMessages(messages);
        vdata.setTitle("authentic");
        
        loginform.get("SECRET").setSecret(true);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void connect(View view) throws Exception {
        String username;
        PackageTool pkgtool = new PackageTool(this);
        DataForm form = view.getElement("login");
        Iocaste iocaste = new Iocaste(this);
        Login login = form.getObject().newInstance();
        
        view.clearExports();
        username = login.getUsername();
        if (iocaste.login(username, login.getSecret(), login.getLocale())) {
            pkgtool = new PackageTool(this);
            
            for (String pkgname : PACKAGES)
                if (!pkgtool.isInstalled(pkgname))
                    pkgtool.install(pkgname);
            
            view.export("username", username);
            view.setReloadableView(true);
            view.redirect("iocaste-tasksel", "main");
        } else {
            view.message(Const.ERROR, "invalid.login");
        }

        form.get("USERNAME").set(null);
        form.get("SECRET").set(null);
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
    private final Properties getMessages() {
        Properties messages = new Properties();
        
        messages.put("authentic", "Autenticação");
        messages.put("connect", "Conectar");
        messages.put("invalid.login", "Usuário ou senha inválidos.");
        messages.put("SECRET", "Senha");
        messages.put("USERNAME", "Usuário");
        messages.put("LOCALE", "Idioma");
        
        return messages;
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
        
        item = modelItemInstance(model, "locale", 2);
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
        DocumentModelItem item = new DocumentModelItem();
        
        item.setDocumentModel(model);
        item.setName(name);
        item.setAttributeName(name);
        item.setIndex(index);
        
        model.add(item);
        
        return item;
    }
}
