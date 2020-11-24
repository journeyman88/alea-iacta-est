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
package net.unknowndomain.alea.systems.nottheend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.unknowndomain.alea.messages.MsgBuilder;
import net.unknowndomain.alea.roll.GenericResult;

/**
 *
 * @author journeyman
 */
public class NotTheEndResults extends GenericResult
{
    private final List<Tokens> results;
    private final List<Tokens> bag;
    private final int whites;
    private final int blacks;
    private NotTheEndResults prev;
    
    public NotTheEndResults(List<Tokens> results, List<Tokens> bag)
    {
        List<Tokens> tmp = new ArrayList<>(results.size());
        tmp.addAll(results);
        this.results = Collections.unmodifiableList(tmp);
        tmp = new ArrayList<>(bag.size());
        tmp.addAll(bag);
        this.bag = Collections.unmodifiableList(tmp);
        int w = 0 , b = 0;
        for (Tokens t : results)
        {
            if (t == Tokens.WHITE)
            {
                w++;
            }
            else
            {
                b++;
            }
        }
        whites = w;
        blacks = b;
    }

    @Override
    protected void formatResults(MsgBuilder messageBuilder, boolean verbose, int indentValue)
    {
        String indent = getIndent(indentValue);
        messageBuilder.append(indent).append("White: ").append(whites);
        messageBuilder.append(" | Black: ").append(blacks).appendNewLine();
        if (verbose)
        {
            messageBuilder.append(indent).append("Roll ID: ").append(getUuid()).appendNewLine();
            if (!results.isEmpty())
            {
                messageBuilder.append(indent).append("Results: ").append(" [ ");
                for (Tokens t : results)
                {
                    messageBuilder.append(t).append(" ");
                }
                messageBuilder.append("]\n");
            }
            if (!bag.isEmpty())
            {
                
                messageBuilder.append(indent).append("Bag content: ").append(" [ ");
                for (Tokens t : bag)
                {
                    messageBuilder.append(t).append(" ");
                }
                messageBuilder.append("]\n");
            }
            if (prev != null)
            {
                messageBuilder.append(indent).append("Prev : {\n");
                prev.formatResults(messageBuilder, verbose, indentValue + 4);
                messageBuilder.append(indent).append("}\n");
            }
        }
    }

    public NotTheEndResults getPrev()
    {
        return prev;
    }

    public void setPrev(NotTheEndResults prev)
    {
        this.prev = prev;
    }

    public List<Tokens> getResults()
    {
        return results;
    }

    public List<Tokens> getBag()
    {
        return bag;
    }

    public int getWhites()
    {
        return whites;
    }

    public int getBlacks()
    {
        return blacks;
    }

}
