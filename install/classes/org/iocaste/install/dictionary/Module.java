package org.iocaste.install.dictionary;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Module {
    public static final byte NUMC = 0;
    public static final byte CHAR = 1;
    public static final byte BOOLEAN = 2;
    public static final byte INSERT = 0;
    private List<Table> tables;
    private List<Query> queries;
    private Map<String, Authorization> authorizations;
    private Map<String, Profile> profiles;
    private int nritm;
    
    public Module() {
        tables = new ArrayList<>();
        queries = new ArrayList<>();
        authorizations = new HashMap<>();
        profiles = new HashMap<>();
    }
    
    protected final Table tableInstance(String name) {
        Table table = new Table(name);
        tables.add(table);
        
        return table;
    }
    
    protected final void compile(Statement ps) throws Exception {
        String sql;
        
        for (Table table : tables) {
            sql = table.toString();
            System.out.println(sql);
            ps.addBatch(sql);
        }
        
        for (Query query : queries) {
            sql = query.toString();
            System.out.println(sql);
            ps.addBatch(sql);
        }
        
        ps.clearBatch();
    }
    
    protected final void compileAuthorizationProfile(Table auth003,
            Table auth004, String prfnm) {
        int ident;
        Authorization authorization;
        Profile profile = profiles.get(prfnm);
        
        auth003.set("prfnm", prfnm);
        auth003.set("prfid", profile.prfid);
        auth003.set("crrnt", profile.crrnt);
        insert(auth003);
        
        ident = profile.prfid * 100;
        for (String name : profile.authorizations) {
            authorization = authorizations.get(name);
            ident++;
            auth004.set("ident", ident);
            auth004.set("prfnm", prfnm);
            auth004.set("autnm", name);
            auth004.set("objct", authorization.objct);
            auth004.set("actio", authorization.actio);
            insert(auth004);
        }
    }
    
    protected final void insert(Table table) {
        queries.add(new Query(INSERT, table));
    }
    
    protected final void insertAuthorizationProfile(String prfnm) {
        Profile profile = new Profile();

        profile.prfid = profiles.size() + 1;
        profile.crrnt = profile.prfid * 100;
        profiles.put(prfnm, profile);
    }
    
    protected final void insertElement(Table docs003, String name, int decim,
            int len, int type, boolean upcase) {
        docs003.set("ename", name);
        docs003.set("decim", decim);
        docs003.set("lngth", len);
        docs003.set("etype", type);
        docs003.set("upcas", upcase);
        insert(docs003);
    }
    
    protected final void insertExecuteAuthorization(Table auth001,
            Table auth002, String autnm, String appnm) {
        Authorization authorization = new Authorization();
        
        authorization.autid = authorizations.size() + 1;
        authorization.appnm = appnm;
        authorization.objct = "APPLICATION";
        authorization.actio = "EXECUTE";
        authorizations.put(autnm, authorization);
        
        auth001.set("autnm", autnm);
        auth001.set("objct", authorization.objct);
        auth001.set("actio", authorization.actio);
        auth001.set("autid", authorization.autid);
        insert(auth001);
        
        auth002.set("ident", (authorization.autid * 100) + 1);
        auth002.set("autnm", autnm);
        auth002.set("param", "APPNAME");
        auth002.set("value", appnm);
        insert(auth002);
    }

    protected final void insertModel(Table docs001, Table docs005, String docid,
            String tname, String class_) {
        docs001.set("docid", docid);
        docs001.set("tname", tname);
        docs001.set("class", class_);
        insert(docs001);
        
        docs005.set("tname", tname);
        docs005.set("docid", docid);
        insert(docs005);
        
        nritm = 0;
    }
    
    protected final void insertModelItem(Table docs002, String iname,
            String docid, String fname, String ename, String attrb) {
        insertModelItem(docs002, null, iname, docid, fname, ename, attrb,
                null);
        
    }
    
    protected final void insertModelItem(Table docs002, Table docs006,
            String iname, String docid, String fname, String ename,
            String attrb, String itref) {
        docs002.set("iname", iname);
        docs002.set("docid", docid);
        docs002.set("nritm", nritm++);
        docs002.set("fname", fname);
        docs002.set("ename", ename);
        docs002.set("attrb", attrb);
        docs002.set("itref", itref);
        insert(docs002);
        
        if (itref == null)
            return;

        docs006.set("iname", iname);
        docs006.set("itref", itref);
        insert(docs006);
    }
    
    protected final void insertModelKey (Table docs002, Table docs004,
            String iname, String docid, String fname, String ename,
            String attrb) {
        insertModelItem(docs002, iname, docid, fname, ename, attrb);
        
        docs004.set("iname", iname);
        docs004.set("docid", docid);
        insert(docs004);
    }
    
    protected final void linkAuthorizationToProfile(String prfnm, String autnm)
    {
        Profile profile = profiles.get(prfnm);
        
        profile.crrnt++;
        profile.authorizations.add(autnm);
    }
    
    protected final void linkUserToProfile(Table users002, String username,
            int userid, String profile) {
        users002.set("ident", userid);
        users002.set("uname", username);
        users002.set("prfnm", profile);
        insert(users002);
    }
}

class Query {
    public byte command;
    public String tablename;
    public Map<String, Object> values;
    public Map<String, Byte> types;
    
    public Query(byte command, Table table) {
        this.command = command;
        tablename = table.getName();
        
        values = new HashMap<>();
        values.putAll(table.getValues());
        
        types = new HashMap<>();
        for (String name : values.keySet())
            types.put(name, table.getType(name));
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        Object value;
        StringBuilder _into, _values, sb = new StringBuilder();
        
        switch (command) {
        case Module.INSERT:
            _into = new StringBuilder();
            _values = new StringBuilder();
            sb.append("insert into ").append(tablename).append("(");
            for (String field : values.keySet()) {
                if (_into.length() > 0) {
                    _into.append(",");
                    _values.append(",");
                }
                value = values.get(field);
                _into.append(field);
                switch (types.get(field)) {
                case Module.CHAR:
                    if (value == null)
                        _values.append("null");
                    else
                        _values.append("'").append(value).append("'");
                    break;
                case Module.BOOLEAN:
                    _values.append((boolean)value? 1 : 0);
                    break;
                case Module.NUMC:
                    _values.append(value);
                    break;
                }
            }
            
            sb.append(_into).append(") values(").append(_values).append(")");
            break;
        }
        
        return sb.toString();
    }
}

class Authorization {
    public String appnm, objct, actio;
    public int autid;
}

class Profile {
    public int prfid, crrnt;
    public List<String> authorizations = new ArrayList<>();
}