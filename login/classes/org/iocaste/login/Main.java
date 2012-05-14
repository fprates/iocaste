package org.iocaste.login;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.packagetool.common.PackageTool;
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
        InputComponent input;
        MessageSource messages;
        Container form = new Form(vdata, "main");
        DataForm loginform = new DataForm(form, "login");
        
        /*
         * não podemos utilizar getModel() de Documents aqui para recuperar
         * o modelo "login". ainda não estamos autenticados pelo iocaste.
         */
        loginform.importModel(modelInstance());
        new Button(form, "connect");
        
        for (Element element : loginform.getElements()) {
            if (!element.isDataStorable())
                continue;
            
            input = (InputComponent)element;
            
            if (input.getName().equals("LOCALE")) {
                input.set("pt_BR");
                input.setObligatory(false);
                continue;
            }
            
            input.setObligatory(true);
        }
        
        messages = new MessageSource();
        messages.loadFromFile("/META-INF/message.properties");
        
        vdata.setMessages(messages);
        vdata.setTitle("authentic");
        vdata.setFocus("USERNAME");
        
        ((DataItem)vdata.getElement("SECRET")).setSecret(true);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void connect(ViewData view) throws Exception {
        PackageTool pkgtool = new PackageTool(this);
        DataForm form = view.getElement("login");
        Iocaste iocaste = new Iocaste(this);
        Login login = form.getObject().newInstance();
        String[] packages = new String[] {
                "iocaste-tasksel",
                "iocaste-packagetool"
        };
        
        if (iocaste.login(login.getUsername(), login.getSecret(),
                login.getLocale())) {
            pkgtool = new PackageTool(this);
            
            for (String pkgname : packages)
                if (!pkgtool.isInstalled(pkgname))
                    pkgtool.install(pkgname);
            
            view.setReloadableView(true);
            view.redirect("iocaste-tasksel", "main");
        } else {
            view.message(Const.ERROR, "invalid.login");
        }

        ((DataItem)view.getElement("USERNAME")).set(null);
        ((DataItem)view.getElement("SECRET")).set(null);
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
