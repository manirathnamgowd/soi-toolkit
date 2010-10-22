/* 
 * Licensed to the soi-toolkit project under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The soi-toolkit project licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.soitoolkit.tools.generator.plugin.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PropertyFileUtil {

	/**
     * Hidden constructor.
     */
    private PropertyFileUtil() {
        throw new UnsupportedOperationException("Not allowed to create an instance of this class");
    }

	static public PrintWriter openPropertyFileForAppend(String outputFolder, String propertyFile) throws IOException {
		String propFile = outputFolder + "/src/environment/" + propertyFile + ".properties";

		// TODO: Replace with sl4j!
		System.err.println("Appending to property file: " + propFile);
		
	    return new PrintWriter(new BufferedWriter(new FileWriter(propFile, true)));
	}
    
}