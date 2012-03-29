package org.iocaste.shell.common;

import java.io.Serializable;

public interface Validator extends Serializable {

    public abstract String validate(ValidatorConfig config) throws Exception;
}
