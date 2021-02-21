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
package net.unknowndomain.alea.bot;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.unknowndomain.alea.command.Command;
import net.unknowndomain.alea.messages.ReturnMsg;
import net.unknowndomain.alea.parser.PicocliParser;
import net.unknowndomain.alea.systems.RpgSystemCommand;
import net.unknowndomain.alea.systems.RpgSystemOptions;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

/**
 *
 * @author journeyman
 */
public class SystemListener implements MessageCreateListener
{
    private final Pattern PATTERN; 
    
    private final RpgSystemCommand system;
    
    public SystemListener(RpgSystemCommand system)
    {
        this.system = system;
        PATTERN = Pattern.compile("^!(?<" + Command.CMD_NAME + ">" + system.getCommandRegex() + ")(( )(?<" + Command.CMD_PARAMS + ">.*))?$");
    }
    
    @Override
    public void onMessageCreate(MessageCreateEvent event)
    {
        Matcher checkPrefix = PATTERN.matcher(event.getMessageContent());
        if (checkPrefix.matches()) {
            MessageBuilder builder = new MessageBuilder();
            String cmdLine = event.getMessageContent().substring(1);
            RpgSystemOptions options = system.buildOptions();
            String args = checkPrefix.group(Command.CMD_PARAMS);
            if (args != null)
            {
                PicocliParser.parseArgs(options, args.split(" "));
            }
            else
            {
                PicocliParser.parseArgs(options, "-h");
            }
            Optional<Long> callerId = readUserId(event.getMessageAuthor());
            Optional<ReturnMsg> msg = system.execCommand(options, callerId);
            if (msg.isPresent())
            {
                MsgFormatter.appendMessage(builder, msg.get());
            }
            else
            {
                MsgFormatter.appendMessage(builder, PicocliParser.printHelp(checkPrefix.group(Command.CMD_NAME), options));
            }
            builder.send(event.getChannel());
            
        }
    }
    
    private Optional<Long> readUserId(MessageAuthor author)
    {
        Optional<Long> retVal = Optional.empty();
        if (author.isUser() && author.asUser().isPresent())
        {
            User discordUser = author.asUser().get();
            retVal = Optional.of(discordUser.getId());
        }
        return retVal;
    }
    
}
