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
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.unknowndomain.alea.roll.GenericRoll;

/**
 *
 * @author journeyman
 */
public abstract class NotTheEndBase implements GenericRoll
{
    
    protected final Set<NotTheEndModifiers> mods;

    public NotTheEndBase(Collection<NotTheEndModifiers> mod)
    {
        this.mods = new HashSet<>();
        this.mods.addAll(mod);
    }

    protected NotTheEndResults extractTokens(List<Tokens> currentResult, List<Tokens> bagPool, int extract)
    {
        Collections.shuffle(bagPool);
        int currentPos = currentResult.size();
        for (int i = currentPos; i < extract; i++)
        {
            currentResult.add(bagPool.remove(0));
        }
        NotTheEndResults results = new NotTheEndResults(currentResult, bagPool);
        results.setVerbose(mods.contains(NotTheEndModifiers.VERBOSE));
        return results;
    }
    
}
