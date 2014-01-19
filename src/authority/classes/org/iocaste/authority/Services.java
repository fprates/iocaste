package org.iocaste.authority;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.UserProfile;

public class Services extends AbstractFunction {
    
    public Services() {
        export("add_authorization", "addAuthorization");
        export("assign_authorization", "assignAuthorization");
        export("assign_profile", "assignProfile");
        export("get", "get");
        export("get_profile", "getProfile");
        export("get_user_profiles", "getUserProfiles");
        export("remove", "remove");
        export("remove_profile", "removeProfile");
        export("save", "save");
        export("save_profile", "saveProfile");
    }
    
    /**
     * Adds an authorization to a profile.
     * @param message
     */
    public final void addAuthorization(Message message) {
        Authorization authorization = message.get("authorization");
        String profile = message.getString("profile");
        
        addAuthorization(profile, authorization);
    }
    
    /**
     * INTERNAL: adds an authorization to a profile.
     * @param name
     * @param authorization
     */
    private final void addAuthorization(String name,
    		Authorization authorization) {
	    int itemid;
	    DocumentModel model;
	    ExtendedObject profileitem;
        Documents documents = new Documents(this);
        ExtendedObject profile = documents.getObject("USER_PROFILE", name);
        
        if (profile == null)
            throw new RuntimeException(new StringBuilder(name).
                    append(" is an invalid profile.").toString());
        
        itemid = profile.geti("CURRENT") + 1;
        model = documents.getModel("USER_PROFILE_ITEM");
        profileitem = new ExtendedObject(model);
        profileitem.set("ID", itemid);
        profileitem.set("PROFILE", name);
        profileitem.set("NAME", authorization.getName());
        profileitem.set("OBJECT", authorization.getObject());
        profileitem.set("ACTION", authorization.getAction());
        documents.save(profileitem);
        
        profile.set("CURRENT", itemid);
        documents.modify(profile);
    }
    
    /**
     * Adds an authorization to a profile, checking
     * @param message
     * @throws Exception
     */
    public final void assignAuthorization(Message message) throws Exception {
        Query query;
        Authorization authorization;
        String username = message.getString("username");
        String profilename = message.getString("profile");
        Documents documents = new Documents(this);
        ExtendedObject[] profiles;
        
        query = new Query();
        query.setModel("USER_AUTHORITY");
        query.andEqual("USERNAME", username);
        query.andEqual("PROFILE", profilename);
        query.setMaxResults(1);
        profiles = documents.select(query);
        if (profiles == null)
            throw new IocasteException(new StringBuilder(profilename).
                    append(" for ").
                    append(username).
                    append(" is an invalid profile.").toString());
        
        authorization = message.get("authorization");
        addAuthorization(profilename, authorization);
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
        
        userid = object.geti("ID");
        object = documents.getObject("USER_PROFILE", profile);
        if (object == null)
            throw new IocasteException("Invalid profile.");
        
        profileid = object.geti("ID");
        id = (userid * 100) + profileid;

        model = documents.getModel("USER_AUTHORITY");
        object = new ExtendedObject(model);
        object.set("ID", id);
        object.set("USERNAME", username);
        object.set("PROFILE", profile);
        
        documents.save(object);
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final Authorization get(Message message) {
        Query query;
        Authorization authorization;
        ExtendedObject[] parameters;
        String name = message.getString("name");
        Documents documents = new Documents(this);
        ExtendedObject authobject = documents.getObject("AUTHORIZATION", name);
        
        if (authobject == null)
            return null;
        
        name = authobject.get("NAME");
        authorization = new Authorization(name);
        authorization.setObject((String)authobject.get("OBJECT"));
        authorization.setAction((String)authobject.get("ACTION"));

        query = new Query();
        query.setModel("AUTHORIZATION_ITEM");
        query.andEqual("AUTHORIZATION", name);
        parameters = documents.select(query);
        if (parameters != null)
            for (ExtendedObject parameter : parameters) {
                name = parameter.get("NAME");
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
        Query query;
        Set<String> names;
        ExtendedObject[] objects;
        String username = message.getString("username");
        Documents documents = new Documents(this);
        
        query = new Query();
        query.setModel("USER_AUTHORITY");
        query.andEqual("USERNAME", username);
        objects = documents.select(query);
        if (objects == null)
            return null;
        
        names = new TreeSet<String>();
        for (ExtendedObject object : objects)
            names.add((String)object.get("PROFILE"));
        
        return names;
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final int remove(Message message) {
        Query[] queries = new Query[3];
        String name = message.getString("name");
        Documents documents = new Documents(this);
        
        queries[0] = new Query("delete");
        queries[0].setModel("USER_PROFILE_ITEM");
        queries[0].andEqual("NAME", name);

        queries[1] = new Query("delete");
        queries[1].setModel("AUTHORIZATION_ITEM");
        queries[1].andEqual("AUTHORIZATION", name);

        queries[2] = new Query("delete");
        queries[2].setModel("AUTHORIZATION");
        queries[2].andEqual("NAME", name);
        return documents.update(queries);
    }
    
    /**
     * Remove o perfil e associações de autorizações
     * @param message dados do perfil
     * @return 1, se a autorização foi removida com sucesso.
     */
    public final int removeProfile(Message message) {
        Query[] queries = new Query[2];
        String name = message.getString("name");
        Documents documents = new Documents(this);
        
        queries[0] = new Query("delete");
        queries[0].setModel("USER_PROFILE_ITEM");
        queries[0].andEqual("PROFILE", name);
        
        queries[1] = new Query("delete");
        queries[1].setModel("USER_PROFILE");
        queries[1].andEqual("NAME", name);
        return documents.update(queries);
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
        authobject.set("NAME", name);
        authobject.set("OBJECT", authorization.getObject());
        authobject.set("ACTION", authorization.getAction());
        authobject.set("INDEX", ident);
        documents.save(authobject);
        
        itemid = 100 * ident;
        parameters = authorization.getParameters();
        for (String key : parameters.keySet()) {
            itemid++;
            authobject = new ExtendedObject(modelitem);
            authobject.set("ID", itemid);
            authobject.set("AUTHORIZATION", name);
            authobject.set("NAME", key);
            authobject.set("VALUE", parameters.get(key));
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
        String profilename = profile.getName();
        Authorization[] authorizations = profile.getAuthorizations();
        
        object.set("NAME", profilename);
        profileid = documents.getNextNumber("PROFILEINDEX");
        object.set("ID", profileid);
        profileid *= 100;
        object.set("CURRENT", profileid + authorizations.length);
        documents.save(object);
        
        model = documents.getModel("USER_PROFILE_ITEM");
        for (Authorization authorization : authorizations) {
            object = new ExtendedObject(model);
            profileid++;
            object.set("ID", profileid);
            object.set("PROFILE", profilename);
            object.set("NAME", authorization.getName());
            object.set("OBJECT", authorization.getObject());
            object.set("ACTION", authorization.getAction());
            documents.save(object);
        }
    }
}
