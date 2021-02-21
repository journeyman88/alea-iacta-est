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
package net.unknowndomain.alea.expr;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import net.unknowndomain.alea.command.BasicCommand;
import net.unknowndomain.alea.messages.MsgBuilder;
import net.unknowndomain.alea.messages.MsgStyle;
import net.unknowndomain.alea.messages.ReturnMsg;

/**
 *
 * @author journeyman
 */
public class ExpressionCommand extends BasicCommand
{

    @Override
    protected String getCommandRegex()
    {
        return "expr";
    }

    @Override
    public ReturnMsg execCommand(String cmdLine, Optional<Long> callerId)
    {
        MsgBuilder retVal = new MsgBuilder();
        Matcher prefixMatcher = PREFIX.matcher(cmdLine);
        if (prefixMatcher.matches())
        {
            if (cmdLine.contains(CMD_HELP) || cmdLine.contains("-h"))
            {
                return printHelp();
            }
            else
            {
                boolean verbose = false;
                String params = prefixMatcher.group(CMD_PARAMS);
                if (cmdLine.contains("-v"))
                {
                    verbose = true;
                    params = params.replaceAll("-v", "");
                }
                Expression solver = new Expression(params);
                List<ExpResult> results = solver.getResults();
                int total = 0;
                for (ExpResult res : results)
                {
                    total += res.getResult();
                }
                retVal.append(params).append(" = ").append(total, MsgStyle.BOLD);
                if (verbose)
                {
                    retVal.appendNewLine();
                    for (ExpResult res : results)
                    {
                        res.formatVerbose(retVal);
                    }
                    
                }
            }
        }
        return retVal.build();
    }
    
    public ReturnMsg printHelp()
    {
        MsgBuilder retVal = new MsgBuilder();
        retVal.append("Expression Engine (expr) Help", MsgStyle.BOLD, MsgStyle.UNDERLINE).append("\n").append("\n");
        StringBuilder sb = new StringBuilder();
        
        sb.append("The expr command performs and solves a simple set of addition/subtraction operations using the following dice annotation").append("\n");
        sb.append(" * NdX: roll N dice with X faces and sum them (es: 2d12)").append("\n");
        sb.append(" * N: add an Integer number (es: +5, -3)").append("\n");
        sb.append(" * NdXkY: roll N dice with X faces, sum the Y with the highest face value (es: 10d6k8)").append("\n");
        sb.append(" * NdXlY: roll N dice with X faces, sum the Y with the lowest face value (es: 3d12l2)").append("\n");
        sb.append(" * NdX/Y: roll N dice with X faces, count the ones with a face value higher or equal than Y (es: 10d10/7)").append("\n");
        sb.append(" * NdX\\Y: roll N dice with X faces, count the ones with a face value lower or equal than Y (es: 10d10\\4)").append("\n");
        sb.append("These operation can be compounded in any way: 4d4/3  + 10d6k5 - 1d8 - 1d12\\5 +7").append("\n");
        retVal.append(sb.toString(), MsgStyle.CODE);
        return retVal.build();
    }
    
}
