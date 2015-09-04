package org.iocaste.kernel.common;

import java.util.ArrayList;
import java.util.List;

public class Field {
    public int type, len, dec;
    public boolean key;
    public String tableref, fieldref, operation;
    public List<String> fkc, rfc, pks;
    
    public Field() {
        fkc = new ArrayList<>();
        rfc = new ArrayList<>();
        pks = new ArrayList<>();
    }
}
