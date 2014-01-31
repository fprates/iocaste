package org.iocaste.shell.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Locale;
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
public class MessageSource implements Serializable {
    private static final long serialVersionUID = 7937205136041189687L;
    private Properties messages;
    
    public MessageSource() {
        messages = new Properties();
    }
    
    /**
     * Obtem mensagem traduzida especificada.
     * @param name nome
     * @return mensagem
     */
    public final String get(String name) {
        return messages.getProperty(name);
    }
    
    /**
     * Obtem mensagem traduzida especificada,
     * retorna default_ se não encontrada.
     * @param name nome
     * @param default_ mensagem padrão
     * @return mensagem
     */
    public final String get(String name, String default_) {
        return messages.getProperty(name, default_);
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
        
        if (locale == null)
            return;
        
        query = new Query();
        query.setModel("MESSAGES");
        query.andEqual("LOCALE", locale.toString());
        query.andEqual("PACKAGE", app);
        objects = new Documents(function).select(query);
        if (objects == null)
            return;
        
        for (ExtendedObject object : objects) {
            tag = object.get("NAME");
            message = object.get("TEXT");
            messages.put(tag, message);
        }
    }
    
    /**
     * Carrega mensagem a partir de arquivo.
     * @param path caminho do arquivo.
     */
    public final void loadFromFile(String path) {
        InputStream is = getClass().getResourceAsStream(path);
        
        if (is == null)
            throw new RuntimeException("Message file not found.");
        
        try {
            messages.load(is);
            is.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Define properties com mensagens.
     * @param messages mensagens.
     */
    public final void setMessages(Properties messages) {
        this.messages.putAll(messages);
    }
}