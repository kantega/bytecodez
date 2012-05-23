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

package no.kantega.uppercase;

import java.lang.instrument.Instrumentation;
import java.util.Properties;

/**
 *
 */
public class UppercaseAgent {

    /**
     * Run before main method
     *
     */
    public static void agentmain(String options, Instrumentation instrumentation) throws Exception {
        addTransformer(options, instrumentation, true);
    }

    /**
     * Run at agent injection
     */
    public static void premain(String options, Instrumentation instrumentation) throws Exception {
        addTransformer(options, instrumentation, true);
    }

    private static void addTransformer(String options, Instrumentation instrumentation, boolean canRetransform) {
        String prefix = options(options).getProperty("prefix");

        instrumentation.addTransformer(new UppercaseTransformer(prefix), canRetransform);
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
