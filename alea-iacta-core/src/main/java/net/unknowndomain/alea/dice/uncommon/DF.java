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
package net.unknowndomain.alea.dice.uncommon;

import net.unknowndomain.alea.dice.DiceN;

/**
 * Implementation of the Fudge Dice AKA Fate Dice
 * 
 * @author journeyman
 */
public class DF extends DiceN
{
    /**
     * The instance of this dice.
     */
    public static final DF INSTANCE = new DF();
    
    private DF()
    {
    }

    @Override
    public int getMinResult()
    {
        return -1;
    }

    /**
     * This method does not return the maximum value of the dF.
     * 
     * Due to how the DiceN superclass is implemented this method gets not the 
     * maximum face value, as for other standard DiceN implementations, but the
     * value necessary to obtain the correct range of values [-1, 0, 1].
     * 
     * @return the value to use as maximum to calculate the correct range.
     */
    @Override
    public int getMaxResult()
    {
        return 3;
    }
    
}
