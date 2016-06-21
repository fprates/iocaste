package org.iocaste.kernel;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.kernel.authorization.Auth;
import org.iocaste.kernel.config.Config;
import org.iocaste.kernel.database.Database;
import org.iocaste.kernel.documents.Documents;
import org.iocaste.kernel.files.FileServices;
import org.iocaste.kernel.packages.Packages;
import org.iocaste.kernel.process.ProcessServices;
import org.iocaste.kernel.session.IsConnected;
import org.iocaste.kernel.session.Session;
import org.iocaste.kernel.users.Users;
import org.iocaste.protocol.AbstractIocasteServlet;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class Servlet extends AbstractIocasteServlet {
    private static final long serialVersionUID = -8569034003940826582L;
    private Session session;
    private Config config;
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.ServerServlet#config()
     */
    @Override
    public void config() {
        Map<String, Object[]> parameters;
        Database database = new Database();
        Users users = new Users();
        Auth auth = new Auth();
        Documents documents = new Documents();
        ProcessServices process = new ProcessServices();
        FileServices files = new FileServices();
        
        session = new Session();
        documents.config = config = new Config();
        
        session.database = users.database = config.database = database; 
        auth.database = documents.database = database;
        users.session = database.session = auth.session = session;
        process.files = files;
        session.users = users;
        
        register(database);
        register(users);
        register(session);
        register(auth);
        register(config);
        register(new Services());
        register(documents);
        register(files);
        register(new Packages());
        register(process);
        
        authorize("is_connected", null);
        authorize("login", null);
        
        parameters = new HashMap<>();
        parameters.put("from",
                new String[] {"SHELL001", "SHELL002", "SHELL003", "SHELL004",
                              "SHELL005", "SHELL006"});
        
        authorize("checked_select", parameters);
        authorize("get_host", null);
        authorize("get_locale", null);
        authorize("commit", null);
        authorize("rollback", null);
        authorize("is_authorized", null);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.ServerServlet#preRun(
     *     org.iocaste.protocol.Message)
     */
    @Override
    protected final void preRun(Message message) throws Exception {
        IsConnected isconnected;
        
        if (!config.isInitialized()) {
            session.setServletContext(getServletContext());
            config.init();
        }
        
        if (isAuthorized(message))
            return;
        
        isconnected = session.get("is_connected");
        if (!(boolean)isconnected.run(message))
            throw new IocasteException(new StringBuilder(message.getId()).
                    append("() denied: invalid session.").toString());
    }
}
