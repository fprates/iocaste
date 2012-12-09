package org.iocaste.login;

import java.util.Properties;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.View;

public class Response {

    /**
     * @param args
     */
    public static final void authentic(View view) {
        String name;
        InputComponent input;
        MessageSource messages;
        Form form = new Form(view, "main");
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
                view.setFocus(element);
            
            input.setObligatory(true);
        }
        
        messages = new MessageSource();
        messages.setMessages(getMessages());
        
        view.setMessages(messages);
        view.setTitle("authentic");
        
        loginform.get("SECRET").setSecret(true);
    }
    
    /**
     * 
     * @param name
     * @param datatype
     * @param length
     * @return
     */
    private static final DataElement dataElementInstance(
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
    private static final Properties getMessages() {
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
    private static final DocumentModel modelInstance() {
        DocumentModelItem item;
        DocumentModel model = new DocumentModel("login");
        
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
    private static final DocumentModelItem modelItemInstance(
            DocumentModel model, String name, int index) {
        DocumentModelItem item = new DocumentModelItem(name);
        
        item.setDocumentModel(model);
        item.setAttributeName(name);
        item.setIndex(index);
        
        model.add(item);
        
        return item;
    }

}
