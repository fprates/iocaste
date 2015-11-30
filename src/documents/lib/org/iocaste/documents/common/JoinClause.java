package org.iocaste.documents.common;

import java.io.Serializable;

public class JoinClause implements Serializable {
    private static final long serialVersionUID = 1L;
    private String[] operators;
    private boolean ns;
    
    public JoinClause(String operator1, String operator2, boolean ns) {
        operators = new String[2];
        operators[0] = operator1;
        operators[1] = operator2;
        this.ns = ns;
    }
    
    public final String getOperator(int number) {
        return operators[number];
    }
    
    public final boolean isNS() {
        return ns;
    }
}
