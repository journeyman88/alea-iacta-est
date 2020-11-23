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
package net.unknowndomain.alea.systems.age;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import net.unknowndomain.alea.command.HelpWrapper;
import net.unknowndomain.alea.messages.ReturnMsg;
import net.unknowndomain.alea.systems.RpgSystemCommand;
import net.unknowndomain.alea.systems.RpgSystemDescriptor;
import net.unknowndomain.alea.roll.GenericRoll;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author journeyman
 */
public class AGECommand extends RpgSystemCommand
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AGECommand.class);
    private static final RpgSystemDescriptor DESC = new RpgSystemDescriptor("AGE System", "age", "age-system");
    
    private static final String ABILITY_PARAM = "ability";
    private static final String BONUS_PARAM = "bonus";
    private static final String FOCUS_PARAM = "focus";
    
    private static final Options CMD_OPTIONS;
    
    static {
        
        CMD_OPTIONS = new Options();
        CMD_OPTIONS.addOption(
                Option.builder("a")
                        .longOpt(ABILITY_PARAM)
                        .desc("Ability value")
                        .hasArg()
                        .required()
                        .argName("abilityValue")
                        .build()
        );
        CMD_OPTIONS.addOption(
                Option.builder("b")
                        .longOpt(BONUS_PARAM)
                        .desc("Misc bonus to apply to the roll")
                        .hasArg()
                        .argName("bonusValue")
                        .build()
        );
        CMD_OPTIONS.addOption(
                Option.builder("f")
                        .longOpt(FOCUS_PARAM)
                        .desc("Enable focus bonus")
                        .build()
        );
        CMD_OPTIONS.addOption(
                Option.builder("h")
                        .longOpt( CMD_HELP )
                        .desc( "Print help")
                        .build()
        );
        CMD_OPTIONS.addOption(
                Option.builder("v")
                        .longOpt(CMD_VERBOSE)
                        .desc("Enable verbose output")
                        .build()
        );
    }
    
    public AGECommand()
    {
        
    }
    
    @Override
    public RpgSystemDescriptor getCommandDesc()
    {
        return DESC;
    }

    @Override
    protected Logger getLogger()
    {
        return LOGGER;
    }
    
    @Override
    protected Optional<GenericRoll> safeCommand(String cmdParams)
    {
        Optional<GenericRoll> retVal;
        try
        {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(CMD_OPTIONS, cmdParams.split(" "));

            if (
                    cmd.hasOption(CMD_HELP)
                )
            {
                return Optional.empty();
            }


            Set<AGERoll.Modifiers> mods = new HashSet<>();

            int a = 0, b = 0, f = 0;
            if (cmd.hasOption(CMD_VERBOSE))
            {
                mods.add(AGERoll.Modifiers.VERBOSE);
            }
            if (cmd.hasOption(ABILITY_PARAM))
            {
                a = Integer.parseInt(cmd.getOptionValue(ABILITY_PARAM));
            }
            if (cmd.hasOption(BONUS_PARAM))
            {
                b = Integer.parseInt(cmd.getOptionValue(BONUS_PARAM));
            }
            if (cmd.hasOption(FOCUS_PARAM))
            {
                f = 2;
            }
            GenericRoll roll = new AGERoll(a + b + f, mods);
            retVal = Optional.of(roll);
        } 
        catch (ParseException | NumberFormatException ex)
        {
            retVal = Optional.empty();
        }
        return retVal;
    }
    
    @Override
    public ReturnMsg getHelpMessage(String cmdName)
    {
        return HelpWrapper.printHelp(cmdName, CMD_OPTIONS, true);
    }
    
}
