package org.iocaste.external.common.sap;

import java.util.HashMap;
import java.util.Map;

public class SapMessage {
    public Map<String, Object> changing, importing;
    public SapMessageTable tparameters;
    
    public SapMessage() {
        changing = new HashMap<>();
        importing = new HashMap<>();
        tparameters = new SapMessageTable();
    }
}
