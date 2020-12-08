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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to build a ReturnMsg.
 * @author journeyman
 * @see ReturnMsg
 */
public class MsgBuilder
{
    private final List<MsgPart> parts = new ArrayList<>();
    
    /**
     * Add a new line to the builder state.
     * 
     * @return this MsgBuilder
     */
    public MsgBuilder appendNewLine()
    {
        return append("\n");
    }
    
    /**
     * Add a text to the builder state.
     * 
     * This method gets a generic Object, gets its string value and add that
     * to the builder state.
     * 
     * @param obj the object containing the message
     * @param styles the styles to apply to the message
     * @return this MsgBuilder
     */
    public MsgBuilder append(Object obj, MsgStyle ... styles)
    {
        return append(String.valueOf(obj), styles);
    }
    
    /**
     * Add a text to the builder state.
     * 
     * @param msg the text of the message
     * @param styles the styles to apply to the message
     * @return this MsgBuilder
     */
    public MsgBuilder append(String msg, MsgStyle ... styles)
    {
        MsgTextPart part = new MsgTextPart(msg, styles);
        parts.add(part);
        return this;
    }
    
    /**
     * Add a file to the builder state.
     * 
     * @param data the binary content of the file.
     * @param fileName the filename to add.
     * @return this MsgBuilder
     */
    public MsgBuilder appendFile(byte [] data, String fileName)
    {
        MsgFilePart part = new MsgFilePart(data, fileName);
        parts.add(part);
        return this;
    }
    
    /**
     * Add a URL to the builder state.
     * 
     * @param url the url to be added
     * @return this MsgBuilder
     * @see URL
     */
    public MsgBuilder appendUrl(URL url)
    {
        MsgUrlPart part = new MsgUrlPart(url);
        parts.add(part);
        return this;
    }
    
    /**
     * Add a file to the builder state.
     * 
     * @param file the file to add
     * @return this MsgBuilder 
     * @throws java.io.IOException if the file param is not readable
     * @see Path
     */
    public MsgBuilder appendFile(Path file) throws IOException
    {
        MsgFilePart part = new MsgFilePart(file);
        parts.add(part);
        return this;
    }
    
    /**
     * Add a URL to the builder state.
     * 
     * @param url the url to be added
     * @return this MsgBuilder 
     * @throws java.net.MalformedURLException if the url parameter is not a valid URL
     */
    public MsgBuilder appendUrl(String url) throws MalformedURLException
    {
        MsgUrlPart part = new MsgUrlPart(url);
        parts.add(part);
        return this;
    }
    
    /**
     * This method build the message using the current state of the builder.
     * 
     * @return the built message.
     */
    public ReturnMsg build()
    {
        return new ReturnMsg(parts);
    }
    
}
