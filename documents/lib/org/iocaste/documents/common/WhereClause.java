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

    public final String getField() {
        return field;
    }

    public final String getOperator() {
        return operator;
    }

    public final byte getCondition() {
        return condition;
    }

    public final Object getValue() {
        return value;
    }
}
