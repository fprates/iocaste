package org.iocaste.kernel.packages;

import org.iocaste.protocol.AbstractFunction;

public class Packages extends AbstractFunction {

    public Packages() {
        export("packages_get", new PackagesGet());
    }
}
