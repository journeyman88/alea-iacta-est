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
import net.unknowndomain.alea.roll.StatefulRoll;

/**
 *
 * @author journeyman
 */
public class NotTheEndRisk extends NotTheEndBase implements StatefulRoll
{
    
    private NotTheEndResults prev;
    
    public NotTheEndRisk(NotTheEndModifiers ... mod)
    {
        this(Arrays.asList(mod));
    }
    
    public NotTheEndRisk(Collection<NotTheEndModifiers> mod)
    {
        super(mod);
    }
    
    @Override
    public GenericResult getResult()
    {
        NotTheEndResults results = prev;
        if (mods.contains(NotTheEndModifiers.RISK))
        {
            List<Tokens> bagPool = new ArrayList<>(prev.getBag().size());
            bagPool.addAll(prev.getBag());
            List<Tokens> current = new ArrayList<>(5);
            current.addAll(prev.getResults());
            results = extractTokens(current, bagPool, 5);
            results.setPrev(prev);
        }
        return results;
    }
    
    @Override
    public boolean loadState(GenericResult state)
    {
        boolean retVal = false;
        if (state instanceof NotTheEndResults)
        {
            prev = (NotTheEndResults) state;
            retVal = true;
        }
        return retVal;
    }
    
}
