package org.iocaste.packagetool.services.installers;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.packagetool.services.IsInstalled;
import org.iocaste.packagetool.services.Services;
import org.iocaste.packagetool.services.State;
import org.iocaste.protocol.Function;

public class PackageInstaller extends AbstractModuleInstaller<String, String> {
    private Documents documents;
    
    public PackageInstaller(Services services) {
        super(services, null);
    }

    @Override
    public void init(Function function) {
        documents = new Documents(function);
    }

    @Override
    protected String getObjectName(String object) {
        // TODO Auto-generated method stub
        return null;
    }
    
    private final ExtendedObject getPackageHeader(State state, String pkgname) {
        ExtendedObject[] objects;
        Query query = new Query();
        
        query.setMaxResults(1);
        query.setModel("PACKAGE");
        query.andEqual("NAME", pkgname);
        objects = state.documents.select(query);
        return (objects == null)? null : objects[0];
    }
    
    private final ExtendedObject getPackageHeaderInstance(
            State state, String pkgname) {
        ExtendedObject header;
        
        header = new ExtendedObject(state.documents.getModel("PACKAGE"));
        header.set("NAME", pkgname);
        return header;
    }

    @Override
    public void install(State state) throws Exception {
        install(state, state.pkgname);
        registry(state, state.pkgname);
    }

    @Override
    protected void install(State state, String pkgname) {
        ExtendedObject header;
        IsInstalled isinstalled = state.function.get("is_installed");
        
        if (isinstalled.run(pkgname)) {
            getPackageHeader(state, pkgname);
        } else {
            header = getPackageHeaderInstance(state, pkgname);
            state.documents.save(header);
        }
    }

    @Override
    public void remove(ExtendedObject object) {
        Query query = new Query("delete");
        query.setModel("PACKAGE");
        query.andEqual("NAME", getObjectName(object));
        documents.update(query);
        documents.delete(object);
    }

    @Override
    public void update(State state) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void update(State state, String object) throws Exception {
        // TODO Auto-generated method stub
        
    }
    
}

