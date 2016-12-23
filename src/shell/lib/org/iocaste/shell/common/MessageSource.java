package org.iocaste.shell.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * Mensagens.
 * 
 * @author francisco.prates
 *
 */
public class MessageSource {
    private Map<String, Properties> messages;
    private String locale;
    
    public MessageSource() {
        messages = new HashMap<>();
    }
    
    public void entries() {
        
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
    
    public final Properties getMessages(String locale) {
        return messages.get(locale);
    }
    
    public final void instance(String locale) {
        if (!this.messages.containsKey(locale))
            this.messages.put(locale, new Properties());
        this.locale = locale;
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
