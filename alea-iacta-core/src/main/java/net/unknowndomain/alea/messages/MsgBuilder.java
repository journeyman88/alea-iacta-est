/*
 * Copyright 2020 journeyman.
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
import java.util.List;

/**
 *
 * @author journeyman
 */
public class MsgBuilder
{
    private final List<MsgPart> parts = new ArrayList<>();
    
//    public MsgBuilder append(boolean bool, MsgStyle ... styles)
//    {
//        return append(String.valueOf(bool), styles);
//    }
//    
//    public MsgBuilder append(double number, MsgStyle ... styles)
//    {
//        return append(String.valueOf(number), styles);
//    }
//    
//    public MsgBuilder append(float number, MsgStyle ... styles)
//    {
//        return append(String.valueOf(number), styles);
//    }
//    
//    public MsgBuilder append(int number, MsgStyle ... styles)
//    {
//        return append(String.valueOf(number), styles);
//    }
//    
//    public MsgBuilder append(long number, MsgStyle ... styles)
//    {
//        return append(String.valueOf(number), styles);
//    }
//    
//    public MsgBuilder append(short number, MsgStyle ... styles)
//    {
//        return append(String.valueOf(number), styles);
//    }
    
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
        MsgPart part = new MsgPart(msg, styles);
        parts.add(part);
        return this;
    }
    
    public ReturnMsg build()
    {
        return new ReturnMsg(parts);
    }
    
}
