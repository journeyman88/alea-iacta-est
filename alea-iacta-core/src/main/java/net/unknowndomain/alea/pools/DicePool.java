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

import java.util.Arrays;
import java.util.Collection;
import net.unknowndomain.alea.dice.GenericDice;

/**
 * An Integer based DicePool implementation.
 * 
 * This class has been created as both a compatibility layer for already written 
 * systems, and as a shortcut for the more complete GenericDicePool.
 * 
 * @author journeyman
 * @param <T> an integer-based GenericDice class
 * @see GenericDice
 * @see GenericDicePool
 */
public class DicePool<T extends GenericDice<Integer>> extends GenericDicePool<T, Integer>
{
    
    public DicePool(T diceClass, int numberOfDice, Integer ... explodingValues)
    {
        super(diceClass, numberOfDice, Arrays.asList(explodingValues));
    }
    
    public DicePool(T diceClass, int numberOfDice, Collection<Integer> explodingValues)
    {
        super(diceClass, numberOfDice, explodingValues);
    }
    
}
