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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author journeyman
 */
public class MsgTextPart implements MsgPart
{
    private final String msgText;
    private final Set<MsgStyle> msgStyle;
    
    protected MsgTextPart(String msgText, MsgStyle ... styles)
    {
        this.msgText = msgText;
        Set<MsgStyle> tmp = new HashSet<>();
        if (styles != null)
        {
            tmp.addAll(Arrays.asList(styles));
        }
        this.msgStyle = Collections.unmodifiableSet(tmp);
    }

    public String getMsgText()
    {
        return msgText;
    }

    public Set<MsgStyle> getMsgStyle()
    {
        return msgStyle;
    }
    
}
