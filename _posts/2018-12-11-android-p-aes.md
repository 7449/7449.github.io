---
layout:     post
title:      "AndroidP AES 加密适配"
subtitle:   "适配AndroidP"
date:       2018-12-11
author:     "y"
header-mask: 0.3
header-img: "img/android.jpg"
catalog: true
tags:
    - android
---

## message

Android N

    Didn't find class "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl"

Android P

    NoSuchAlgorithmException: class configured for SecureRandom (provider: Crypto) cannot be found

## 解决办法

其实在`android N`还可以搜索到解决办法，例如[security-crypto-provider-deprecated-in-android-n](https://stackoverflow.com/questions/39097099/security-crypto-provider-deprecated-in-android-n/42337802)

    // android n
    SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", new CryptoProvider());
    // android version < n
    SecureRandom sr = SecureRandom.getInstance("SHA1PRNG","Crypto");
    
    public final class CryptoProvider extends Provider {
        /**
     * Creates a Provider and puts parameters
     */
    public CryptoProvider() {
        super("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
        put("SecureRandom.SHA1PRNG",
                "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
        put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
    }}

但是这个在`android P`上会收到第二个异常信息，因为在`android P`上`google`已经移除了相关类

但是`google`也给出了适配方案，这里记录下：

[security-crypto-provider-deprecated-in](https://android-developers.googleblog.com/2016/06/security-crypto-provider-deprecated-in.html)


这里给出适配之后`Utils`

    
    public class AESUtils {
    
        private final static String SHA1_PRNG = "SHA1PRNG";
        private static final int KEY_SIZE = 32;
    
        /**
         * Aes加密/解密
         *
         * @param content  字符串
         * @param password 密钥
         * @param type     加密：{@link Cipher#ENCRYPT_MODE}，解密：{@link Cipher#DECRYPT_MODE}
         * @return 加密/解密结果字符串
         */
        @SuppressLint({"DeletedProvider", "GetInstance"})
        public static String des(String content, String password, @AESType int type) {
            if (TextUtils.isEmpty(content) || TextUtils.isEmpty(password)) {
                return null;
            }
            try {
                SecretKeySpec secretKeySpec;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    secretKeySpec = deriveKeyInsecurely(password);
                } else {
                    secretKeySpec = fixSmallVersion(password);
                }
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(type, secretKeySpec);
                if (type == Cipher.ENCRYPT_MODE) {
                    byte[] byteContent = content.getBytes("utf-8");
                    return parseByte2HexStr(cipher.doFinal(byteContent));
                } else {
                    byte[] byteContent = parseHexStr2Byte(content);
                    return new String(cipher.doFinal(byteContent));
                }
            } catch (NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException |
                    UnsupportedEncodingException | InvalidKeyException | NoSuchPaddingException |
                    NoSuchProviderException e) {
                e.printStackTrace();
            }
            return null;
        }
    
        @SuppressLint("DeletedProvider")
        private static SecretKeySpec fixSmallVersion(String password) throws NoSuchAlgorithmException, NoSuchProviderException {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                secureRandom = SecureRandom.getInstance(SHA1_PRNG, new CryptoProvider());
            } else {
                secureRandom = SecureRandom.getInstance(SHA1_PRNG, "Crypto");
            }
            secureRandom.setSeed(password.getBytes());
            generator.init(128, secureRandom);
            byte[] enCodeFormat = generator.generateKey().getEncoded();
            return new SecretKeySpec(enCodeFormat, "AES");
        }
    
        private static SecretKeySpec deriveKeyInsecurely(String password) {
            byte[] passwordBytes = password.getBytes(StandardCharsets.US_ASCII);
            return new SecretKeySpec(InsecureSHA1PRNGKeyDerivator.deriveInsecureKey(passwordBytes, AESUtils.KEY_SIZE), "AES");
        }
    
        private static String parseByte2HexStr(byte buf[]) {
            StringBuilder sb = new StringBuilder();
            for (byte b : buf) {
                String hex = Integer.toHexString(b & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                sb.append(hex.toUpperCase());
            }
            return sb.toString();
        }
    
        private static byte[] parseHexStr2Byte(String hexStr) {
            if (hexStr.length() < 1) return null;
            byte[] result = new byte[hexStr.length() / 2];
            for (int i = 0; i < hexStr.length() / 2; i++) {
                int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
                int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
                result[i] = (byte) (high * 16 + low);
            }
            return result;
        }
    
        @IntDef({Cipher.ENCRYPT_MODE, Cipher.DECRYPT_MODE})
        @interface AESType {
        }
    
        private static final class CryptoProvider extends Provider {
            CryptoProvider() {
                super("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
                put("SecureRandom.SHA1PRNG", "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
                put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
            }
        }
    }

`InsecureSHA1PRNGKeyDerivator`类太多了，这里就不贴了，可以去[InsecureSHA1PRNGKeyDerivator](https://android.googlesource.com/platform/development/+/master/samples/BrokenKeyDerivation/src/com/example/android/brokenkeyderivation/InsecureSHA1PRNGKeyDerivator.java)
下载，如果官网链接不上可以去[github](https://github.com/7449/AndroidDevelop/blob/develop/AppModules/common/src/main/java/com/common/util/InsecureSHA1PRNGKeyDerivator.java)下载
