package org.iocaste.packagetool;

import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.shell.common.PageContext;

public class Context extends PageContext {
    public Map<String, String> pkgsdata;
    public InstallData data;
}
