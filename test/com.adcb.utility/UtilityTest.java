package com.adcb.utility;

import com.adcb.bean.SecureObject;
import org.junit.Assert;
import org.junit.Test;

public class UtilityTest extends EDUtility
{
    public SecureObject testEncrypt(String plainText)
    {
        try
        {
            return getEncryptedText(plainText);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("ISSUE WITH ENCRYPTION");
            return null;
        }
    };

    public String testDecrypt(SecureObject secureObject)
    {
        try
        {
            return getDecryptedText(secureObject);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("ISSUE WITH DECRYPTION");
            return null;
        }
    };

    @Test
    public void test()
    {
        String plainText="VIRAJ";
        SecureObject secureObject=testEncrypt(plainText);
        String output=testDecrypt(secureObject);
        System.out.println(plainText);
        System.out.println("ENCRYPTED TEXT :: "+secureObject.getText());
        System.out.println("IV :: "+secureObject.getInitVector());
        System.out.println(output);
        Assert.assertEquals(plainText,output);
    }
}
