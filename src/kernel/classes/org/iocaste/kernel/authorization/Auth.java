package org.iocaste.kernel.authorization;

import java.sql.Connection;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.kernel.documents.Documents;
import org.iocaste.kernel.documents.GetDocumentModel;
import org.iocaste.kernel.documents.GetNextNumber;
import org.iocaste.kernel.documents.GetObject;
import org.iocaste.kernel.documents.ModifyDocument;
import org.iocaste.kernel.documents.SaveDocument;
import org.iocaste.kernel.documents.SelectDocument;
import org.iocaste.kernel.documents.UpdateMultiple;
import org.iocaste.kernel.session.Session;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.UserProfile;

public class Auth extends AbstractFunction {
    public Session session;
    public Documents documents;
    
    public Auth() {
        export("add_auth", "addAuthorization");
        export("assign_auth", "assignAuthorization");
        export("assign_auth_profile", "assignProfile");
        export("get_auth", "get");
        export("get_auth_profile", "getProfile");
        export("get_user_profiles", "getUserProfiles");
        export("is_authorized", new IsAuthorized());
        export("remove_auth", "remove");
        export("remove_auth_profile", "removeProfile");
        export("save_auth", "save");
        export("save_auth_profile", "saveProfile");
    }
    
    /**
     * Adds an authorization to a profile.
     * @param message
     */
    public final void addAuthorization(Message message) throws Exception {
        Authorization authorization = message.get("authorization");
        String profile = message.getst("profile");
        
        addAuthorization(message.getSessionid(), profile, authorization);
    }
    
    /**
     * INTERNAL: adds an authorization to a profile.
     * @param name
     * @param authorization
     */
    private final void addAuthorization(String sessionid, String name,
            Authorization authorization) throws Exception {
        int itemid;
        DocumentModel model;
        ExtendedObject profileitem;
        GetDocumentModel modelget;
        SaveDocument documentsave;
        ModifyDocument documentmodify;
        GetObject objectget = documents.get("get_object");
        Connection connection = documents.database.getDBConnection(sessionid);
        ExtendedObject profile = objectget.
                run(connection, documents, "USER_PROFILE", name);
        
        if (profile == null)
            throw new IocasteException("%s is an invalid profile.", name);
        
        modelget = documents.get("get_document_model");
        documentsave = documents.get("save_document");
        documentmodify = documents.get("modify");
        
        itemid = profile.geti("CURRENT") + 1;
        model = modelget.run(connection, documents, "USER_PROFILE_ITEM");
        profileitem = new ExtendedObject(model);
        profileitem.set("ID", itemid);
        profileitem.set("PROFILE", name);
        profileitem.set("NAME", authorization.getName());
        profileitem.set("OBJECT", authorization.getObject());
        profileitem.set("ACTION", authorization.getAction());
        documentsave.run(connection, profileitem);
        
        profile.set("CURRENT", itemid);
        documentmodify.run(documents, connection, profile);
    }
    
    /**
     * Adds an authorization to a profile, checking
     * @param message
     * @throws Exception
     */
    public final void assignAuthorization(Message message) throws Exception {
        Query query;
        Authorization authorization;
        ExtendedObject[] profiles;
        String username = message.getst("username");
        String profilename = message.getst("profile");
        String sessionid = message.getSessionid();
        SelectDocument select = documents.get("select_document");
        Connection connection = documents.database.getDBConnection(sessionid);
        
        query = new Query();
        query.setModel("USER_AUTHORITY");
        query.andEqual("USERNAME", username);
        query.andEqual("PROFILE", profilename);
        query.setMaxResults(1);
        profiles = select.run(connection, query);
        if (profiles == null)
            throw new IocasteException(
                    "%s for %s is an invalid profile.", profilename, username);
        
        authorization = message.get("authorization");
        addAuthorization(sessionid, profilename, authorization);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void assignProfile(Message message) throws Exception {
        int userid, id, profileid;
        DocumentModel model;
        GetDocumentModel modelget;
        SaveDocument save;
        String username = message.getst("username");
        String profile = message.getst("profile");
        String sessionid = message.getSessionid();
        Connection connection = documents.database.getDBConnection(sessionid);
        GetObject objectget = documents.get("get_object");
        ExtendedObject object = objectget.
                run(connection, documents, "LOGIN", username);
        
        if (object == null)
            throw new IocasteException("Invalid user.");
        
        object = objectget.run(connection, documents, "USER_PROFILE", profile);
        if (object == null)
            throw new IocasteException("Invalid profile.");
        
        profileid = object.geti("ID");
        userid = object.geti("ID");
        id = (userid * 100) + profileid;

        modelget = documents.get("get_document_model");
        model = modelget.run(connection, documents, "USER_AUTHORITY");
        object = new ExtendedObject(model);
        object.set("ID", id);
        object.set("USERNAME", username);
        object.set("PROFILE", profile);
        
        save = documents.get("save_document");
        save.run(connection, object);
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final Authorization get(Message message) throws Exception {
        Query query;
        Authorization authorization;
        ExtendedObject[] parameters;
        SelectDocument select;
        String name = message.getst("name");
        String sessionid = message.getSessionid();
        Connection connection = documents.database.getDBConnection(sessionid);
        GetObject objectget = documents.get("get_object");
        ExtendedObject authobject = objectget.
                run(connection, documents, "AUTHORIZATION", name);
        
        if (authobject == null)
            return null;
        
        name = authobject.get("NAME");
        authorization = new Authorization(name);
        authorization.setObject((String)authobject.get("OBJECT"));
        authorization.setAction((String)authobject.get("ACTION"));

        query = new Query();
        query.setModel("AUTHORIZATION_ITEM");
        query.andEqual("AUTHORIZATION", name);

        select = documents.get("select_document");
        parameters = select.run(connection, query);
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
    public final UserProfile getProfile(Message message) throws Exception {
        UserProfile profile;
        String name = message.getst("name");
        GetObject objectget = documents.get("get_object");
        Connection connection = documents.database.
                getDBConnection(message.getSessionid());
        ExtendedObject object = objectget.
                run(connection, documents, "USER_PROFILE", name);
        
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
    public final Set<String> getUserProfiles(Message message)
            throws Exception {
        Query query;
        Set<String> names;
        ExtendedObject[] objects;
        String username = message.getst("username");
        SelectDocument select = documents.get("select_document");
        Connection connection = documents.database.
                getDBConnection(message.getSessionid());
        
        query = new Query();
        query.setModel("USER_AUTHORITY");
        query.andEqual("USERNAME", username);
        objects = select.run(connection, query);
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
    public final int remove(Message message) throws Exception {
        Query[] queries = new Query[3];
        String name = message.getst("name");
        UpdateMultiple update = documents.get("update_m");
        Connection connection = documents.database.
                getDBConnection(message.getSessionid());
        
        queries[0] = new Query("delete");
        queries[0].setModel("USER_PROFILE_ITEM");
        queries[0].andEqual("NAME", name);

        queries[1] = new Query("delete");
        queries[1].setModel("AUTHORIZATION_ITEM");
        queries[1].andEqual("AUTHORIZATION", name);

        queries[2] = new Query("delete");
        queries[2].setModel("AUTHORIZATION");
        queries[2].andEqual("NAME", name);
        return update.run(connection, documents, queries);
    }
    
    /**
     * Remove o perfil e associações de autorizações
     * @param message dados do perfil
     * @return 1, se a autorização foi removida com sucesso.
     */
    public final int removeProfile(Message message) throws Exception {
        Query[] queries = new Query[2];
        String name = message.getst("name");
        UpdateMultiple update = documents.get("update_m");
        Connection connection = documents.database.
                getDBConnection(message.getSessionid());
        
        queries[0] = new Query("delete");
        queries[0].setModel("USER_PROFILE_ITEM");
        queries[0].andEqual("PROFILE", name);
        
        queries[1] = new Query("delete");
        queries[1].setModel("USER_PROFILE");
        queries[1].andEqual("NAME", name);
        return update.run(connection, documents, queries);
    }
    
    /**
     * 
     * @param message
     */
    public final void save(Message message) throws Exception {
        ExtendedObject authobject;
        long ident, itemid;
        Map<String, String> parameters;
        Authorization authorization = message.get("authorization");
        GetDocumentModel modelget = documents.get("get_document_model");
        GetNextNumber numberget = documents.get("get_next_number");
        SaveDocument save = documents.get("save_document");
        Connection connection = documents.database.
                getDBConnection(message.getSessionid());
        DocumentModel model = modelget.
                run(connection, documents, "AUTHORIZATION");
        DocumentModel modelitem = modelget.
                run(connection, documents, "AUTHORIZATION_ITEM");
        String name = authorization.getName();
        
        ident = numberget.run(connection, documents, "AUTHINDEX", null, null);
        authobject = new ExtendedObject(model);
        authobject.set("NAME", name);
        authobject.set("OBJECT", authorization.getObject());
        authobject.set("ACTION", authorization.getAction());
        authobject.set("INDEX", ident);
        save.run(connection, authobject);
        
        itemid = 100 * ident;
        parameters = authorization.getParameters();
        for (String key : parameters.keySet()) {
            itemid++;
            authobject = new ExtendedObject(modelitem);
            authobject.set("ID", itemid);
            authobject.set("AUTHORIZATION", name);
            authobject.set("NAME", key);
            authobject.set("VALUE", parameters.get(key));
            save.run(connection, authobject);
        }
    }
    
    /**
     * 
     * @param message
     */
    public final void saveProfile(Message message) throws Exception {
        long profileid;
        UserProfile profile = message.get("profile");
        GetDocumentModel modelget = documents.get("get_document_model");
        GetNextNumber numberget = documents.get("get_next_number");
        SaveDocument save = documents.get("save_document");
        Connection connection = documents.database.
                getDBConnection(message.getSessionid());
        DocumentModel model = modelget.
                run(connection, documents, "USER_PROFILE");
        ExtendedObject object = new ExtendedObject(model);
        String profilename = profile.getName();
        Authorization[] authorizations = profile.getAuthorizations();
        
        object.set("NAME", profilename);
        profileid = numberget.
                run(connection, documents, "PROFILEINDEX", null, null);
        object.set("ID", profileid);
        profileid *= 100;
        object.set("CURRENT", profileid + authorizations.length);
        save.run(connection, object);
        
        model = modelget.run(connection, documents, "USER_PROFILE_ITEM");
        for (Authorization authorization : authorizations) {
            object = new ExtendedObject(model);
            profileid++;
            object.set("ID", profileid);
            object.set("PROFILE", profilename);
            object.set("NAME", authorization.getName());
            object.set("OBJECT", authorization.getObject());
            object.set("ACTION", authorization.getAction());
            save.run(connection, object);
        }
    }

}
