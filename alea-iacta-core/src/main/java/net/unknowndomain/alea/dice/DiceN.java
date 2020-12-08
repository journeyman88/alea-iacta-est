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

import java.util.concurrent.ThreadLocalRandom;

/**
 * This is the base class to implement numeric dice with ascending numbers.
 * 
 * @author journeyman
 */
public abstract class DiceN implements GenericDice<Integer>
{
    /**
     * Gets the minimum result of this dice.
     * 
     * @return the minimum result of the dice.
     */
    public abstract int getMinResult();
    
    /**
     * Gets the maximum result of this dice.
     * 
     * @return the maximum result of the dice.
     */
    public abstract int getMaxResult();
    
    @Override
    public Integer roll()
    {
        int result = ThreadLocalRandom.current().nextInt(getMaxResult());
        result += getMinResult();
        return result;
    }
}
