/*
 * Copyright (C) 2017-2023 Ignite Realtime Foundation. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jivesoftware.util;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;

public class AesEncryptorTest {

    @Test
    public void testEncryptionUsingDefaultKey() {
        String test = UUID.randomUUID().toString();
        
        Encryptor encryptor = new AesEncryptor();
        
        String b64Encrypted = encryptor.encrypt(test);
        assertNotEquals(test, b64Encrypted);
        
        assertEquals(test, encryptor.decrypt(b64Encrypted));
    }

    @Test
    public void testEncryptionUsingCustomKey() {
        
        String test = UUID.randomUUID().toString();
        
        Encryptor encryptor = new AesEncryptor(UUID.randomUUID().toString());
        
        String b64Encrypted = encryptor.encrypt(test);
        assertNotEquals(test, b64Encrypted);
        
        assertEquals(test, encryptor.decrypt(b64Encrypted));
    }

    @Test
    public void testEncryptionForEmptyString() {
        
        String test = "";
        
        Encryptor encryptor = new AesEncryptor();
        
        String b64Encrypted = encryptor.encrypt(test);
        assertNotEquals(test, b64Encrypted);
        
        assertEquals(test, encryptor.decrypt(b64Encrypted));
    }


    @Test
    public void testEncryptionForNullString() {
        Encryptor encryptor = new AesEncryptor();
        
        String b64Encrypted = encryptor.encrypt(null);
        
        assertNull(b64Encrypted);
    }

    @Test
    public void testEncryptionWithKeyAndIV() {

        final String plainText = UUID.randomUUID().toString();
        final byte[] iv = "0123456789abcdef".getBytes();
        final Encryptor encryptor = new AesEncryptor(UUID.randomUUID().toString());
        final String encryptedText = encryptor.encrypt(plainText, iv);

        final String decryptedText = encryptor.decrypt(encryptedText, iv);

        assertThat(decryptedText, is(plainText));
    }

    @Test
    public void testEncryptionWithKeyAndBadIV() {

        final String plainText = UUID.randomUUID().toString();
        final byte[] iv = "0123456789abcdef".getBytes();
        final Encryptor encryptor = new AesEncryptor(UUID.randomUUID().toString());
        final String encryptedText = encryptor.encrypt(plainText, iv);

        final String decryptedText = encryptor.decrypt(encryptedText);

        assertThat(decryptedText, is(not(plainText)));

    }

}
