package org.iocaste.documents.common;

import java.io.Serializable;

public class WhereClause implements Serializable {
    private static final long serialVersionUID = -4358463875417916722L;
    public static final byte EQ = 0;
    public static final byte NE = 1;
    public static final byte LT = 2;
    public static final byte LE = 3;
    public static final byte GT = 4;
    public static final byte GE = 5;
    public static final byte IN = 6;
    public static final byte BE = 7;
    public static final byte EE = 8;
    public static final byte EQ_ENTRY = 9;
    public static final byte NE_ENTRY = 10;
    public static final byte LT_ENTRY = 11;
    public static final byte LE_ENTRY = 12;
    public static final byte GT_ENTRY = 13;
    public static final byte GE_ENTRY = 14;
    public static final byte IN_ENTRY = 15;
    public static final byte BE_ENTRY = 16;
    public static final byte EE_ENTRY = 17;
    public static final byte CP = 18;
    public static final byte RG = 19;
    public static final byte BT= 20;
    private String field, operator;
    private byte condition;
    private Object value;
    
    public WhereClause(String field, byte condition, Object value,
            String operator) {
        this.field = field;
        this.condition = condition;
        this.value = value;
        this.operator = operator;
    }

    public final byte getCondition() {
        return condition;
    }
    
    public final String getField() {
        return field;
    }

    public final String getOperator() {
        return operator;
    }

    public final Object getValue() {
        return value;
    }
}
