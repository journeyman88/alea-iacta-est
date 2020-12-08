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
package net.unknowndomain.alea.pools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.unknowndomain.alea.dice.GenericDice;

/**
 * This is a generic DicePool implementation.
 * 
 * @author journeyman
 * @param <T> The GenericDice to use for building the results.
 * @param <V> The class of a single result.
 */
public class GenericDicePool<T extends GenericDice<V>, V>
{
    private final T diceClass;
    private final Integer numberOfDice;
    private final Set<V> explodingValues;
    
    /**
     * Builds the GenericDicePool.
     * 
     * @param diceClass the instance of a Generic Dice to be used in the pool rool.
     * @param numberOfDice the number of dice that builds the pool.
     * @param explodingValues the set of values that cause dice explosion in varargs form.
     */
    public GenericDicePool(T diceClass, int numberOfDice, V ... explodingValues)
    {
        this(diceClass, numberOfDice, Arrays.asList(explodingValues));
    }
    
    /**
     * Builds the GenericDicePool.
     * 
     * @param diceClass the instance of a Generic Dice to be used in the pool rool.
     * @param numberOfDice the number of dice that builds the pool.
     * @param explodingValues the set of values that cause dice explosion as a Collection.
     */
    public GenericDicePool(T diceClass, int numberOfDice, Collection<V> explodingValues)
    {
        this.diceClass = diceClass;
        this.numberOfDice = numberOfDice;
        Set<V> tmpSet = new HashSet<>();
        if ((explodingValues != null) && !explodingValues.isEmpty())
        {
            tmpSet.addAll(explodingValues);
        }
        this.explodingValues = Collections.unmodifiableSet(tmpSet);
    }
    
    /**
     * Gets a list of results for this pool.
     * 
     * This method toss the dice contained in the pool to determine the results,
     * also checking if any single result matches the Set of results that cause 
     * a 'dice explosion'.
     * 
     * @return the results.
     */
    public List<V> getResults()
    {
        List<V> results = new ArrayList<>(numberOfDice);
        int idx = 0;
        while(idx < numberOfDice)
        {
            idx++;
            V result = diceClass.roll();
            if (explodingValues.contains(result))
            {
                idx--;
            }
            results.add(result);
        }
        return results;
    }
}
