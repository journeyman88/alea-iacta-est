/*
 * Copyright 2021 Marco Bignami.
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
package net.unknowndomain.alea.command;

import java.util.Optional;
import net.unknowndomain.alea.messages.ReturnMsg;

/**
 * The basic class for internal commands
 * 
 * @author journeyman
 */
public abstract class BasicCommand extends Command
{
    
    /**
     * Exec this command.
     * This method execute the command specified in the cmdLine parameter, and 
     * builds a wrapped message with the results.
     * Is best to call the checkCommand method beforehand, to see if the commandline
     * can be interpreted by the implementation.
     * 
     * @param cmdLine The commandline which is interpreted by the implementation
     * @param callerId An Optional that may contain a caller id number, used for rerolls.
     * @return The return message wrapper which contains the command results.
     * @see ReturnMsg
     * @see Optional
     */
    public abstract ReturnMsg execCommand(String cmdLine, Optional<Long> callerId);
}
