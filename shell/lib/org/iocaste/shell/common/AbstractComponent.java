package org.iocaste.shell.common;

import org.iocaste.documents.common.DocumentModelItem;

public abstract class AbstractComponent extends AbstractElement implements Component {
    private static final long serialVersionUID = -5327168368336946819L;
    private Container container;
    private DocumentModelItem modelitem;
    
    public AbstractComponent(Container container, Const type, String name) {
        super(type, name);
        
        this.container = container;
        
        if (container != null)
            container.add(this);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Component#getContainer()
     */
    @Override
    public final Container getContainer() {
        return container;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Component#getModelItem()
     */
    @Override
    public final DocumentModelItem getModelItem() {
        return modelitem;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isContainable()
     */
    @Override
    public final boolean isContainable() {
        return false;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Component#setModelItem(
     *     org.iocaste.documents.common.DocumentModelItem)
     */
    @Override
    public final void setModelItem(DocumentModelItem modelitem) {
        this.modelitem = modelitem;
    }
}
