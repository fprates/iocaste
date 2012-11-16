package org.iocaste.documents.common;

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
