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
package net.unknowndomain.alea.systems.nottheend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import net.unknowndomain.alea.roll.GenericResult;

/**
 *
 * @author journeyman
 */
public class NotTheEndExtract extends NotTheEndBase
{
    
    private final List<Tokens> bag;
    private final int extract;
    
    public NotTheEndExtract(Integer trait, Integer difficulty, Integer extract, NotTheEndModifiers ... mod)
    {
        this(trait, difficulty, extract, Arrays.asList(mod));
    }
    
    public NotTheEndExtract(Integer traits, Integer difficulty, Integer extract, Collection<NotTheEndModifiers> mod)
    {
        super(mod);
        bag = new ArrayList<>();
        int i;
        for (i = 0; i < traits; i++)
        {
            bag.add(Tokens.WHITE);
        }
        for (i = 0; i < difficulty; i++)
        {
            bag.add(Tokens.BLACK);
        }
        if (extract > 4)
        {
            extract = 4;
        }
        if (extract < 1)
        {
            extract = 1;
        }
        this.extract = extract;
    }
    
    @Override
    public GenericResult getResult()
    {
        List<Tokens> bagPool = new ArrayList<>(bag.size());
        bagPool.addAll(bag);
        List<Tokens> result = new ArrayList<>(extract);
        NotTheEndResults results = extractTokens(result, bagPool, extract);
        return results;
    }
    
}
