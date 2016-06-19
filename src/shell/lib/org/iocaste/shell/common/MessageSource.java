package org.iocaste.shell.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.Function;

/**
 * Mensagens.
 * 
 * @author francisco.prates
 *
 */
public final class MessageSource {
    private Map<String, Properties> messages;
    private String locale;
    
    public MessageSource() {
        messages = new HashMap<>();
    }
    
    /**
     * Obtem mensagem traduzida especificada.
     * @param name nome
     * @return mensagem
     */
    public final String get(String name) {
        return messages.get(locale).getProperty(name);
    }
    
    /**
     * Obtem mensagem traduzida especificada,
     * retorna default_ se não encontrada.
     * @param name nome
     * @param default_ mensagem padrão
     * @return mensagem
     */
    public final String get(String name, String default_) {
        return messages.get(locale).getProperty(name, default_);
    }
    
    public final void instance(String locale) {
        if (!this.messages.containsKey(locale))
            this.messages.put(locale, new Properties());
        this.locale = locale;
    }
    
    /**
     * Carrega mensagens instaladas.
     * @param app aplicação.
     * @param locale localização
     * @param function função.
     */
    public final void loadFromApplication(String app, Locale locale,
            Function function) {
        Query query;
        ExtendedObject[] objects;
        String tag, message;
        Properties properties;
        
        if (locale == null)
            return;
        
        query = new Query();
        query.setModel("MESSAGES");
        query.andEqual("LOCALE", locale.toString());
        query.andEqual("PACKAGE", app);
        objects = new Documents(function).select(query);
        if (objects == null)
            return;
        
        properties = messages.get(locale);
        if (properties == null)
            instance(locale.toString());
        
        for (ExtendedObject object : objects) {
            tag = object.get("NAME");
            message = object.get("TEXT");
            put(tag, message);
        }
    }
    
    /**
     * Carrega mensagem a partir de arquivo.
     * @param path caminho do arquivo.
     */
    public final void loadFromFile(String locale, String path) {
        InputStream is = getClass().getResourceAsStream(path);
        
        if (is == null)
            throw new RuntimeException("Message file not found.");
        
        try {
            messages.get(locale).load(is);
            is.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public final void put(String tag, String message) {
        put(locale, tag, message);
    }
    
    public final void put(String locale, String tag, String message) {
        messages.get(locale).put(tag, message);
    }
    
    public final void setLocale(Locale locale) {
        this.locale = locale.toString();
    }
    
    public final int size() {
        return messages.size();
    }
}
