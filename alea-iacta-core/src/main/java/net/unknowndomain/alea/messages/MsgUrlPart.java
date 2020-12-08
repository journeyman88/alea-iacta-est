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
package net.unknowndomain.alea.messages;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * A MsgPart created to wrap an URL.
 * 
 * @author journeyman
 */
public class MsgUrlPart implements MsgPart
{
    private final URL url;
    
    /**
     * Builds the part using a URL.
     * 
     * @param url url to wrap
     */
    protected MsgUrlPart(URL url)
    {
        this.url = url;
    }
    
    /**
     * Builds the part using a String.
     * 
     * @param url url to wrap
     * @throws java.net.MalformedURLException if the url is not valid
     */
    protected MsgUrlPart(String url) throws MalformedURLException
    {
        this.url = new URL(url);
    }

    /**
     * Gets the url contained in this part.
     * 
     * @return url
     */
    public URL getUrl()
    {
        return url;
    }
}
