package org.iocaste.documents.common;

/**
 * DummyModelItem provides a model item reference useful
 * for reference in installation scripts.
 * 
 * It's key enabled by default, so it can be used as a
 * foreign key.
 * 
 * @author francisco.prates
 *
 */
public class DummyModelItem extends DocumentModelItem {
    private static final long serialVersionUID = -4093367632974107518L;

    public DummyModelItem(String modelname, String itemname) {
        super(itemname);
        DocumentModel model = new DocumentModel(modelname);

        setDocumentModel(model);
        setDummy(true);
        model.add(new DocumentModelKey(this));
    }
}
