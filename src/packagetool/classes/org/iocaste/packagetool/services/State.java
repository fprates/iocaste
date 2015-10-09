package org.iocaste.packagetool.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Function;

public class State {
    public Documents documents;
    public String pkgname;
    public Map<String, Set<DocumentModelItem>> shm;
    public InstallData data;
    public Function function;
    public Map<String, Map<String, String>> messages;
    public Stack<ExtendedObject> log;
    public long pkgitem;
    public byte installed;
    
    public State() {
        log = new Stack<>();
        shm = new HashMap<>();
    }
}
