package org.iocaste.authority;

import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.Authorization;

public class Services extends AbstractFunction {
    private static final byte SELECT_PROFILE = 0;
    private static final byte AUTH_ITENS = 1;
    private static final String[] QUERIES = {
        "select * from USER_PROFILE where USERNAME = ? and PROFILE = ?",
        "select * from AUTHORIZATION_ITEM where AUTHORIZATION = ?"
    };
    
    public Services() {
        export("assign", "assign");
        export("get", "get");
        export("remove", "remove");
        export("save", "save");
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void assign(Message message) throws Exception {
        Map<String, String> parameters;
        ExtendedObject paramobj;
        long itemid;
        Authorization authorization = message.get("authorization");
        String username = message.getString("username");
        String profilename = message.getString("profile");
        Documents documents = new Documents(this);
        ExtendedObject[] profileobj = documents.selectLimitedTo(
                QUERIES[SELECT_PROFILE], 1, username, profilename);
        int profileid = profileobj[0].getValue("ID");
        long lastauthid = profileobj[0].getValue("CURRENT");
        DocumentModel model = documents.getModel("USER_AUTHORITY");
        ExtendedObject userauth = new ExtendedObject(model);
        
        lastauthid++;
        userauth.setValue("ID", lastauthid);
        userauth.setValue("PROFILE", profileid);
        userauth.setValue("NAME", authorization.getName());
        documents.save(userauth);
        
        model = documents.getModel("USER_AUTHORITY_ITEM");
        parameters = authorization.getParameters();
        itemid = lastauthid * 1000;
        for (String key : parameters.keySet()) {
            itemid++;
            paramobj = new ExtendedObject(model);
            paramobj.setValue("ID", itemid);
            paramobj.setValue("AUTHORIZATION", lastauthid);
            paramobj.setValue("NAME", key);
            paramobj.setValue("VALUE", parameters.get(key));
            
            documents.save(paramobj);
        }
        
        profileobj[0].setValue("CURRENT", lastauthid);
        documents.modify(profileobj[0]);
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final Authorization get(Message message) throws Exception {
        Authorization authorization;
        ExtendedObject[] parameters;
        String name = message.getString("name");
        Documents documents = new Documents(this);
        ExtendedObject authobject = documents.getObject("AUTHORIZATION", name);
        
        if (authobject == null)
            return null;
        
        name = authobject.getValue("NAME");
        authorization = new Authorization(name);
        authorization.setObject((String)authobject.getValue("OBJECT"));
        authorization.setAction((String)authobject.getValue("ACTION"));
        
        parameters = documents.select(QUERIES[AUTH_ITENS], name);
        for (ExtendedObject parameter : parameters) {
            name = parameter.getValue("NAME");
            authorization.add(name, null);
        }
        
        return authorization;
    }
    
    public final void remove(Message message) {
        
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void save(Message message) throws Exception {
        ExtendedObject authobject;
        long ident, itemid;
        Map<String, String> parameters;
        Authorization authorization = message.get("authorization");
        Documents documents = new Documents(this);
        DocumentModel model = documents.getModel("AUTHORIZATION");
        DocumentModel modelitem = documents.getModel("AUTHORIZATION_ITEM");
        String name = authorization.getName();
        
        ident = documents.getNextNumber("AUTHINDEX");
        authobject = new ExtendedObject(model);
        authobject.setValue("NAME", name);
        authobject.setValue("OBJECT", authorization.getObject());
        authobject.setValue("ACTION", authorization.getAction());
        authobject.setValue("INDEX", ident);
        
        documents.save(authobject);
        
        itemid = 1000 * ident;
        parameters = authorization.getParameters();
        for (String key : parameters.keySet()) {
            itemid++;
            authobject = new ExtendedObject(modelitem);
            authobject.setValue("ID", itemid);
            authobject.setValue("AUTHORIZATION", name);
            authobject.setValue("NAME", key);
            
            documents.save(authobject);
        }
    }
}
