package org.iocaste.documents.common;

public enum RangeOption {
    EQ("="),
    NE("<>"),
    LT("<"),
    GT(">"),
    LE("<="),
    GE(">="),
    BT("AND"),
    CP("LIKE");
    
    private String operator;
    
    RangeOption(String operator) {
        this.operator = operator;
    }
    
    public final String getOperator() {
        return operator;
    }
}
