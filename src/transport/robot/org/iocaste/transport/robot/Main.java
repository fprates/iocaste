package org.iocaste.transport.robot;

import org.iocaste.external.common.AbstractExternalApplication;
import org.iocaste.protocol.Message;

public class Main extends AbstractExternalApplication {
	
	@Override
	protected final void config() {
		required("--file", KEY_VALUE);
		option("--language", KEY_VALUE, "pt_BR");
	}
	
	@Override
	protected final void execute(Message message) {
		
	}
	
	public static final void main(String[] args) {
		new Main().init(args);
	}
}
