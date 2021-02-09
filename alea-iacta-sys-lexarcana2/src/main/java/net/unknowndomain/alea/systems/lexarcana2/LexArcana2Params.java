/*
 * Copyright 2021 Marco Bignami.
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

import net.unknowndomain.alea.systems.RpgSystemParams;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

/**
 *
 * @author journeyman
 */
@Command(
        description = "Lex Arcana 2nd Edition",
        resourceBundle = "net.unknowndomain.alea.systems.lexarcana2.RpgSystemBundle",
        sortOptions = false
)
public class LexArcana2Params extends RpgSystemParams {
    
    @ArgGroup(exclusive = false, multiplicity = "1", order = 1)
    FirstGroup groupedDice;
    
    static class FirstGroup {
        @Option(names = { "-f", "--first-dice"}, descriptionKey = "lexarcana2.options.first", paramLabel = "firstDice", required = true)
        private Integer firstDice;
        @ArgGroup(exclusive = false, multiplicity = "1", order = 1)
        SecondGroup charData;
    }
    
    static class SecondGroup {
        @Option(names = { "-s", "--second-dice"}, descriptionKey = "lexarcana2.options.second", paramLabel = "secondDice", required = true)
        private Integer secondDice;
        @Option(names = { "-t", "--third-dice"}, descriptionKey = "lexarcana2.options.third", paramLabel = "thirdDice")
        private Integer thirdDice;
    }
    
}
