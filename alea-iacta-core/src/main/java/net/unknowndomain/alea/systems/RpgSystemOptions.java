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
package net.unknowndomain.alea.systems;

import net.unknowndomain.alea.systems.annotations.RpgSystemOption;


/**
 *
 * @author journeyman
 */
public abstract class RpgSystemOptions
{
    
    @RpgSystemOption(name = "help", shortcode = "h", description = "basic.options.help")
    private boolean help;
    @RpgSystemOption(name = "verbose", shortcode = "v", description = "basic.options.verbose")
    private boolean verbose;
    
    public abstract boolean isValid();

    public boolean isHelp()
    {
        return help;
    }

    public void setHelp(boolean help)
    {
        this.help = help;
    }

    public boolean isVerbose()
    {
        return verbose;
    }

    public void setVerbose(boolean verbose)
    {
        this.verbose = verbose;
    }

    
}
