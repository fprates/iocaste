package org.iocaste.protocol.database;

import java.io.Serializable;
import java.util.Date;

public class ConnectionInfo implements Serializable {
    private static final long serialVersionUID = -3216986528549484799L;
    public boolean assigned;
    public int connid;
    public String sessionid;
    public Date createdon;
}