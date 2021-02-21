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
package net.unknowndomain.alea.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The generic command base-class.
 * This is the superclass of every command used in the system, both base commands
 * and pluggable RPG-specific commands.
 * 
 * @author journeyman
 */
public abstract class Command
{
    /**
     * Helper String constant to access the command name.
     */
    public static final String CMD_NAME = "command";
    
    /**
     * Helper String constant to access the command parameters.
     */
    public static final String CMD_PARAMS = "parameters";
    
    /**
     * Helper String constant for the common 'help' parameter.
     */
    public static final String CMD_HELP = "help";
    
    /**
     * Helper String constant for the common 'verbose' parameter.
     */
    public static final String CMD_VERBOSE = "verbose";
    
    /**
     * The Pattern used to match the command.
     * @see Pattern
     */
    protected final Pattern PREFIX = Pattern.compile("^(?<command>" + getCommandRegex() + ")(( )(?<parameters>.*))?$");
    
    /**
     * Gets the regex that matches this command.
     * 
     * @return the command matching regex in String form.
     */
    protected abstract String getCommandRegex();
    
    /**
     * Check if this system can interpret the given command-line.
     * 
     * @param cmdLine the input command line
     * @return true if the command line patches the command.
     */
    public boolean checkCommand(String cmdLine)
    {
        Matcher matcher = PREFIX.matcher(cmdLine);
        return matcher.matches();
    }
    
}
