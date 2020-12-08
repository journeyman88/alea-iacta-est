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
package net.unknowndomain.alea.messages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is a container class for the return message.
 * 
 * This is an unmodifiable class that should only be built using MsgBulder.
 * 
 * @author journeyman
 * @see MsgBuilder
 */
public class ReturnMsg
{
    private final List<MsgPart> parts;
    
    /**
     * This method builds the final message.
     * 
     * To ensure that the part list is unmodifiable, each element is copied into 
     * a new list that is then accessed only by an unmodifiable decorator.
     * 
     * @param parts the parts that form the message.
     */
    protected ReturnMsg(List<MsgPart> parts)
    {
        List<MsgPart> tmp = new ArrayList<>(parts.size());
        tmp.addAll(parts);
        this.parts = Collections.unmodifiableList(tmp);
    }
    
    /**
     * Gets the parts of the message.
     * 
     * This method is used by the bot implementation to build the message from 
     * the different parts, applying the relative syles for each part.
     * 
     * @return the unmodifiable list of parts that form the message.
     */
    public List<MsgPart> getParts()
    {
        return parts;
    }
    
    
}
