package org.iocaste.workbench;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Const;

public class ExecuteCommand extends AbstractActionHandler {
    private static final byte FUNCTION = 0;
    private static final byte STRING = 1;
    private static final byte PARAMETER = 2;
    private static final byte NAME = 0;
    private static final byte VALUE = 1;
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        AbstractCommand command;
        StringBuilder sb;
        byte type;
        Map<String, Object> parameters;
        String message, value, name;
        Context extcontext = getExtendedContext();
        String function = getinputst("command");
        char[] buffer = function.toCharArray();
        List<String> tokens = new ArrayList<>();
        
        type = FUNCTION;
        function = null;
        sb = new StringBuilder();
        for (int i = 0; i < buffer.length; i++) {
            switch (buffer[i]) {
            case ' ':
                if (type == STRING)
                    break;
                if (type != PARAMETER) {
                    type = PARAMETER;
                    if (function != null)
                        tokens.add(sb.toString());
                    else
                        function = sb.toString();
                    sb.setLength(0);
                    continue;
                }
                tokens.add(sb.toString());
                sb.setLength(0);
                continue;
            case '\"':
                if (type != STRING)
                    type = STRING;
                else
                    type = PARAMETER;
                break;
            }
            sb.append(buffer[i]);
        }
        
        if (function == null)
            function = sb.toString();
        else
            tokens.add(sb.toString());
        
        command = extcontext.commands.get(function);
        if (command == null) {
            message(Const.ERROR, "invalid.command");
            return;
        }
        
        parameters = new HashMap<>();
        for (String token : tokens) {
            buffer = token.toCharArray();
            name = value = null;
            type = NAME;
            sb.setLength(0);
            for (int i = 0; i < buffer.length; i++) {
                switch (buffer[i]) {
                case '=':
                    if (type == VALUE)
                        break;
                    name = sb.toString();
                    sb.setLength(0);
                    type = VALUE;
                    continue;
                case '\"':
                    continue;
                }
                sb.append(buffer[i]);
            }
            value = sb.toString();
            if (value.length() == 0)
                value = null;
            parameters.put(name, value);
        }
        
        message = command.areParametersValid(parameters);
        if (message != null) {
            message(Const.ERROR, message);
            return;
        }

        message = command.isValidContext(extcontext);
        if (message != null) {
            message(Const.ERROR, message);
            return;
        }
        
        command.set(parameters);
        execute(function);
    }

}
