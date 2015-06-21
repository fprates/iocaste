package org.iocaste.login;

import java.util.Properties;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;

public class Context implements ExtendedContext {
    private DataElement username, secret;
    public DocumentModel loginmodel, chgscrtmodel;
    public Properties messages;
    public String uname;
    
    public Context() {
        username = dataElementInstance(
                "USERNAME", DataType.CHAR, 12, DataType.UPPERCASE);
        secret = dataElementInstance(
                "SECRET", DataType.CHAR, 12, DataType.KEEPCASE);
        loginmodel = loginmodel();
        chgscrtmodel = chgscrtmodel();
        
        messages = new Properties();
        messages.put("authentic", "Autenticação");
        messages.put("changesecret", "Alterar");
        messages.put("CONFIRM", "Repita a senha");
        messages.put("connect", "Conectar");
        messages.put("invalid.login", "Usuário ou senha inválidos.");
        messages.put("LOCALE", "Idioma");
        messages.put("password.change", "Alteração de senha");
        messages.put("password.mismatch", "Senhas não são iguais.");
        messages.put("SECRET", "Senha");
        messages.put("USERNAME", "Usuário");
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
