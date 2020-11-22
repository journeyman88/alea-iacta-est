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
package net.unknowndomain.alea.systems.lexarcana2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.unknowndomain.alea.dice.DiceBuilder;
import net.unknowndomain.alea.dice.DiceN;
import net.unknowndomain.alea.messages.MsgBuilder;
import net.unknowndomain.alea.messages.ReturnMsg;
import net.unknowndomain.alea.roll.GenericRoll;

/**
 *
 * @author journeyman
 */
public class LexArcana2Roll implements GenericRoll
{
    
    public enum Modifiers
    {
        VERBOSE
    }
    
    private final List<DiceN> diceList;
    private final Set<Modifiers> mods;
    
    
    public LexArcana2Roll(Integer trait, Integer skill, Integer bonus, Modifiers ... mod)
    {
        this(trait, skill, bonus, Arrays.asList(mod));
    }
    
    public LexArcana2Roll(Integer first, Integer second, Integer third, Collection<Modifiers> mod)
    {
        this.mods = new HashSet<>();
        if (mod != null)
        {
            this.mods.addAll(mod);
        }
        List<DiceN> tmp = new ArrayList<>();
        tmp.add(DiceBuilder.parseDice(first));
        if (second > 0)
        {
            tmp.add(DiceBuilder.parseDice(second));
        }
        if (third > 0)
        {
            tmp.add(DiceBuilder.parseDice(third));
        }
        this.diceList = Collections.unmodifiableList(tmp);
    }
    
    @Override
    public ReturnMsg getResult()
    {
        LexArcana2Results results = buildResults();
        return formatResults(results);
    }
    
    private ReturnMsg formatResults(LexArcana2Results results)
    {
        MsgBuilder mb = new MsgBuilder();
        mb.append("Total: ").append(results.getTotal()).appendNewLine();
        if (mods.contains(Modifiers.VERBOSE))
        {
            mb.append("Results: ").append(" [ ");
            int round = 1;
            for (Integer t : results.getResults())
            {
                if (round == 1)
                {
                    mb.append("( ");
                }
                mb.append(t);
                if (round == this.diceList.size())
                {
                    mb.append("); ");
                    round = 1;
                }
                else
                {
                    mb.append(", ");
                    round++;
                }
            }
            mb.append("]\n");
        }
        return mb.build();
    }
    
    private LexArcana2Results buildResults()
    {
        List<Integer> res = new ArrayList<>(diceList.size());
        boolean fateRoll = true;
        while(fateRoll)
        {
            for (DiceN dn : diceList)
            {
                int r = dn.roll();
                fateRoll = fateRoll && (r == dn.getMaxResult());
                res.add(r);
            }
        }
        LexArcana2Results results = new LexArcana2Results(res);
        return results;
    }
    
    
}
