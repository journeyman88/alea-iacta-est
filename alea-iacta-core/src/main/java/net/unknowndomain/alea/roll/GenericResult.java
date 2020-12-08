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
package net.unknowndomain.alea.roll;

import java.util.UUID;
import net.unknowndomain.alea.messages.MsgBuilder;
import net.unknowndomain.alea.messages.ReturnMsg;
import org.apache.commons.lang3.StringUtils;

/**
 * This is a common result base-class to be extended by system specific classes.
 * 
 * @author journeyman
 */
public abstract class GenericResult
{
    private boolean verbose;
    private final String uuid = UUID.randomUUID().toString();
    
    /**
     * Builds a message from this result.
     * 
     * @return the message-wrapped formatted result.
     */
    public ReturnMsg getMessage()
    {
        MsgBuilder msg = new MsgBuilder();
        formatResults(msg, verbose, 0);
        return msg.build();
    }
    
    /**
     * Formats this result and append it to the passed MsgBuilder.
     * 
     * This is the result-specific formatter method to be used to wrap the 
     * results in a message. Is called by the getMessage method and can also
     * be used to build a chain of results, in case of rerolls.
     * 
     * @param messageBuilder the message builder upon which append the formatted result.
     * @param verbose true to activate the verbose modifier.
     * @param indentValue a number of spaces to be used as indentation. 
     * @see MsgBuilder
     */
    protected abstract void formatResults(MsgBuilder messageBuilder, boolean verbose, int indentValue);
    
    /**
     * Builds the indentation prefix.
     * 
     * @param indentValue a number of spaces to be used as indentation.
     * @return a string of whitepaces, indentValue long
     */
    protected String getIndent(int indentValue)
    {
        return StringUtils.leftPad("", indentValue);
    }

    /**
     * Check if the 'verbose' modifier is set for the result.
     * 
     * @return true if the result formatting is setted as 'verbose'.
     */
    public boolean isVerbose()
    {
        return verbose;
    }

    /**
     * Sets state of the 'verbose' modifier.
     * @param verbose true to enable the 'verbose' modifier.
     */
    public void setVerbose(boolean verbose)
    {
        this.verbose = verbose;
    }

    /**
     * Gets the roll unique identifier.
     * @return the roll uuid in string format.
     * @see UUID
     */
    public String getUuid()
    {
        return uuid;
    }
}
