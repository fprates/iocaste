package org.iocaste.documents.common;

public enum RangeOption {
    EQ(WhereClause.EQ),
    NE(WhereClause.NE),
    LT(WhereClause.LT),
    GT(WhereClause.GT),
    LE(WhereClause.LE),
    GE(WhereClause.GE),
    BT(WhereClause.BT),
    CP(WhereClause.CP);
    
    private byte operator;
    
    RangeOption(byte operator) {
        this.operator = operator;
    }
    
    public final byte getOperator() {
        return operator;
    }
}
