package org.iocaste.authority;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.UserProfile;

public class Services extends AbstractFunction {
    private static final byte SELECT_PROFILE = 0;
    private static final byte AUTH_ITENS = 1;
    private static final byte DEL_AUTH_ITENS = 2;
    private static final byte DEL_AUTH = 3;
    private static final byte DEL_PROFILE_ITEM = 4;
    private static final byte PROFILES = 5;
    private static final String[] QUERIES = {
        "select * from USER_AUTHORITY where USERNAME = ? and PROFILE = ?",
        "select * from AUTHORIZATION_ITEM where AUTHORIZATION = ?",
        "delete from AUTHORIZATION_ITEM where AUTHORIZATION = ?",
        "delete from AUTHORIZATION where NAME = ?",
        "delete from USER_PROFILE_ITEM where NAME = ?",
        "from USER_AUTHORITY where USERNAME = ?",
    };
    
    public Services() {
        export("assign_authorization", "assignAuthorization");
        export("assign_profile", "assignProfile");
        export("get", "get");
        export("get_profile", "getProfile");
        export("get_user_profiles", "getUserProfiles");
        export("remove", "remove");
        export("save", "save");
        export("save_profile", "saveProfile");
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
            throw new IocasteException(new StringBuilder(profilename).
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
            throw new IocasteException("Invalid user.");
        
        userid = object.getValue("ID");
        
        object = documents.getObject("USER_PROFILE", profile);
        if (object == null)
            throw new IocasteException("Invalid profile.");
        
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
     */
    public final Authorization get(Message message) {
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
    
    /**
     * 
     * @param message
     * @return
     */
    public final UserProfile getProfile(Message message) {
        UserProfile profile;
        Documents documents = new Documents(this);
        String name = message.getString("name");
        ExtendedObject object = documents.getObject("USER_PROFILE", name);
        
        if (object == null)
            return null;
        
        profile = new UserProfile(name);
        return profile;
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final Set<String> getUserProfiles(Message message) {
        Set<String> names;
        String username = message.getString("username");
        Documents documents = new Documents(this);
        ExtendedObject[] objects = documents.select(QUERIES[PROFILES],
                username);
        
        if (objects == null)
            return null;
        
        names = new TreeSet<String>();
        for (ExtendedObject object : objects)
            names.add((String)object.getValue("PROFILE"));
        
        return names;
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final int remove(Message message) {
        String name = message.getString("name");
        Documents documents = new Documents(this);
        
        documents.update(QUERIES[DEL_PROFILE_ITEM], name);
        documents.update(QUERIES[DEL_AUTH_ITENS], name);
        return documents.update(QUERIES[DEL_AUTH], name);
    }
    
    /**
     * 
     * @param message
     */
    public final void save(Message message) {
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
    
    /**
     * 
     * @param message
     */
    public final void saveProfile(Message message) {
        long profileid;
        UserProfile profile = message.get("profile");
        Documents documents = new Documents(this);
        DocumentModel model = documents.getModel("USER_PROFILE");
        ExtendedObject object = new ExtendedObject(model);
        
        object.setValue("NAME", profile.getName());
        profileid = documents.getNextNumber("PROFILEINDEX");
        object.setValue("ID", profileid);
        object.setValue("CURRENT", profileid * 100);
        documents.save(object);
    }
}
