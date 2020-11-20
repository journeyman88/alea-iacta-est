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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.unknowndomain.alea.dice.DiceBuilder;
import net.unknowndomain.alea.dice.DiceN;
import net.unknowndomain.alea.pools.DicePool;

/**
 *
 * @author journeyman
 */
public abstract class DicePart extends ExpPart
{
    private static final Pattern PATTERN = Pattern.compile("^(\\+|-?)(?<number>\\d+)(?<class>d\\d+)(.*)$");
    protected final DicePool<DiceN> dicePool;

    public DicePart(String exp)
    {
        super(exp);
        Matcher m = PATTERN.matcher(exp);
        m.find();
        Integer diceNumber = Integer.parseInt(m.group("number"));
        DiceN dice = DiceBuilder.parseDice(m.group("class"));
        dicePool = new DicePool<>(dice, diceNumber);
    }
    
}
