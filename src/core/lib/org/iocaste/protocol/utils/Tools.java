package org.iocaste.protocol.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Tools {
	public enum TYPE {
		HASH
	};
    
    public static final Object[][] toArray(Map<?,?> map) {
        return toArray(map, (String)null);
    }
    
    public static final Object[][] toArray(Map<?,?> map, String... excluding) {
        Set<String> keys;
        Object[][] items;
        int i;
        
        if ((excluding != null) && (excluding.length > 0)) {
            keys = new HashSet<>();
            for (String key : excluding) {
                if (!map.containsKey(key))
                    continue;
                keys.add(key);
            }
            items = new Object[map.size() - keys.size()][2];
        } else {
            keys = null;
            items = new Object[map.size()][2];
        }
        
        i = 0;
        for (Object key : map.keySet()) {
            if ((keys != null) && keys.contains(key))
                continue;
            items[i][0] = key;
            items[i++][1] = map.get(key);
        }
        return items;
    }
    
    @SuppressWarnings("unchecked")
	public static final <K, L> Map<K, L> toMap(TYPE type, Object[][] values) {
    	Map<K, L> map = null;
    	switch (type) {
    	case HASH:
    		map = new HashMap<>();
    		break;
    	}
    	for (int i = 0; i < values.length; i++)
    		map.put((K)values[i][0], (L)values[i][1]);
    	return (Map<K,L>)map;
    }
    
    @SuppressWarnings("unchecked")
	public static final <K> Set<K> toSet(TYPE type, Object[] values) {
    	Set<K> set = null;
    	switch (type) {
    	case HASH:
    		set = new HashSet<>();
    		break;
    	}
    	for (int i = 0; i < values.length; i++)
    		set.add((K)values[i]);
    	return (Set<K>)set;
    }
}
