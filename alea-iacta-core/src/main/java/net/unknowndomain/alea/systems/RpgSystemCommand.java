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

import java.util.Locale;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.concurrent.TimeUnit;
import net.unknowndomain.alea.command.Command;
import net.unknowndomain.alea.messages.ReturnMsg;
import net.unknowndomain.alea.roll.GenericResult;
import net.unknowndomain.alea.roll.GenericRoll;
import net.unknowndomain.alea.roll.StatefulRoll;
import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;
import org.slf4j.Logger;

/**
 * This is both the service entry-point for the pluggable RPGs and the basic system command.
 * This class w
 * @author journeyman
 * @see Command
 */
public abstract class RpgSystemCommand extends Command
{
    /**
     * This is the service loader used to access the pluggable-RPGs specific implementations.
     */
    public static final ServiceLoader<RpgSystemCommand> LOADER = ServiceLoader.load(RpgSystemCommand.class);
    
    private static final Cache<Long, GenericResult> RESULT_CACHE = new Cache2kBuilder<Long, GenericResult>() {}
            .expireAfterWrite(2, TimeUnit.MINUTES)
            .build();
    
    
    @Override
    public String getCommandRegex()
    {
        return getCommandDesc().getShortcut() + "|" + getCommandDesc().getCommand();
    }
    
    /**
     * Gets the descriptor of this RPG system.
     * 
     * @return A descriptor of this system.
     * @see RpgSystemDescriptor
     */
    public abstract RpgSystemDescriptor getCommandDesc();
    
    /**
     * Gets the implementation Logger to use in the shared methods.
     * 
     * @return returns the implementation logger.
     * @see Logger
     */
    protected abstract Logger getLogger();
    
    
    /**
     * Execute this command and builds the relative roll.This method uses the parameters to build a system-specific roll wrapped in a null-safe Optional.
     * 
     * The command is then invoked as part of the execCommand method.
     * 
     * @param lang the language to use for the translation
     * @param options the command parameters
     * @return An Optional containing the roll build by this command
     * @see GenericRoll
     * @see Optional
     */
    protected abstract Optional<GenericRoll> safeCommand(RpgSystemOptions options, Locale lang);
    
    
    /**
     * Execute this command and builds the relative roll.This method uses the parameters to build a system-specific roll wrapped in a null-safe Optional.
     * 
     * The command is then invoked as part of the execCommand method.
     * 
     * @return An Optional containing the roll build by this command
     * @see RpgSystemOptions
     */
    public abstract RpgSystemOptions buildOptions();
    
    public Optional<ReturnMsg> execCommand(RpgSystemOptions options, Optional<Long> callerId)
    {
        return execCommand(options, Locale.ENGLISH, callerId);
    }
    
    public Optional<ReturnMsg> execCommand(RpgSystemOptions options, Locale lang, Optional<Long> callerId)
    {
        Optional<ReturnMsg> retVal = Optional.empty();
        if (options.isValid())
        {
            Optional<GenericRoll> parsedRoll = safeCommand(options, lang);
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
                        return retVal;
                    }
                }
                result = roll.getResult();
                if (callerId.isPresent())
                {
                    Long id = callerId.get();
                    RESULT_CACHE.put(id, result);
                }
                retVal = Optional.of(result.getMessage());
            }
        }
        return retVal;
    }
    
}
