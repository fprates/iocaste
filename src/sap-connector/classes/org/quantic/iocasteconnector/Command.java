package org.quantic.iocasteconnector;

import org.iocaste.documents.common.ComplexDocument;

import com.sap.conn.jco.JCoDestination;

public class Command {
    public int error;
    public String locale, port;
    public ComplexDocument portconfig;
    public JCoDestination destination;
    public RFCDataProvider provider;
}
