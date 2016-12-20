package org.iocaste.packagetool.services.installers;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.SearchHelpData;
import org.iocaste.packagetool.services.Services;
import org.iocaste.packagetool.services.State;
import org.iocaste.packagetool.services.Uninstall;
import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.shell.common.SHLib;

public class SearchHelpInstaller
        extends AbstractModuleInstaller<String, SearchHelpData> {

    public SearchHelpInstaller(Services services) {
        super(services, "SH");
    }

    @Override
    public void install(State state) throws Exception {
        installAll(state, state.data.getSHData());
    }

    @Override
    protected String getObjectName(SearchHelpData shd) {
        return shd.getName();
    }

    @Override
    protected void install(State state, SearchHelpData shd) {
        new SHLib(state.function).save(shd);
    }

    @Override
    public void remove(AbstractServiceInterface[] services,
            ExtendedObject object) {
        Documents documents = (Documents)services[Uninstall.DOCS_LIB];
        SHLib shlib = (SHLib)services[Uninstall.SH_LIB];
        
        shlib.remove(getObjectName(object));
        documents.delete(object);
    }

    @Override
    public void update(State state) throws Exception {
        updateAll(state, state.data.getSHData());
    }

    @Override
    protected void update(State state, SearchHelpData shd) throws Exception {
        new SHLib(state.function).remove(getObjectName(shd));
        install(state, shd);
    }
    
}

