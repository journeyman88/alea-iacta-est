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
public class NotTheEndCommand extends RpgSystemCommand
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(NotTheEndCommand.class);
    private static final RpgSystemDescriptor DESC = new RpgSystemDescriptor("Not The End", "nte", "not-the-end");
    private static final String TRAITS_PARAM = "traits";
    private static final String DIFFICULTY_PARAM = "difficulty";
    private static final String EXTRACT_PARAM = "extract";
    private static final String RISK_PARAM = "risk";
    
    private static final Options CMD_OPTIONS;
    
    static{
        CMD_OPTIONS = new Options();
        CMD_OPTIONS.addOption(
                Option.builder("t")
                        .longOpt(TRAITS_PARAM)
                        .desc("Number of white tokens that compose the bag")
                        .type(Integer.class)
                        .hasArg()
                        .argName("traitsNumber")
                        .build()
        );
        CMD_OPTIONS.addOption(
                Option.builder("d")
                        .longOpt(DIFFICULTY_PARAM)
                        .desc("Number of black tokens that compose the bag")
                        .hasArg()
                        .type(Integer.class)
                        .argName("difficultyRating")
                        .build()
        );
        CMD_OPTIONS.addOption(
                Option.builder("e")
                        .longOpt(EXTRACT_PARAM)
                        .desc("How many token extract from the bag (1 - 4)")
                        .hasArg()
                        .type(Integer.class)
                        .argName("extractValue")
                        .build()
        );
        CMD_OPTIONS.addOption(
                Option.builder("r")
                        .longOpt(RISK_PARAM)
                        .desc("Enable rhe 'Take a risk' mode. Requires a previous extraction.")
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
    };
    
    public NotTheEndCommand()
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
            if (cmd.hasOption(CMD_HELP) || (cmd.hasOption(TRAITS_PARAM) ^ cmd.hasOption(DIFFICULTY_PARAM)) || (cmd.hasOption(TRAITS_PARAM) ^ cmd.hasOption(EXTRACT_PARAM)))
            {
                return Optional.empty();
            }

            Set<NotTheEndModifiers> mods = new HashSet<>();

            if (cmd.hasOption(CMD_VERBOSE))
            {
                mods.add(NotTheEndModifiers.VERBOSE);
            }
            GenericRoll roll;
            if (cmd.hasOption(RISK_PARAM))
            {
                mods.add(NotTheEndModifiers.RISK);
                roll = new NotTheEndRisk(mods);
            }
            else
            {
                Integer traits = Integer.parseInt(cmd.getOptionValue(TRAITS_PARAM));
                Integer difficulty = Integer.parseInt(cmd.getOptionValue(DIFFICULTY_PARAM));
                Integer extract = Integer.parseInt(cmd.getOptionValue(EXTRACT_PARAM));
                roll = new NotTheEndExtract(traits, difficulty, extract, mods);
            }
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
