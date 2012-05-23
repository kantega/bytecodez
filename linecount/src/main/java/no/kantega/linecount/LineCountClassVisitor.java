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

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 */
public class LineCountClassVisitor extends ClassVisitor {
    private SortedSet<Integer> existingLines = new TreeSet<Integer>();
    private String className;

    public LineCountClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM4, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.className = name;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        return new LineCountMethodVisitor(super.visitMethod(access, name, desc, signature, exceptions));
    }

    private class LineCountMethodVisitor extends MethodVisitor {
        private LineCountMethodVisitor(MethodVisitor mv) {
            super(Opcodes.ASM4, mv);
        }

        @Override
        public void visitLineNumber(int lineNumber, Label start) {
            super.visitLineNumber(lineNumber, start);
            // TODO 1: Register that this line exists

            // TODO 2: Add byte code here which produces a method call to Registry.callLine()

        }
    }


    public int[] getExistingLines() {

        int[] array = new int[existingLines.size()];
        int c = 0;
        for(int i : existingLines) {
            array[c++] = i;
        }
        return array;
    }

}
