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
package net.unknowndomain.alea.systems.lexarcana2;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import net.unknowndomain.alea.command.HelpWrapper;
import net.unknowndomain.alea.systems.RpgSystemCommand;
import net.unknowndomain.alea.systems.RpgSystemDescriptor;
import net.unknowndomain.alea.roll.GenericRoll;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.javacord.api.entity.message.MessageBuilder;

/**
 *
 * @author journeyman
 */
public class LexArcana2Command extends RpgSystemCommand
{
    private static final RpgSystemDescriptor DESC = new RpgSystemDescriptor("Lex Arcana 2nd Edition", "lex", "lex-arcana-2nd");
    
    private static final String FIRST_DICE_PARAM = "first-dice";
    private static final String SECOND_DICE_PARAM = "second-dice";
    private static final String THIRD_DICE_PARAM = "third-dice";
    
    private static final Options CMD_OPTIONS;
    
    static {
        CMD_OPTIONS = new Options();
        CMD_OPTIONS.addOption(
                Option.builder("f")
                        .longOpt(FIRST_DICE_PARAM)
                        .desc("Dice points to use for the first dice (for a d4 => 4)")
                        .hasArg()
                        .required()
                        .argName("firstDice")
                        .build()
        );
        CMD_OPTIONS.addOption(
                Option.builder("s")
                        .longOpt(SECOND_DICE_PARAM)
                        .desc("(Optional) Dice points to use for the second dice (for a d4 => 4)")
                        .hasArg()
                        .argName("secondDice")
                        .build()
        );
        CMD_OPTIONS.addOption(
                Option.builder("t")
                        .longOpt( THIRD_DICE_PARAM )
                        .desc( "(Optional) Dice points to use for the third dice (for a d4 => 4)")
                        .hasArg()
                        .argName("thirdDice")
                        .build()
        );
        
        CMD_OPTIONS.addOption(
                Option.builder("h")
                        .longOpt( CMD_HELP )
                        .desc( "Print help")
                        .hasArg(false)
                        .build()
        );
        
        CMD_OPTIONS.addOption(
                Option.builder("v")
                        .longOpt(CMD_VERBOSE)
                        .desc("Enable verbose output")
                        .build()
        );
    }
    
    public LexArcana2Command()
    {
        
    }
    
    @Override
    public RpgSystemDescriptor getCommandDesc()
    {
        return DESC;
    }
    
    @Override
    public MessageBuilder execCommand(String cmdLine)
    {
        MessageBuilder retVal = new MessageBuilder();
        Matcher prefixMatcher = PREFIX.matcher(cmdLine);
        if (prefixMatcher.matches())
        {
            String params = prefixMatcher.group(CMD_PARAMS);
            if (params == null || params.isEmpty())
            {
                return HelpWrapper.printHelp(prefixMatcher.group(CMD_NAME), CMD_OPTIONS, true);
            }
            try
            {
                CommandLineParser parser = new DefaultParser();
                CommandLine cmd = parser.parse(CMD_OPTIONS, params.split(" "));
                if (
                        cmd.hasOption(CMD_HELP) || 
                        (cmd.hasOption(SECOND_DICE_PARAM) && (! cmd.hasOption(FIRST_DICE_PARAM))) || 
                        (cmd.hasOption(THIRD_DICE_PARAM) && (! cmd.hasOption(SECOND_DICE_PARAM)))
                    )
                {
                    return HelpWrapper.printHelp(prefixMatcher.group(CMD_NAME), CMD_OPTIONS, true);
                }
                
                Set<LexArcana2Roll.Modifiers> mods = new HashSet<>();
                
                if (cmd.hasOption(CMD_VERBOSE))
                {
                    mods.add(LexArcana2Roll.Modifiers.VERBOSE);
                }
                GenericRoll roll;
                String f = cmd.getOptionValue(FIRST_DICE_PARAM);
                String s = cmd.getOptionValue(SECOND_DICE_PARAM);
                String t = cmd.getOptionValue(THIRD_DICE_PARAM);
                int sec = 0;
                if ((s != null) && (!s.isEmpty()))
                {
                    sec = Integer.parseInt(s);
                }
                int thi = 0;
                if ((t != null) && (!t.isEmpty()))
                {
                    thi = Integer.parseInt(t);
                }
                roll = new LexArcana2Roll(Integer.parseInt(f), sec, thi, mods);
                retVal = roll.getResult();
            } 
            catch (ParseException | NumberFormatException ex)
            {
                retVal = HelpWrapper.printHelp(prefixMatcher.group(CMD_NAME), CMD_OPTIONS, true);
            }
        }
        return retVal;
    }
    
}
