package org.iocaste.external.service;

import java.io.Serializable;

public interface ExternalMessageProtocol extends Serializable {

    public abstract ExternalPropertyProtocol[] getProperties();

}