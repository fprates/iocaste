package org.iocaste.packagetool;

import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.shell.common.AbstractContext;

public class Context extends AbstractContext {
    public Map<String, String> pkgsdata;
    public InstallData data;
}
