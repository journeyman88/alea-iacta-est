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
package net.unknowndomain.alea.dice;

import net.unknowndomain.alea.dice.standard.*;
import net.unknowndomain.alea.dice.uncommon.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This is to be used as the entry-point to build a custom DiceN.
 * 
 * Since the system has a set of common and less common polyhedral dice preset,
 * this builder can - if a match is found - return a known instance instead of a 
 * custom anonymous class for optimization reason.
 * 
 * @author journeyman
 */
public class DiceBuilder
{
    
    private final static Map<String, DiceN> REGISTRY;
    
    static {
        REGISTRY = new HashMap<>();
        REGISTRY.put("dF"  , DF.INSTANCE);
        REGISTRY.put("d3"  , D3.INSTANCE);
        REGISTRY.put("d4"  , D4.INSTANCE);
        REGISTRY.put("d5"  , D5.INSTANCE);
        REGISTRY.put("d6"  , D6.INSTANCE);
        REGISTRY.put("d7"  , D7.INSTANCE);
        REGISTRY.put("d8"  , D8.INSTANCE);
        REGISTRY.put("d9"  , D9.INSTANCE);
        REGISTRY.put("d10" , D10.INSTANCE);
        REGISTRY.put("d11" , D11.INSTANCE);
        REGISTRY.put("d12" , D12.INSTANCE);
        REGISTRY.put("d13" , D13.INSTANCE);
        REGISTRY.put("d14" , D14.INSTANCE);
        REGISTRY.put("d15" , D15.INSTANCE);
        REGISTRY.put("d16" , D16.INSTANCE);
        REGISTRY.put("d18" , D18.INSTANCE);
        REGISTRY.put("d20" , D20.INSTANCE);
        REGISTRY.put("d22" , D22.INSTANCE);
        REGISTRY.put("d24" , D24.INSTANCE);
        REGISTRY.put("d30" , D30.INSTANCE);
        REGISTRY.put("d100", D100.INSTANCE);
    }
    
    /**
     * Builds a DiceN instance given the diceClass.
     * 
     * @param dX the diceClass in standard dice annotation.
     * @return the DiceN instance
     */
    public static final DiceN parseDice(String dX)
    {
        if (REGISTRY.containsKey(dX))
        {
            return REGISTRY.get(dX);
        }
        int x = Integer.parseInt(dX.replaceAll("d", ""));
        return new DiceN()
        {
            @Override
            public int getMinResult()
            {
                return 1;
            }

            @Override
            public int getMaxResult()
            {
                return x;
            }
        };
    }
    
    /**
     * Builds a DiceN instance given the maximum Value.
     * 
     * This builds a DiceN with minimum value of 1 and maximum value of X.
     * 
     * @param X the DiceN maximum value
     * @return the DiceN instance
     */
    public static final DiceN parseDice(Integer X)
    {
        String dX = "d" + X;
        return parseDice(dX);
    }
}
