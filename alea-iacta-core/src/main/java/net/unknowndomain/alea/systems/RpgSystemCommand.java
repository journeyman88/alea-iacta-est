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
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import net.unknowndomain.alea.command.Command;
import net.unknowndomain.alea.messages.MsgBuilder;
import net.unknowndomain.alea.messages.ReturnMsg;
import net.unknowndomain.alea.roll.GenericResult;
import net.unknowndomain.alea.roll.GenericRoll;
import net.unknowndomain.alea.roll.StatefulRoll;
import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;
import org.slf4j.Logger;

/**
 *
 * @author journeyman
 */
public abstract class RpgSystemCommand extends Command
{
    
    public static final ServiceLoader<RpgSystemCommand> LOADER = ServiceLoader.load(RpgSystemCommand.class);
    
    private static final Cache<Long, GenericResult> RESULT_CACHE = new Cache2kBuilder<Long, GenericResult>() {}
            .expireAfterWrite(2, TimeUnit.MINUTES)
            .build();
    
    
    @Override
    protected String getCommandRegex()
    {
        return getCommandDesc().getShortcut() + "|" + getCommandDesc().getCommand();
    }
    
    public abstract RpgSystemDescriptor getCommandDesc();
    
    protected abstract Logger getLogger();
    
    
    protected abstract Optional<GenericRoll> safeCommand(String cmdParams);
    
    
    @Override
    public ReturnMsg execCommand(String cmdLine, Optional<Long> callerId)
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
                GenericResult result = null;
                if (callerId.isPresent())
                {
                    Long id = callerId.get();
                    if (RESULT_CACHE.containsKey(id))
                    {
                        result = RESULT_CACHE.peek(id);
                    }
                }
                if ((roll instanceof StatefulRoll) && (result != null))
                {
                    StatefulRoll stateFul = (StatefulRoll) roll;
                    boolean chkLoad = stateFul.loadState(result);
                    if (!chkLoad)
                    {
                        return getHelpMessage(prefixMatcher.group(CMD_NAME));
                    }
                }
                result = roll.getResult();
                if (callerId.isPresent())
                {
                    Long id = callerId.get();
                    RESULT_CACHE.put(id, result);
                }
                retVal = result.getMessage();
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
