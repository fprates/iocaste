package org.iocaste.documents.common;

import java.io.Serializable;

public class DataComponent implements Serializable {
    private static final long serialVersionUID = -7540830568904826385L;
    private String name;

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }
}
