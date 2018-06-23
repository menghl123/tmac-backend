package com.tmac.encryptor;

import org.jasypt.intf.service.JasyptStatelessService;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordEncryptorTest {
    @Test
    public void should_encrypt_password() {
        // given
        final String input = "!@#mhl123123";

        // when
        final JasyptStatelessService service = new JasyptStatelessService();
        String result = service.encrypt(input, "secretkey",
                null, null,
                null, null, null,
                null, null,
                null, null,
                null, null,
                null, null,
                null, null,
                null, null,
                null,
                null, null);
        System.out.println("---enc:" + result);

        final String plainText = service.decrypt(result, "secretkey",
                null, null,
                null, null, null,
                null, null,
                null, null,
                null, null,
                null, null,
                null, null,
                null, null,
                null,
                null, null);

        System.out.println("---enc:" + plainText);
        // then
        assertThat(plainText).isEqualTo(input);
    }
}
