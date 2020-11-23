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
package net.unknowndomain.alea.systems.shadowrun5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.unknowndomain.alea.dice.standard.D6;
import net.unknowndomain.alea.pools.DicePool;
import net.unknowndomain.alea.roll.GenericResult;
import net.unknowndomain.alea.roll.GenericRoll;

/**
 *
 * @author journeyman
 */
public class Shadowrun5Roll implements GenericRoll
{
    
    public enum Modifiers
    {
        VERBOSE,
        PUSH_THE_LIMIT,
        SECOND_CHANCE
    }
    
    private final DicePool<D6> dicePool;
    private final Integer limit;
    private final Set<Modifiers> mods;
    
    public Shadowrun5Roll(Integer dice, Modifiers ... mod)
    {
        this(dice, Arrays.asList(mod));
    }
    
    public Shadowrun5Roll(Integer trait, Integer limit, Modifiers ... mod)
    {
        this(trait, limit, Arrays.asList(mod));
    }
    
    public Shadowrun5Roll(Integer dice, Collection<Modifiers> mod)
    {
        this(dice, null, mod);
    }
    
    public Shadowrun5Roll(Integer dice, Integer limit, Collection<Modifiers> mod)
    {
        this.mods = new HashSet<>();
        this.mods.addAll(mod);
        if (mods.contains(Modifiers.PUSH_THE_LIMIT))
        {
            this.dicePool = new DicePool<>(D6.INSTANCE, dice, 6);
        }
        else
        {
            this.dicePool = new DicePool<>(D6.INSTANCE, dice);
        }
        this.limit = limit;
    }
    
    @Override
    public GenericResult getResult()
    {
        List<Integer> resultsPool = this.dicePool.getResults();
        List<Integer> res = new ArrayList<>();
        res.addAll(resultsPool);
        Shadowrun5Results results = buildIncrements(res);
        Shadowrun5Results results2 = null;
        if (mods.contains(Modifiers.SECOND_CHANCE) && (results.getMiss() > 0) )
        {
            DicePool<D6> reroll;
            if (mods.contains(Modifiers.PUSH_THE_LIMIT))
            {
                reroll = new DicePool<>(D6.INSTANCE, results.getMiss(), 10);
            }
            else
            {
                reroll = new DicePool<>(D6.INSTANCE, results.getMiss());
            }
            boolean done = false;
            res = new ArrayList<>();
            res.addAll(reroll.getResults());
            res.addAll(results.getHitResults());
            results2 = results;
            results = buildIncrements(res);
        }
        results.setPush(mods.contains(Modifiers.PUSH_THE_LIMIT));
        results.setVerbose(mods.contains(Modifiers.VERBOSE));
        return results;
    }
    
    private Shadowrun5Results buildIncrements(List<Integer> res)
    {
        res.sort((Integer o1, Integer o2) ->
        {
            return -1 * o1.compareTo(o2);
        });
        Shadowrun5Results results = new Shadowrun5Results(res);
        int max = res.size();
        for (int i = 0; i < max; i++)
        {
            int temp = res.remove(0);
            if (temp >= 5)
            {
                results.addHit(temp);
            }
            else if (temp > 1)
            {
                results.addMiss();
            }
            else
            {
                results.addOne();
            }
        }
        results.setLimit(limit);
        return results;
    }
}
