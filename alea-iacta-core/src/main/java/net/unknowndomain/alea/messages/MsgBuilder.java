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
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author journeyman
 */
public class MsgBuilder
{
    private final List<MsgPart> parts = new ArrayList<>();
    
    public MsgBuilder appendNewLine()
    {
        return append("\n");
    }
    
    public MsgBuilder append(Object obj, MsgStyle ... styles)
    {
        return append(String.valueOf(obj), styles);
    }
    
    public MsgBuilder append(String msg, MsgStyle ... styles)
    {
        MsgTextPart part = new MsgTextPart(msg, styles);
        parts.add(part);
        return this;
    }
    
    public MsgBuilder appendFile(byte [] data, String fileName)
    {
        MsgFilePart part = new MsgFilePart(data, fileName);
        parts.add(part);
        return this;
    }
    
    public MsgBuilder appendUrl(URL url)
    {
        MsgUrlPart part = new MsgUrlPart(url);
        parts.add(part);
        return this;
    }
    
    public MsgBuilder appendFile(Path file) throws IOException
    {
        MsgFilePart part = new MsgFilePart(file);
        parts.add(part);
        return this;
    }
    
    public MsgBuilder appendUrl(String url) throws IOException
    {
        MsgUrlPart part = new MsgUrlPart(url);
        parts.add(part);
        return this;
    }
    
    public ReturnMsg build()
    {
        return new ReturnMsg(parts);
    }
    
}
