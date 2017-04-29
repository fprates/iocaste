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
    	int i = 0;
    	Object[][] items = new Object[map.size()][2];
    	for (Object key : map.keySet()) {
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
