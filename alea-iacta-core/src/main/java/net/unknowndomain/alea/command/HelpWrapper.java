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
package net.unknowndomain.alea.command;

import java.io.PrintWriter;
import java.io.StringWriter;
import net.unknowndomain.alea.messages.MsgBuilder;
import net.unknowndomain.alea.messages.MsgStyle;
import net.unknowndomain.alea.messages.ReturnMsg;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

/**
 * Thiss class is to build a help for a command and wrap it in a message.
 * 
 * @author journeyman
 */
public class HelpWrapper
{
    /**
     * Builds a Help message.
     * 
     * @param commandName the command name used to format the help.
     * @param opts the command line options used to build the help.
     * @param autoUsage true if this has to build automatically a usage line.
     * @return the help contained in a message wrapper.
     * @see ReturnMsg
     * @see Options
     */
    public static ReturnMsg printHelp(String commandName, Options opts, boolean autoUsage)
    {
        MsgBuilder mb = new MsgBuilder();
        HelpFormatter formatter = new HelpFormatter();
        StringWriter out = new StringWriter();
        PrintWriter pw = new PrintWriter(out);

        formatter.printHelp(pw, 80, commandName, null, opts, formatter.getLeftPadding(), formatter.getDescPadding(), null, autoUsage);
        pw.flush();
        mb.append(out.toString(), MsgStyle.CODE);
        return mb.build();
    }
}
