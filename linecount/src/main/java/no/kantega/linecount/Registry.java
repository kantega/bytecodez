/*
 * Copyright 2012 Kantega AS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package no.kantega.linecount;

import java.util.*;

/**
 *
 */
public class Registry {


    private static SortedMap<String, Map<Integer, Integer>> lineCounts= new TreeMap<String, Map<Integer, Integer>>();

    public static void countLine(String className, int lineNumber) {
        // TODO
    }

    public static SortedSet<String> getRegisteredClassNames() {
        // TODO
        return null;
    }

    public static int getLineCount(String className, int lineNumber) {
        // TODO
        return 0;
    }

    public static int[] getLinesOfClass(String className) {
        //TODO
        return null;
    }

    public static void registerClass(String className, int[] lineNumbers) {
        // Set inital count of all lines to 0
        HashMap<Integer, Integer> counts = new HashMap<Integer, Integer>();
        lineCounts.put(className, counts);
        for(int lineNumber : lineNumbers) {
            counts.put(lineNumber, 0);
        }
    }

    public static void printCoverage() {
        System.out.println("Line coverage report:");
        System.out.println();

        for(String className : getRegisteredClassNames()) {
            System.out.println("Class " + className);
            for(int lineNumber : getLinesOfClass(className)) {
                System.out.println("\t" + lineNumber +":\t" + getLineCount(className, lineNumber));
            }

        }
    }
}
