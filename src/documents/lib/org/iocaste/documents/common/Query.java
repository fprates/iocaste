package org.iocaste.documents.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Query implements Serializable {
    private static final long serialVersionUID = -5600137702519280069L;
    private String model, statement;
    private List<WhereClause> where;
    private String[] orderby, columns;
    private Map<String, List<JoinClause>> join;
    private int maxresults;
    private boolean skiperror, descending;
    private Map<String, Object> values;
    private ExtendedObject[] entries;
    private Object ns;
    
    public Query() {
        init();
    }
    
    public Query(String statement) {
        init();
        this.statement = statement;
        values = new HashMap<>();
    }
    
    private final void add(String field, byte condition, Object value,
            String operator) {
        where.add(new WhereClause(field, condition, value, operator));
    }
    
    public final void addColumns(String... columns) {
        this.columns = columns;
    }
    
    public final void andEnclose() {
        enclose(true, "and");
    }
    
    public final void andEntries(String field, Collection<?> entries) {
        boolean first;
        
        andEnclose();
        first = true;
        for (Object entry : entries)
            if (first) {
                first = !first;
                andEqual(field, entry);
            } else {
                orEqual(field, entry);
            }
        endEnclose();
    }
    
    public final void andEqual(String field, Object value) {
        add(field, WhereClause.EQ, value, "and");
    }

    public final void andEqualEntries(String field, String entriesfield) {
        add(field, WhereClause.EQ_ENTRY, entriesfield, "and");
    }
    
    public final void andGE(String field, Object value) {
        add(field, WhereClause.GE, value, "and");
    }
    
    public final void andGT(String field, Object value) {
        add(field, WhereClause.GT, value, "and");
    }
    
    public final void andIn(String field, Object... values) {
        add(field, WhereClause.IN, values, "and");
    }

    public final void andIn(String field, ValueRange range) {
        andEnclose();
        add(field, WhereClause.RG, range, "and");
        endEnclose();
    }
    
    public final void andLE(String field, Object value) {
        add(field, WhereClause.LE, value, "and");
    }
    
    public final void andLike(String field, Object value) {
        add(field, WhereClause.CP, value, "and");
    }
    
    public final void andLT(String field, Object value) {
        add(field, WhereClause.LT, value, "and");
    }
    
    public final void andNot(String field, Object value) {
        add(field, WhereClause.NE, value, "and");
    }
    
    public final void descending() {
        descending = true;
    }
    
    private final void enclose(boolean begin, String condition) {
        add(null, (begin)? WhereClause.BE : WhereClause.EE, null, condition);
    }
    
    public final void endEnclose() {
        enclose(false, null);
    }
    
    public final void forEntries(ExtendedObject[] entries) {
        this.entries = entries;
    }
    
    public final String[] getColumns() {
        return columns;
    }
    
    public final ExtendedObject[] getEntries() {
        return entries;
    }
    
    public final List<JoinClause> getJoinClauses(String name) {
        return join.get(name);
    }
    
    public final Set<String> getJoins() {
        return join.keySet();
    }
    
    public final int getMaxResults() {
        return maxresults;
    }
    
    public final String getModel() {
        return model;
    }
    
    public final Object getNS() {
        return ns;
    }
    
    public final String[] getOrderBy() {
        return orderby;
    }
    
    public final String getStatement() {
        return statement;
    }
    
    public final Map<String, Object> getValues() {
        return values;
    }
    
    public final List<WhereClause> getWhere() {
        return where;
    }
    
    private final void init() {
        where = new ArrayList<>();
        join = new LinkedHashMap<>();
        skiperror = true;
    }
    
    public final boolean isDescending() {
        return descending;
    }
    
    public final void join(String model, String field1, String field2) {
        join(model, field1, field2, false);
    }
    
    private final void join(
            String model, String field1, String field2, boolean ns) {
        List<JoinClause> clauses;
        
        if (!join.containsKey(model)) {
            clauses = new ArrayList<>();
            join.put(model, clauses);
        } else {
            clauses = join.get(model);
        }
        
        clauses.add(new JoinClause(field1, field2, ns));
    }
    
    public final void joinNS(String model, String joinmodel) {
        join(model, joinmodel, null, true);
    }
    
    public final boolean mustSkipError() {
        return skiperror;
    }
    
    public final void orderBy(String... fields) {
        orderby = fields;
    }
    
    public final void orEnclose() {
        enclose(true, "or");
    }
    
    public final void orEqual(String field, Object value) {
        add(field, WhereClause.EQ, value, "or");
    }

    public final void orGE(String field, Object value) {
        add(field, WhereClause.GE, value, "or");
    }
    
    public final void orGT(String field, Object value) {
        add(field, WhereClause.GT, value, "or");
    }
    
    public final void orIn(String field, Object... values) {
        add(field, WhereClause.IN, values, "or");
    }

    public final void orLE(String field, Object value) {
        add(field, WhereClause.LE, value, "or");
    }
    
    public final void orLike(String field, Object value) {
        add(field, WhereClause.CP, value, "or");
    }
    
    public final void orLT(String field, Object value) {
        add(field, WhereClause.LT, value, "or");
    }
    
    public final void orNot(String field, Object value) {
        add(field, WhereClause.NE, value, "or");
    }
    
    public final void setMaxResults(int max) {
        maxresults = max;
    }
    
    public final void setModel(String model) {
        this.model = model;
    }
    
    public final void setNS(Object ns) {
        this.ns = ns;
    }
    
    public final void setSkipError(boolean skiperror) {
        this.skiperror = skiperror;
    }
    
    public final void values(String name, Object value) {
        values.put(name, value);
    }
}
