/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.unknowndomain.alea.systems.shintiara;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import net.unknowndomain.alea.systems.RpgSystemOptions;
import net.unknowndomain.alea.systems.annotations.RpgSystemData;
import net.unknowndomain.alea.systems.annotations.RpgSystemOption;


/**
 *
 * @author journeyman
 */
@RpgSystemData(bundleName = "net.unknowndomain.alea.systems.shintiara.RpgSystemBundle")
public class ShintiaraOptions extends RpgSystemOptions
{
    @RpgSystemOption(name = "target", shortcode = "t", description = "shintiara.options.target", argName = "targetValue")
    private Integer targetValue;
    @RpgSystemOption(name = "advantage", shortcode = "a", description = "shintiara.options.advantage", argName = "advantageValue")
    private Integer advantageValue;
    @RpgSystemOption(name = "disadvantage", shortcode = "d", description = "shintiara.options.disadvantage", argName = "disadvantageValue")
    private Integer disadvantageValue;
    
    @Override
    public boolean isValid()
    {
        return !(isHelp() || targetValue == null);
    }

    public Integer getTargetValue()
    {
        return targetValue;
    }

    public Integer getAdvantageValue()
    {
        return advantageValue;
    }

    public Collection<ShintiaraModifiers> getModifiers()
    {
        Set<ShintiaraModifiers> mods = new HashSet<>();
        if (isVerbose())
        {
            mods.add(ShintiaraModifiers.VERBOSE);
        }
        return mods;
    }

    public Integer getDisadvantageValue()
    {
        return disadvantageValue;
    }
    
}