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
package net.unknowndomain.alea.systems.shintiara;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author journeyman
 */
public class ShintiaraCommand extends RpgSystemCommand
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ShintiaraCommand.class);
    private static final RpgSystemDescriptor DESC = new RpgSystemDescriptor("Shintiara RPG", "shn", "shintiara");
    
    private static final String TARGET_PARAM = "target";
    private static final String ADVANTAGE_PARAM = "advantage";
    private static final String DISADVANTAGE_PARAM = "disadvantage";
    
    private static final Options CMD_OPTIONS;
    
    static {
        
        CMD_OPTIONS = new Options();
        CMD_OPTIONS.addOption(
                Option.builder("t")
                        .longOpt(TARGET_PARAM)
                        .desc("Target to achieve success")
                        .hasArg()
                        .required()
                        .argName("targetValue")
                        .build()
        );
        CMD_OPTIONS.addOption(
                Option.builder("a")
                        .longOpt(ADVANTAGE_PARAM)
                        .desc("Advantage rank, between 0 and 9")
                        .hasArg()
                        .argName("advantageValue")
                        .build()
        );
        CMD_OPTIONS.addOption(
                Option.builder("d")
                        .longOpt(DISADVANTAGE_PARAM)
                        .desc("Disadvantage rank, between 0 and 9")
                        .hasArg()
                        .argName("disadvantageValue")
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
    
    public ShintiaraCommand()
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
            LOGGER.debug(cmdLine);
            try
            {
                CommandLineParser parser = new DefaultParser();
                CommandLine cmd = parser.parse(CMD_OPTIONS, params.split(" "));
                
                if (
                        cmd.hasOption(CMD_HELP)
                    )
                {
                    return HelpWrapper.printHelp(prefixMatcher.group(CMD_NAME), CMD_OPTIONS, true);
                }
                
                
                Set<ShintiaraRoll.Modifiers> mods = new HashSet<>();
                
                int t = 0, a = 0, d = 0;
                if (cmd.hasOption(CMD_VERBOSE))
                {
                    mods.add(ShintiaraRoll.Modifiers.VERBOSE);
                }
                if (cmd.hasOption(TARGET_PARAM))
                {
                    t = Integer.parseInt(cmd.getOptionValue(TARGET_PARAM));
                }
                if (cmd.hasOption(ADVANTAGE_PARAM))
                {
                    a = Integer.parseInt(cmd.getOptionValue(ADVANTAGE_PARAM));
                }
                if (cmd.hasOption(DISADVANTAGE_PARAM))
                {
                    d = Integer.parseInt(cmd.getOptionValue(DISADVANTAGE_PARAM));
                }
                GenericRoll roll = new ShintiaraRoll(t, a, d, mods);
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