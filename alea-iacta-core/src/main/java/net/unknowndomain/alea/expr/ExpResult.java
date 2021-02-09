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
package net.unknowndomain.alea.expr;

import java.util.ArrayList;
import java.util.List;
import net.unknowndomain.alea.messages.MsgBuilder;
import net.unknowndomain.alea.messages.MsgStyle;

/**
 *
 * @author journeyman
 */
public class ExpResult
{
    private Integer result;
    private String expr;
    private List<Integer> validResults = new ArrayList<>();
    private List<Integer> discardedResults = new ArrayList<>();

    public void formatVerbose(MsgBuilder msgBuilder)
    {
        boolean v = !getValidResults().isEmpty();
        boolean d = !getValidResults().isEmpty();
        if (v || d)
        {
            msgBuilder.append(getExpr());
            msgBuilder.append(" => ");
            boolean first = true;
            msgBuilder.append("(");
            if (v)
            {
                for (Integer roll : getValidResults())
                {
                    if (first)
                    {
                        first = false;
                    }
                    else 
                    {
                        msgBuilder.append(",");
                    }
                    msgBuilder.append(roll);
                }
            }
            if (d)
            {
                for (Integer roll : getDiscardedResults())
                {
                    if (first)
                    {
                        first = false;
                    }
                    else 
                    {
                        msgBuilder.append(",");
                    }
                    msgBuilder.append(roll, MsgStyle.STRIKETHRU);
                }
            }
            msgBuilder.append(")");
            msgBuilder.appendNewLine();
        }
    }
    
    public Integer getResult()
    {
        return result;
    }

    public List<Integer> getValidResults()
    {
        return validResults;
    }

    public List<Integer> getDiscardedResults()
    {
        return discardedResults;
    }

    public void setResult(Integer result)
    {
        this.result = result;
    }

    public void setValidResults(List<Integer> validResults)
    {
        this.validResults = validResults;
    }

    public void setDiscardedResults(List<Integer> discardedResults)
    {
        this.discardedResults = discardedResults;
    }

    public String getExpr()
    {
        return expr;
    }

    public void setExpr(String expr)
    {
        this.expr = expr;
    }
}
