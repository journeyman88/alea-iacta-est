/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.unknowndomain.alea.systems.age;

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
@RpgSystemData(bundleName = "net.unknowndomain.alea.systems.age.RpgSystemBundle")
public class AGEOptions extends RpgSystemOptions
{
    @RpgSystemOption(name = "ability", shortcode = "a", description = "age.options.ability", argName = "abilityValue")
    private Integer ability;
    @RpgSystemOption(name = "bonus", shortcode = "b", description = "age.options.bonus", argName = "bonusValue")
    private Integer bonus;
    @RpgSystemOption(name = "focus", shortcode = "f", description = "age.options.focus")
    private boolean focus;
    
    @Override
    public boolean isValid()
    {
        return !(isHelp());
    }

    public Integer getAbility()
    {
        return ability;
    }

    public Integer getBonus()
    {
        return bonus;
    }
    
    public boolean isFocus()
    {
        return focus;
    }
    
    public Integer getTotal()
    {
        int tot = 0;
        if (ability != null)
        {
            tot += ability;
        }
        if (bonus != null)
        {
            tot += bonus;
        }
        if (focus)
        {
            tot += 2;
        }
        return tot;
    }

    public Collection<AGEModifiers> getModifiers()
    {
        Set<AGEModifiers> mods = new HashSet<>();
        if (isVerbose())
        {
            mods.add(AGEModifiers.VERBOSE);
        }
        return mods;
    }
    
}