package org.iocaste.packagetool.services.installers;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.services.Services;
import org.iocaste.packagetool.services.State;
import org.iocaste.packagetool.services.Uninstall;
import org.iocaste.protocol.AbstractServiceInterface;

public class ComplexModelInstaller
        extends AbstractModuleInstaller<String, ComplexModel> {

    public ComplexModelInstaller(Services services) {
        super(services, "CMODEL");
    }

    @Override
    public void install(State state) throws Exception {
        super.installAll(state, state.data.getCModels());
    }

    @Override
    protected String getObjectName(ComplexModel cmodel) {
        return cmodel.getName();
    }

    @Override
    protected void install(State state, ComplexModel cmodel) {
        if (state.documents.getComplexModel(getObjectName(cmodel)) == null)
            state.documents.create(cmodel);
    }

    @Override
    public void remove(AbstractServiceInterface[] services,
            ExtendedObject object) {
        Documents documents = (Documents)services[Uninstall.DOCS_LIB];
        documents.removeComplexModel(getObjectName(object));
        documents.delete(object);
    }

    @Override
    public void update(State state) throws Exception {
        super.updateAll(state, state.data.getCModels());
    }

    @Override
    protected void update(State state, ComplexModel cmodel) throws Exception {
        String name = cmodel.getName();
        if (state.documents.getComplexModel(name) != null)
            state.documents.update(cmodel);
    }
    
}

