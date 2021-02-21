/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.unknowndomain.alea.systems.lexarcana2;

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
@RpgSystemData(bundleName = "net.unknowndomain.alea.systems.lexarcana2.RpgSystemBundle")
public class LexArcana2Options extends RpgSystemOptions
{
    @RpgSystemOption(name = "first-dice", shortcode = "f", description = "lexarcana2.options.first", argName = "firstDice")
    private Integer firstDice;
    @RpgSystemOption(name = "second-dice", shortcode = "s", description = "lexarcana2.options.second", argName = "secondDice")
    private Integer secondDice;
    @RpgSystemOption(name = "third-dice", shortcode = "t", description = "lexarcana2.options.third", argName = "thirdDice")
    private Integer thirdDice;
    
    @Override
    public boolean isValid()
    {
        return !(isHelp() || (firstDice == null && secondDice != null) || (secondDice == null ||  thirdDice != null));
    }

    public Integer getFirstDice()
    {
        return firstDice;
    }

    public Integer getSecondDice()
    {
        return secondDice;
    }

    public Integer getThirdDice()
    {
        return thirdDice;
    }

    public Collection<LexArcana2Modifiers> getModifiers()
    {
        Set<LexArcana2Modifiers> mods = new HashSet<>();
        if (isVerbose())
        {
            mods.add(LexArcana2Modifiers.VERBOSE);
        }
        return mods;
    }
    
}