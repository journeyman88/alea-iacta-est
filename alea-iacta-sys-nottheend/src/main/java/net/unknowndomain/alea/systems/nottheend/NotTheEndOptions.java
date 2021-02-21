/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.unknowndomain.alea.systems.nottheend;

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
@RpgSystemData(bundleName = "net.unknowndomain.alea.systems.nottheend.RpgSystemBundle")
public class NotTheEndOptions extends RpgSystemOptions
{
    @RpgSystemOption(name = "traits", shortcode = "t", description = "nottheend.options.traits", argName = "traitsNumber")
    private Integer traits;
    @RpgSystemOption(name = "difficulty", shortcode = "d", description = "nottheend.options.difficulty", argName = "difficultyRating")
    private Integer difficulty;
    @RpgSystemOption(name = "extract", shortcode = "e", description = "nottheend.options.extract", argName = "extractValue")
    private Integer extract;
    @RpgSystemOption(name = "risk", shortcode = "r", description = "nottheend.options.risk")
    private boolean risk;
    
    @Override
    public boolean isValid()
    {
        return !(isHelp() || (traits == null && difficulty != null) || (difficulty == null ||  extract != null));
    }

    public Integer getTraits()
    {
        return traits;
    }

    public Integer getDifficulty()
    {
        return difficulty;
    }

    public Integer getExtract()
    {
        return extract;
    }

    public Collection<NotTheEndModifiers> getModifiers()
    {
        Set<NotTheEndModifiers> mods = new HashSet<>();
        if (isVerbose())
        {
            mods.add(NotTheEndModifiers.VERBOSE);
        }
        return mods;
    }

    public boolean isRisk()
    {
        return risk;
    }
    
}