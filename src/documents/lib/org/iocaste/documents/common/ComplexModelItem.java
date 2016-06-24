package org.iocaste.documents.common;

import java.io.Serializable;

public class ComplexModelItem implements Serializable {
    private static final long serialVersionUID = 5798058803469868188L;
    public DocumentModel model;
    public ComplexModel cmodel;
    
    public ComplexModelItem(DocumentModel model) {
        this.model = model;
    }
    
    public ComplexModelItem(ComplexModel cmodel) {
        this.cmodel = cmodel;
    }    
}
