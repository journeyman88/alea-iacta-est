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
package net.unknowndomain.alea.expr;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author journeyman
 */
public class DropPart extends DicePart
{
    
    private static final Pattern PATTERN = Pattern.compile("^(.*)(l|L)(?<drop>\\d+)$");
    private final int maxDice;

    public DropPart(String exp)
    {
        super(exp);
        Matcher m = PATTERN.matcher(exp);
        m.find();
        maxDice = Integer.parseInt(m.group("drop"));
    }

    @Override
    public ExpResult getResult()
    {
        int sum = 0;
        ExpResult res = new ExpResult();
        res.setExpr(getExpr());
        List<Integer> results = dicePool.getResults();
        results.sort((Integer o1, Integer o2) ->
        {
            return o1.compareTo(o2);
        });
        int limit = (maxDice > results.size()) ? results.size() : maxDice;
        for (int idx = 0; idx < results.size(); idx++)
        {
            int tmp = results.get(idx);
            if (idx < limit)
            {
                sum += tmp;
                res.getValidResults().add(tmp);
            }
            else
            {
                res.getDiscardedResults().add(tmp);
            }
        }
        res.setResult((isPositive() ? 1 : -1) * sum);
        return res;
    }
    
}
