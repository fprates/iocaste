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
        Map<String, String> clone, source;
        Map<String, Map<String, String>> original;
        
        original = stylesheet.get(media);
        source = original.get(from);
        if (source == null)
            source = stylesheet.get("default").get(from);
        clone = new HashMap<>(source);
        original.put(to, clone);
        return clone;
    }
    
    public final Map<String, String> clone(String to, String from) {
        return clone("default", to, from);
    }
    
    public static final Object[][] convertStyleSheet(StyleSheet stylesheet) {
        Object[][] sheet;
        int l;
        Media media;
        Map<String, Media> medias;
        
        l = 0;
        medias = stylesheet.getMedias();
        sheet = new Object[medias.size()][3];
        for (String key : medias.keySet()) {
            media = medias.get(key);
            sheet[l][0] = key;
            sheet[l][1] = media.getRule();
            sheet[l++][2] = stylesheet.getElements(key);
        }
        return sheet;
    }
    
    public final void export(View view) {
        Object[][] styleconst = new Object[constants.size()][2];
        for (Integer i : constants.keySet()) {
            styleconst[i][0] = i;
            styleconst[i][1] = constants.get(i);
        }
        
        view.setStyleSheet(convertStyleSheet(this));
        view.setStyleConst(styleconst);
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
    
    @SuppressWarnings("unchecked")
    public static final StyleSheet instance(View view) {
        String mediakey;
        StyleSheet stylesheet;
        Media media;
        Map<Integer, String> constants;
        Object[][] sheet, styleconst;
        
        if (view != null) {
            sheet = view.getStyleSheet();
            styleconst = view.getStyleConstants();
        } else {
            sheet = styleconst = null;
        }
        
        stylesheet = new StyleSheet();
        if (sheet != null)
            for (int i = 0; i < sheet.length; i++) {
                mediakey = (String)sheet[i][0];
                media = stylesheet.instanceMedia(mediakey);
                media.setRule((String)sheet[i][1]);
                stylesheet.set(mediakey,
                        (Map<String, Map<String, String>>)sheet[i][2]);
            }
        
        if (styleconst != null) {
            constants = new HashMap<>();
            for (int i = 0; i < styleconst.length; i++)
                constants.put((int)styleconst[i][0], (String)styleconst[i][1]);
            stylesheet.setConstants(constants);
        }
        
        return stylesheet;
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
