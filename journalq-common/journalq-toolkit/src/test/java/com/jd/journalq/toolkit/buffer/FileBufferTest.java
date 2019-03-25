/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jd.journalq.toolkit.buffer;

import org.junit.AfterClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * File buffer test.
 */
public class FileBufferTest extends BufferTest {

    @AfterClass
    public static void afterClazz() {
        FileTesting.cleanFiles();
    }

    @Override
    protected Buffer createBuffer(long capacity) {
        return FileBuffer.allocate(FileTesting.createFile(), capacity);
    }

    @Override
    protected Buffer createBuffer(long capacity, long maxCapacity) {
        return FileBuffer.allocate(FileTesting.createFile(), capacity, maxCapacity);
    }

    @Test
    public void testPersist() {
        File file = FileTesting.createFile();
        FileBuffer buffer = FileBuffer.allocate(file, 16);
        try {
            buffer.writeLong(10).writeLong(11).flip();
            assertEquals(buffer.readLong(), 10);
            assertEquals(buffer.readLong(), 11);
        } finally {
            buffer.close();
        }
        buffer = FileBuffer.allocate(file, 16);
        try {
            assertEquals(buffer.readLong(), 10);
            assertEquals(buffer.readLong(), 11);
        } finally {
            buffer.close();
        }
    }

    @Test
    public void testDelete() {
        File file = FileTesting.createFile();
        FileBuffer buffer = FileBuffer.allocate(file, 16);
        buffer.writeLong(10).writeLong(11).flip();
        assertEquals(buffer.readLong(), 10);
        assertEquals(buffer.readLong(), 11);
        assertTrue(file.exists());
        buffer.delete();
        assertFalse(file.exists());
    }

}
