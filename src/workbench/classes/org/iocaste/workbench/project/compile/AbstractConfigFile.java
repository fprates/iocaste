package org.iocaste.workbench.project.compile;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.files.Directory;
import org.iocaste.protocol.files.DirectoryInstance;
import org.iocaste.protocol.utils.XMLElement;

public abstract class AbstractConfigFile implements ConfigFile{
    protected XMLElement root;
    protected String directory, file;
    private Documents documents;
    
    public AbstractConfigFile(PageBuilderContext context, String xmlroot) {
        root = new XMLElement(xmlroot);
        root.head("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        documents = new Documents(context.function);
    }
    
    @Override
    public final void save(Directory war) {
        DirectoryInstance file;
        
        file = war.file(directory, this.file);
        file.content(root.toString());
    }
    
    protected final ExtendedObject[] select(Query query) {
        return documents.select(query);
    }
}
