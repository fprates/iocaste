package org.iocaste.appbuilder.common.panel;

public class PanelPageItemContextEntry {
    public PanelPageEntryType type;
    public String group, task;
}

enum PanelPageEntryType {
    TASK,
    GROUP
};