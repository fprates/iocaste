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
        "select * from USER_AUTHORITY where USERNAME = ? and PROFILE = ?",
        "select * from AUTHORIZATION_ITEM where AUTHORIZATION = ?"
    };
    
    public Services() {
        export("assign_authorization", "assignAuthorization");
        export("assign_profile", "assignProfile");
        export("get", "get");
        export("remove", "remove");
        export("save", "save");
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void assignAuthorization(Message message) throws Exception {
        DocumentModel model;
        ExtendedObject profileitem;
        int itemid;
        Authorization authorization = message.get("authorization");
        String username = message.getString("username");
        String profilename = message.getString("profile");
        Documents documents = new Documents(this);
        ExtendedObject[] profiles = documents.selectLimitedTo(
                QUERIES[SELECT_PROFILE], 1, username, profilename);
        
        if (profiles == null)
            throw new Exception(new StringBuilder(profilename).
                    append(" for ").
                    append(username).
                    append(" is an invalid profile.").toString());
        
        profiles[0] = documents.getObject("USER_PROFILE", profilename);
        itemid = profiles[0].getValue("CURRENT");
        itemid++;
        
        model = documents.getModel("USER_PROFILE_ITEM");
        profileitem = new ExtendedObject(model);
        profileitem.setValue("ID", itemid);
        profileitem.setValue("PROFILE", profilename);
        profileitem.setValue("NAME", authorization.getName());
        profileitem.setValue("OBJECT", authorization.getObject());
        profileitem.setValue("ACTION", authorization.getAction());
        documents.save(profileitem);
        
        profiles[0].setValue("CURRENT", itemid);
        documents.modify(profiles[0]);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void assignProfile(Message message) throws Exception {
        int userid, id, profileid;
        DocumentModel model;
        String username = message.getString("username");
        String profile = message.getString("profile");
        Documents documents = new Documents(this);
        ExtendedObject object = documents.getObject("LOGIN", username);
        
        if (object == null)
            throw new Exception("Invalid user.");
        
        userid = object.getValue("ID");
        
        object = documents.getObject("USER_PROFILE", profile);
        if (object == null)
            throw new Exception("Invalid profile.");
        
        profileid = object.getValue("ID");
        id = (userid * 100) + profileid;

        model = documents.getModel("USER_AUTHORITY");
        object = new ExtendedObject(model);
        object.setValue("ID", id);
        object.setValue("USERNAME", username);
        object.setValue("PROFILE", profile);
        
        documents.save(object);
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
        if (parameters != null)
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
        
        itemid = 100 * ident;
        parameters = authorization.getParameters();
        for (String key : parameters.keySet()) {
            itemid++;
            authobject = new ExtendedObject(modelitem);
            authobject.setValue("ID", itemid);
            authobject.setValue("AUTHORIZATION", name);
            authobject.setValue("NAME", key);
            authobject.setValue("VALUE", parameters.get(key));
            
            documents.save(authobject);
        }
    }
}
