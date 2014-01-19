package org.iocaste.external;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElementDetail {
    public String name, text;
    public Map<String, String> attributes;
    public List<ElementDetail> children;
    
    public ElementDetail() {
        attributes = new HashMap<>();
        children = new ArrayList<>();
    }
}
