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

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 *
 */
public class UppercaseClassVisitor extends ClassVisitor {
    public UppercaseClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM4, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        return new UppercaseMethodVisitor(super.visitMethod(access, name, desc, signature, exceptions));
    }

    private class UppercaseMethodVisitor extends MethodVisitor {
        private UppercaseMethodVisitor(MethodVisitor mv) {
            super(Opcodes.ASM4, mv);
        }

        @Override
        public void visitLdcInsn(Object cst) {
            if(cst instanceof  String) {
                super.visitLdcInsn(cst.toString().toUpperCase());
            } else {
                super.visitLdcInsn(cst);
            }
        }
    }


}
