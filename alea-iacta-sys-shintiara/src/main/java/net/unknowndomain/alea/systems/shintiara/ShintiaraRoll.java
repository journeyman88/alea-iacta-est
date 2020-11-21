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
package net.unknowndomain.alea.systems.shintiara;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import net.unknowndomain.alea.dice.D10;
import net.unknowndomain.alea.dice.D100;
import net.unknowndomain.alea.roll.GenericRoll;
import org.javacord.api.entity.message.MessageBuilder;

/**
 *
 * @author journeyman
 */
public class ShintiaraRoll implements GenericRoll
{
    
    public enum Modifiers
    {
        VERBOSE
    }
    
    private final int target;
    private final int netVantage;
    private final Set<Modifiers> mods;
    
    public ShintiaraRoll(Integer target, Integer advantage, Integer disadvantage, Modifiers ... mod)
    {
        this(target, advantage, disadvantage, Arrays.asList(mod));
    }
    
    public ShintiaraRoll(Integer target, Integer advantage, Integer disadvantage, Collection<Modifiers> mod)
    {
        this.mods = new HashSet<>();
        if (mod != null)
        {
            this.mods.addAll(mod);
        }
        if ((advantage == null) || (advantage < 0))
        {
            advantage = 0;
        }
        else if (advantage > 9)
        {
            advantage = 9;
        }
        if ((disadvantage == null) || (disadvantage < 0))
        {
            disadvantage = 0;
        }
        else if (disadvantage > 9)
        {
            disadvantage = 9;
        }
        this.netVantage = advantage - disadvantage;
        this.target = target;
    }
    
    @Override
    public MessageBuilder getResult()
    {
        ShintiaraResults results = buildResults(D100.INSTANCE.roll(), (D10.INSTANCE.roll() -1));
        return formatResults(results);
    }
    
    private MessageBuilder formatResults(ShintiaraResults results)
    {
        MessageBuilder mb = new MessageBuilder();
        if (results.isAutoSuccess())
        {
            mb.append("Automatic success");
        }
        else if (results.isCriticalSuccess())
        {
            mb.append("Critical success");
        }
        else if (results.isCriticalFailure())
        {
            mb.append("Critical failure");
        }
        else if (results.isSuccess())
        {
            mb.append("Success");
        }
        else
        {
            mb.append("Failure");
        }
        mb.appendNewLine();
        if (mods.contains(Modifiers.VERBOSE))
        {
            mb.append("Result: ").append(results.getResult());
            if (netVantage != 0)
            {
                mb.append(" Asset: ").append(results.getAssetResult() * 10);
            }
            mb.appendNewLine();
        }
        return mb;
    }
    
    private ShintiaraResults buildResults(Integer result, Integer assetDice)
    {
        ShintiaraResults results = new ShintiaraResults(result, assetDice);
        BigDecimal tgt = new BigDecimal(target);
        int criticalThreshold = tgt.divide(new BigDecimal(2), 0, RoundingMode.CEILING).intValue();
        if (result == 1)
        {
            results.setAutoSuccess(true);
        }
        if (result <= criticalThreshold)
        {
            results.setCriticalSuccess(true);
        }
        if (result >= 95)
        {
            results.setCriticalFailure(true);
            results.setSuccess(false);
        }
        else
        {
            int vantage = 0;
            if (netVantage != 0)
            {
                vantage = (Math.abs(netVantage) < assetDice) ? Math.abs(netVantage) : assetDice; 
                vantage = (netVantage > 0) ? vantage * -10 : vantage * 10;
            }
            int adjRes = result + vantage;
            results.setSuccess((adjRes <= target));
        }
        return results;
    }
}
