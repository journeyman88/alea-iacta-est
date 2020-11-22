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

import java.util.Optional;
import java.util.ServiceLoader;
import java.util.regex.Matcher;
import net.unknowndomain.alea.command.Command;
import net.unknowndomain.alea.command.HelpWrapper;
import net.unknowndomain.alea.messages.MsgBuilder;
import net.unknowndomain.alea.messages.ReturnMsg;
import net.unknowndomain.alea.roll.GenericRoll;
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
    
    
    protected abstract Optional<GenericRoll> safeCommand(String cmdParams);
    
    
    @Override
    public ReturnMsg execCommand(String cmdLine)
    {
        MsgBuilder errorBuilder = new MsgBuilder();
        ReturnMsg retVal = errorBuilder.append("Unexpected Error").build();
        Matcher prefixMatcher = PREFIX.matcher(cmdLine);
        if (prefixMatcher.matches())
        {
            getLogger().debug(cmdLine);
            String cmdParams = prefixMatcher.group(CMD_PARAMS);
            Optional<GenericRoll> parsedRoll;
            if (cmdParams == null || cmdParams.isEmpty())
            {
                parsedRoll = Optional.empty();
            }
            else
            {
                parsedRoll = safeCommand(cmdParams);
            }
            if (parsedRoll.isPresent())
            {
                GenericRoll roll = parsedRoll.get();
                retVal = roll.getResult().getMessage();
            }
            else
            {
                retVal = getHelpMessage(prefixMatcher.group(CMD_NAME));
            }
        }
        return retVal;
    }
    
    public abstract ReturnMsg getHelpMessage(String cmdName);
    
}
