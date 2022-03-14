package com.tuituidan.openhub.util;

import com.tuituidan.openhub.exception.EncryptException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

/**
 * AES加密，GCM模式下，同一个明文，每次加密的密文都不同.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2022/2/28
 */
@Slf4j
@UtilityClass
public class AesGcmUtils {

    private static final int KEY_LEN = 128;

    private static final int IV_LEN = 12;

    private static final int SALT_LEN = 16;

    private static final String KEY_ALGORITHM = "AES";

    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/GCM/NoPadding";

    private static final String SECRET_KEY = "ppnn13%dkstFeb.1st";

    /**
     * AES 加密操作
     *
     * @param content 明文
     * @return 密文
     */
    public static String encrypt(String content) {
        try {
            byte[] iv = new byte[IV_LEN];
            new SecureRandom().nextBytes(iv);
            byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            GCMParameterSpec params = new GCMParameterSpec(KEY_LEN, iv);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(), params);
            byte[] encryptData = cipher.doFinal(contentBytes);
            byte[] message = new byte[IV_LEN + contentBytes.length + SALT_LEN];
            System.arraycopy(iv, 0, message, 0, IV_LEN);
            System.arraycopy(encryptData, 0, message, IV_LEN, encryptData.length);
            return Base64.getEncoder().encodeToString(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new EncryptException("AES加密失败", e);
        }
    }

    /**
     * AES 解密操作
     *
     * @param content 密文
     * @return 明文
     */
    public static String decrypt(String content) {
        try {
            byte[] encryptData = Base64.getDecoder().decode(content);
            Assert.isTrue(encryptData.length >= IV_LEN + SALT_LEN, "密文格式不对");
            GCMParameterSpec params = new GCMParameterSpec(KEY_LEN, encryptData, 0, IV_LEN);
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), params);
            byte[] decryptData = cipher.doFinal(encryptData, IV_LEN, encryptData.length - IV_LEN);
            return new String(decryptData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new EncryptException("AES解密失败", e);
        }
    }

    /**
     * 生成加密秘钥
     *
     * @return SecretKeySpec
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    private static SecretKeySpec getSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        kg.init(KEY_LEN, secureRandom);
        return new SecretKeySpec(kg.generateKey().getEncoded(), KEY_ALGORITHM);
    }

}
