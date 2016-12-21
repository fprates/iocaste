package org.iocaste.packagetool.services.installers;

import org.iocaste.authority.common.Authority;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.services.Services;
import org.iocaste.packagetool.services.State;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.user.Authorization;

public class AuthorizationInstaller
        extends AbstractModuleInstaller<String, Authorization> {
    private Authority authority;
    private Documents documents;
    public AuthorizationInstaller(Services services) {
        super(services, "AUTHORIZATION");
    }

    @Override
    protected String getObjectName(String key, Authorization authorization) {
        return authorization.getName();
    }

    @Override
    public final void init(Function function) {
        if (authority != null)
            return;
        authority = new Authority(function);
        documents = new Documents(function);
    }
    
    @Override
    public final void install(State state) throws Exception {
        installAll(state, state.data.getAuthorizations());
    }

    @Override
    protected void install(State state, String key, Authorization authorization)
    {
        if (authority.get(authorization.getName()) == null)
            authority.save(authorization);
        authority.assign("ADMIN", "ALL", authorization);
    }

    @Override
    public void remove(ExtendedObject object) {
        authority.remove(getObjectName(object));
        documents.delete(object);
    }

    @Override
    public void update(State state) throws Exception {
        super.updateAll(state, state.data.getAuthorizations());
    }

    @Override
    protected void update(State state, String key, Authorization authorization)
            throws Exception {
        install(state, key, authorization);
    }
    
}

