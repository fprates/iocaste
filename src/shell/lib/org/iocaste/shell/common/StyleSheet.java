package org.iocaste.shell.common;

import java.util.HashMap;
import java.util.Map;

public class StyleSheet {
    private Map<String, Map<String, Map<String, String>>> stylesheet;
    private Map<String, Media> media;
    private Map<Integer, String> constants;
    private String link;
    
    public StyleSheet() {
        this.stylesheet = new HashMap<>();
        this.media = new HashMap<>();
        instanceMedia("default");
    }

    public final void add(String media, Map<String, Map<String, String>> style)
    {
        stylesheet.get(media).putAll(style);
    }
    
    public final Map<String, String> clone(String media, String to, String from)
    {
        Map<String, String> clone;
        Map<String, Map<String, String>> original;
        
        original = stylesheet.get(media);
        clone = new HashMap<>(original.get(from));
        original.put(to, clone);
        return clone;
    }
    
    public final Map<String, String> clone(String to, String from) {
        return clone("default", to, from);
    }

    public final Map<String, String> get(String media, String name) {
        return stylesheet.get(media).get(name);
    }
    
    public final Map<String, String> get(String name) {
        return get("default", name);
    }
    
    public final Map<Integer, String> getConstants() {
        return constants;
    }

    public final Map<String, Map<String, String>> getElements(String media) {
        return stylesheet.get(media);
    }
    
    public final Map<String, Map<String, String>> getElements() {
        return getElements("default");
    }
    
    public final String getLink() {
        return link;
    }
    
    public final Map<String, Media> getMedias() {
        return media;
    }
    
    public final Media instanceMedia(String name) {
        Media media;
        
        media = this.media.get(name);
        if (media != null)
            return media;
        media = new Media(this.media, name);
        this.stylesheet.put(name, new HashMap<>());
        return media;
    }
    
    public final Map<String, String> newElement(String media, String name) {
        Map<String, String> element = new HashMap<>();
        
        stylesheet.get(media).put(name, element);
        return element;
    }
    
    public final Map<String, String> newElement(String name) {
        return newElement("default", name);
    }
    
    public final void remove(String media, String element, String property) {
        Map<String, String> properties = stylesheet.get(media).get(element);
        properties.remove(property);
    }
    
    public final void remove(String element, String property) {
        remove("default", element, property);
    }
    
    public final void set(String media, Map<String, Map<String, String>> styles)
    {
        this.stylesheet.put(media, styles);
    }
    
    public final void setConstants(Map<Integer, String> contants) {
        this.constants = contants;
    }
    
    public final void setLink(String link) {
        this.link = link;
    }
}
