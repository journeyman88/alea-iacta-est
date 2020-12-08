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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * An MsgPart created to wrap a file.
 * 
 * @author journeyman
 */
public class MsgFilePart implements MsgPart
{
    private final byte [] data;
    private final String fileName;
    
    /**
     * Builds the part using a filename and the content.
     * 
     * @param data content
     * @param fileName filename
     */
    protected MsgFilePart(byte [] data, String fileName)
    {
        this.data = data;
        this.fileName = fileName;
    }
    /**
     * Builds the part using a Path.
     * 
     * @param file the file to wrap
     * @throws IOException if the file is not readable.
     * @see Path
     */
    protected MsgFilePart(Path file) throws IOException
    {
        this.data = Files.readAllBytes(file);
        this.fileName = file.toFile().getName();
    }

    /**
     * Gets the content of the file contained in this part.
     * 
     * @return binary data
     */
    public byte[] getData()
    {
        return data;
    }

    /**
     * Gets the name of the file contained in this part.
     * 
     * @return filename
     */
    public String getFileName()
    {
        return fileName;
    }
    
}
