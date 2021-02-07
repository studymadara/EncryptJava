package com.adcb.utility;

import com.adcb.bean.SecureObject;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class EDUtility
{
    //1.encrypt any given plain text
    //2.decrypt any given cyphered text

    //ENCRYPTION
    //1. input one plain text with AES 128 bit CBC
    //2. will have one secret key and IV
    //3. output will be encrypted text using Cipher block Chaining

    //DECRYPTION
    //1. input encrypted string and IV
    //2. use secret key with received IV to decrypt

    //SECRET KEY
    public static final String secretKey="IAMGROOT";
    public static final String secretSalt="IAMGROOT";

    public static SecretKey getSecretKey(String secretKey,String secretSalt) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        SecretKeyFactory secretKeyFactory=SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec keySpec=new PBEKeySpec(secretKey.toCharArray(),secretSalt.getBytes(),65536,256);
        SecretKey secret = new SecretKeySpec(secretKeyFactory.generateSecret(keySpec).getEncoded(),"AES");
        return secret;
    };
    public static IvParameterSpec getInitialVector()
    {
        byte[] ivBytes=new byte[16];
        new SecureRandom().nextBytes(ivBytes);
        return new IvParameterSpec(ivBytes);
    };
    public static SecureObject getEncryptedText(String plainText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException
    {
        Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKey key=getSecretKey(secretKey,secretSalt);
        IvParameterSpec iv=getInitialVector();
        cipher.init(Cipher.ENCRYPT_MODE,key,iv);
        String encryptedText=Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()));

        SecureObject secureObject=new SecureObject();
        secureObject.setText(encryptedText);
        secureObject.setInitVector(Base64.getEncoder().encodeToString(iv.getIV()));

        return secureObject;
    };

    public static String getDecryptedText(SecureObject secureObject) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException
    {
        Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKey key=getSecretKey(secretKey,secretSalt);
        IvParameterSpec iv=new IvParameterSpec(Base64.getDecoder().decode(secureObject.getInitVector()));
        cipher.init(Cipher.DECRYPT_MODE,key,iv);
        byte[] plainText=cipher.doFinal(Base64.getDecoder().decode(secureObject.getText()));
        return new String(plainText);
    }
}
