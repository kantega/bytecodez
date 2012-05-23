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

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class RegistryTest {
    @Test

    public void shouldRegisterLineCounts() {

        // Given

        int[] linesInClass = {4, 6, 7, 15};
        Registry.registerClass("no/kantega/SomeClass", linesInClass);
        Registry.countLine("no/kantega/SomeClass", 4);
        Registry.countLine("no/kantega/SomeClass", 4);
        Registry.countLine("no/kantega/SomeClass", 6);
        Registry.countLine("no/kantega/SomeClass", 7);
        Registry.countLine("no/kantega/SomeClass", 7);
        Registry.countLine("no/kantega/SomeClass", 7);

        // Then

        assertEquals(1, Registry.getRegisteredClassNames().size());
        assertEquals("no/kantega/SomeClass", Registry.getRegisteredClassNames().iterator().next());

        assertArrayEquals(linesInClass, Registry.getLinesOfClass("no/kantega/SomeClass"));

        assertEquals(2, Registry.getLineCount("no/kantega/SomeClass", 4));
        assertEquals(1, Registry.getLineCount("no/kantega/SomeClass", 6));
        assertEquals(3, Registry.getLineCount("no/kantega/SomeClass", 7));
        assertEquals(0, Registry.getLineCount("no/kantega/SomeClass", 15));
        assertEquals(-1, Registry.getLineCount("no/kantega/SomeClass", 666));

        Registry.printCoverage();

    }

    public static void main(String[] args) throws IOException {
        Class clazz = RegistryTest.class;
        InputStream inputStream = clazz.getResourceAsStream(clazz.getSimpleName() + ".class");

        ClassReader reader = new ClassReader(inputStream);

        ClassVisitor visitor = new TraceClassVisitor(null, new ASMifier(), new PrintWriter(System.out));
        reader.accept(visitor, ClassReader.EXPAND_FRAMES);
    }

}
