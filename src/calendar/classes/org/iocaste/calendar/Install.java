package org.iocaste.calendar;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;

public class Install {

    public static final InstallData init() {
        Map<String, String> messages;
        InstallData data = new InstallData();
        
        messages = new HashMap<>();
        messages.put("sunday", "dom");
        messages.put("monday", "seg");
        messages.put("tuesday", "ter");
        messages.put("wednesday", "qua");
        messages.put("thursday", "qui");
        messages.put("friday", "sex");
        messages.put("saturday", "sáb");
        data.setMessages("pt_BR", messages);
        
        return data;
    }
}
