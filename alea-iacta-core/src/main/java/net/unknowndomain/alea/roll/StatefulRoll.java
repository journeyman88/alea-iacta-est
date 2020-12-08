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
package net.unknowndomain.alea.roll;

/**
 * This interface represents a roll that is state dependent.
 * 
 * This roll can load its state from the previous results, and is used to 
 * implement those systems that allows or requires a reroll based on the player 
 * choices.
 * 
 * @author journeyman
 */
public interface StatefulRoll extends GenericRoll
{

    /**
     * Loads the state from a previous result.
     * 
     * @param state the previous result
     * @return true if loading was successful
     */
    boolean loadState(GenericResult state);
    
}
