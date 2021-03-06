package org.iocaste.install.settings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.install.DBConfig;
import org.iocaste.install.dictionary.Core;
import org.iocaste.install.dictionary.Documents;
import org.iocaste.install.dictionary.Login;
import org.iocaste.install.dictionary.Module;
import org.iocaste.install.dictionary.ModuleContext;
import org.iocaste.install.dictionary.Package;
import org.iocaste.install.dictionary.SH;
import org.iocaste.kernel.common.DBNames;
import org.iocaste.kernel.common.Table;
import org.iocaste.packagetool.common.PackageTool;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;

public class InstallContinue extends AbstractActionHandler {
    private static final String CONFIG_FILE = "core.properties";
    private static final String IOCASTE_DIR = ".iocaste";
    
    private static final String[] MSSQL_INIT = {
        "use master;",
        "if exists(select name from sys.databases where name = '",
        "drop database ",
        "create database ",
        "use "
    };
    
    private static final String[] PACKAGES = new String[] {
        "iocaste-packagetool",
        "iocaste-tasksel",
        "iocaste-setup",
        "iocaste-appbuilder",
        "iocaste-usereditor",
        "iocaste-dataeditor",
        "iocaste-datadict",
        "iocaste-dataview",
        "iocaste-gconfigview",
        "iocaste-external",
        "iocaste-copy",
        "iocaste-upload",
        "iocaste-masterdata",
        "iocaste-sysconfig"
    };
    
    private static final String[] MYSQL_INIT = {
        "drop database if exists ",
        "create database ",
        "use "
    };
    
    private static final String[] POSTGRES_INIT = {
        "drop database ",
        "create database "
    };
    
    private final void createTables(Statement ps, Config config)
            throws Exception {
        Module documents, login, sh, package_;
        Map<String, Table> tables;
        byte dbtype = DBNames.names.get(config.dbtype);
        List<String> sqllist = new ArrayList<>();
        ModuleContext context = new ModuleContext();
        
        sqllist.addAll(new Core(dbtype).install(context));

        documents = new Documents(dbtype);
        sqllist.addAll(documents.install(context));
        tables = documents.getTables();
        
        login = new Login(dbtype);
        login.putTables(tables);
        sqllist.addAll(login.install(context));
        
        sh = new SH(dbtype);
        sh.putTables(tables);
        sqllist.addAll(sh.install(context));
        
        package_ = new Package(dbtype);
        package_.putTables(tables);
        sqllist.addAll(package_.install(context));
        
        for (String sql : sqllist)
            ps.addBatch(sql);
    }

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Statement ps;
        Exception ex;
        boolean dorollback;
        Connection connection;
        String[] init = null;
        Config config = new Config();
        
        config.option = getdfb("dbinfo", "OPTIONS");
        config.host = getdfst("dbinfo", "HOST");
        config.username = getdfst("dbinfo", "USERNAME");
        config.secret = getdfst("dbinfo", "SECRET");
        config.dbname = getdfst("dbinfo", "DBNAME");
        config.dbtype = getinputst("dbtype");
        init = getDBInitializator(config);
        
        switch (config.option) {
        case DBConfig.KEEP_BASE:
        case DBConfig.NEW_BASE:
            dorollback = false;
            connection = null;
            try {
                Class.forName(config.dbdriver).newInstance();
                connection = DriverManager.getConnection(
                        config.iurl, config.username, config.secret);
                connection.setAutoCommit(true);
                ps = connection.createStatement();
                
                if (init != null)
                    for (String sql : init)
                        ps.addBatch(sql);
                ps.executeBatch();

                dorollback = true;
                if (config.secconn) {
                    connection.close();
                    connection = DriverManager.getConnection(
                            config.url, config.username, config.secret);
                    connection.setAutoCommit(false);
                    ps = connection.createStatement();
                }
                
                ps.clearBatch();
                connection.setAutoCommit(false);
                createTables(ps, config);
                ps.executeBatch();
                
                saveConfig(config);
                connection.commit();
                connection.close();
                ps.close();
            } catch (Exception e) {
                if (dorollback) {
                    connection.rollback();
                    connection.close();
                }
                
                ex = new Exception(e.toString());
                ex.setStackTrace(e.getStackTrace());
                throw ex;
            }
            
            break;
        case DBConfig.CHANGE_BASE:
            saveConfig(config);
            break;
        }
        
        reconnectdb(context);
        if (config.option != DBConfig.CHANGE_BASE)
            installPackages(context);
        
        init("finish", getExtendedContext());
        redirect("finish");
    }
    
    private final void installPackages(PageBuilderContext context) {
        PackageTool pkgtool;
        Iocaste iocaste;
        
        iocaste = new Iocaste(context.function);
        iocaste.login("ADMIN", "iocaste", "pt_BR");
        
        try {
            pkgtool = new PackageTool(context.function);
            for (String pkgname : PACKAGES)
                pkgtool.install(pkgname);
        } catch (Exception e) {
            iocaste.rollback();
            iocaste.disconnect();
            throw e;
        }

        iocaste.commit();
        iocaste.disconnect();
    }
    
    private final void reconnectdb(PageBuilderContext context) {
        GenericService service;
        Message message;
        
        message = new Message("disconnected_operation");
        message.add("disconnected", false);
        service = new GenericService(context.function, Iocaste.SERVERNAME);
        service.invoke(message);
    }
    
    private final String[] getDBInitializator(Config config) {
        String[] init = null;
        byte dbtype = DBNames.names.get(config.dbtype);
        
        switch (dbtype) {
        case DBNames.MSSQL1:
        case DBNames.MSSQL2:
            config.dbname = config.dbname.toUpperCase();
            config.dbdriver = DBNames.DRIVERS[dbtype];
            
            switch (config.option) {
            case DBConfig.CHANGE_BASE:
                init = new String[1];
                init[0] = MSSQL_INIT[4].concat(config.dbname);
            case DBConfig.KEEP_BASE:
                config.iurl = new StringBuilder("jdbc:sqlserver://").
                        append(config.host).
                        append(";databaseName=").
                        append(config.dbname).toString();
                config.url = config.iurl;
                break;
            case DBConfig.NEW_BASE:
                config.url = new StringBuilder("jdbc:sqlserver://").
                        append(config.host).toString();
                config.iurl = new StringBuilder(config.url).
                        append(";databaseName=").
                        append(config.dbname).toString();
                
                init = new String[4];
                init[0] = MSSQL_INIT[0];
                init[1] = new StringBuilder(MSSQL_INIT[1]).
                        append(config.dbname).
                        append("') ").
                        append(MSSQL_INIT[2]).
                        append(config.dbname).
                        append(";").toString();
                init[2] = new StringBuilder(MSSQL_INIT[3]).
                        append(config.dbname).
                        append(";").toString();
                init[3] = MSSQL_INIT[4].concat(config.dbname);
                
                break;
            }
            
            break;
        case DBNames.HSQLDB:
            config.dbname = config.dbname.toUpperCase();
            config.dbdriver = DBNames.DRIVERS[dbtype];
            config.iurl = new StringBuilder("jdbc:hsqldb:hsql://").
                    append(config.host).
                    append("/").
                    append(config.dbname).toString();
            config.url = config.iurl;
            
            switch (config.option) {
            case DBConfig.NEW_BASE:
                init = new String[1];
                init[0] = "drop schema public cascade";
                
                break;
            }
            
            break;
        case DBNames.MYSQL:
            config.dbname = config.dbname.toUpperCase();
            config.dbdriver = DBNames.DRIVERS[dbtype];
            config.iurl = "jdbc:mysql://".concat(config.host);
            config.url = new StringBuilder(config.iurl).
                    append("/").
                    append(config.dbname).toString();
            
            switch (config.option) {
            case DBConfig.KEEP_BASE:
                init = new String[1];
                init[0] = MYSQL_INIT[2].concat(config.dbname);
                
                break;
            case DBConfig.NEW_BASE:
                init = new String[3];
                init[0] = MYSQL_INIT[0].concat(config.dbname);
                init[1] = MYSQL_INIT[1].concat(config.dbname);
                init[2] = MYSQL_INIT[2].concat(config.dbname);
                
                break;
            }
            
            break;
        case DBNames.POSTGRES:
            config.dbdriver = DBNames.DRIVERS[dbtype];
            config.iurl = new StringBuilder("jdbc:postgresql://").
                    append(config.host).append("/template1").toString();
            config.url = new StringBuilder("jdbc:postgresql://").
                    append(config.host).append("/").
                    append(config.dbname).toString();
            
            switch (config.option) {
            case DBConfig.NEW_BASE:
                init = new String[2];
                init[0] = POSTGRES_INIT[0].concat(config.dbname);
                init[1] = POSTGRES_INIT[1].concat(config.dbname);
                config.secconn = true;
                break;
            }
            
            break;
        }
        
        return init;
    }
    
    private final void saveConfig(Config config) throws Exception {
        File file;
        BufferedWriter writer;
        Properties properties = new Properties();
        String path = new StringBuilder(System.getProperty("user.home")).
                append(File.separator).
                append(IOCASTE_DIR).toString();
        
        new File(path).mkdir();
        file = new File(new StringBuilder(path).
                append(File.separator).append(CONFIG_FILE).toString());
        writer = new BufferedWriter(new FileWriter(file));
        
        properties.put("dbdriver", config.dbdriver);
        properties.put("dbname", config.dbname);
        properties.put("username", config.username);
        properties.put("secret", (config.secret == null)? "" : config.secret);
        properties.put("url", config.url);
        properties.put("host", config.host);
        properties.put("dbtype", config.dbtype.toString());
        properties.store(writer, null);
        
        writer.flush();
        writer.close();
    }

}

class Config {
    public byte option;
    public boolean secconn;
    public String iurl, dbdriver, host, url, username, secret, dbname, dbtype;
}
