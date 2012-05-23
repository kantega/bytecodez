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

import com.sun.tools.attach.*;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

/**
 *
 */
public class MainMethod {

    public static void main(String[] args) throws InterruptedException, AgentInitializationException, IOException, AgentLoadException, AttachNotSupportedException {
        if (args.length == 0) {
            for (int i = 0; i < 1000; i++) {
                System.out.println("Hello World!");
                Thread.sleep(1000);
            }
        } else if (args.length == 1) {
            final String jarPath = getJarLocation().getAbsolutePath();

            System.out.println("Loading agent " + jarPath);

            String identifier = args[0];

            try {
                identifier = Integer.toString(Integer.parseInt(identifier));
            } catch (NumberFormatException e) {
                for (VirtualMachineDescriptor machine : VirtualMachine.list()) {
                    if (machine.displayName().equals(identifier)) {
                        identifier = machine.id();
                        break;
                    }
                }
            }

            final VirtualMachine machine = VirtualMachine.attach(identifier);

            if (args.length == 1) {
                machine.loadAgent(jarPath);
            } else {
                machine.loadAgent(jarPath, args[1]);
            }
        }
    }

    /**
     * Return a File pointing to the location of the Jar file this Main method is executed from.
     *
     * @return
     */
    private static File getJarLocation() {
        Class clazz = MainMethod.class;
        URL resource = clazz.getResource(clazz.getSimpleName() + ".class");
        String file = resource.getFile();
        file = file.substring("file:".length(), file.indexOf("!"));
        try {
            return new File(URLDecoder.decode(file, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
