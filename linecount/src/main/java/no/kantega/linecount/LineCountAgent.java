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

import java.lang.instrument.Instrumentation;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import static java.lang.String.format;

/**
 *
 */
public class LineCountAgent {

    /**
     * Run before main method
     *
     */
    public static void premain(String options, Instrumentation instrumentation) throws Exception {
        String prefix = options(options).getProperty("prefix");

        instrumentation.addTransformer(new LineCountTransformer(prefix));

        addShutdownHook();
    }

    /**
     * Run at agent injection
     */
    public static void agentmain(String options, Instrumentation instrumentation) throws Exception {
        String prefix = options(options).getProperty("prefix");

        instrumentation.addTransformer(new LineCountTransformer(prefix), true);

        Set<Class> retransforms = new HashSet<Class>();

        for(Class clazz : instrumentation.getAllLoadedClasses()) {
            if(clazz.getName().startsWith(prefix)) {
                retransforms.add(clazz);
            }
        }

        System.out.println(format("Retransforming %s classes", retransforms.size()));

        instrumentation.retransformClasses(retransforms.toArray(new Class[retransforms.size()]));

        addShutdownHook();
    }

    private static void addShutdownHook() {
        if(false) {
            // TODO: Add a JVM shutdown hook such that
            Registry.printCoverage();
            // TODO: is called before the JVM exits
        }
    }


    private static Properties options(String options) {
        Properties props = new Properties();
        if(options == null) {
            return props;
        }
        for(String abs : options.split(",")) {
            String[] ab = abs.split("=");
            props.setProperty(ab[0], ab[1]);
        }
        return props;
    }

}
