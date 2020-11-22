/*
 * Copyright 2020 Marco Bignami.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.unknowndomain.alea.systems;

import java.util.ServiceLoader;
import java.util.regex.Matcher;
import net.unknowndomain.alea.command.Command;
import net.unknowndomain.alea.messages.MsgBuilder;
import net.unknowndomain.alea.messages.ReturnMsg;
import org.slf4j.Logger;

/**
 *
 * @author journeyman
 */
public abstract class RpgSystemCommand extends Command
{
    
    public static final ServiceLoader<RpgSystemCommand> LOADER = ServiceLoader.load(RpgSystemCommand.class);
    
    
    @Override
    protected String getCommandRegex()
    {
        return getCommandDesc().getShortcut() + "|" + getCommandDesc().getCommand();
    }
    
    public abstract RpgSystemDescriptor getCommandDesc();
    
    protected abstract Logger getLogger();
    
    
    protected abstract ReturnMsg safeCommand(String cmdName, String cmdParams);
    
    
    @Override
    public ReturnMsg execCommand(String cmdLine)
    {
        MsgBuilder errorBuilder = new MsgBuilder();
        ReturnMsg retVal = errorBuilder.append("Unexpected Error").build();
        Matcher prefixMatcher = PREFIX.matcher(cmdLine);
        if (prefixMatcher.matches())
        {
            getLogger().debug(cmdLine);
            retVal = safeCommand(prefixMatcher.group(CMD_NAME), prefixMatcher.group(CMD_PARAMS));
        }
        return retVal;
    }
    
}
