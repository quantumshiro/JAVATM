import java.io.*;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypt {
    enum MODE {
        ENCRYPT,
        DECRYPT
    }
    public static byte[] crypt(SecretKey key2, byte[] input, MODE mode, String encMode) throws Exception {
        byte[] output = null;
        try {

            // read key
            FileInputStream fis = new FileInputStream("AES128.key");
            ObjectInputStream ois = new ObjectInputStream(fis);
            SecretKey key = (SecretKey) ois.readObject();
            ois.close();
            // read iv
            FileInputStream fis2 = new FileInputStream("iv.txt");
            ObjectInputStream ois2 = new ObjectInputStream(fis2);
            IvParameterSpec iv = (IvParameterSpec) ois2.readObject();
            ois2.close();
            

            SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");

            IvParameterSpec ivSpec = new IvParameterSpec(iv.getIV());
            Cipher cipher = Cipher.getInstance("AES/" + encMode + "/NoPadding");

            int m = (mode = MODE.ENCRYPT) != null ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
            cipher.init(m, keySpec, ivSpec);
            output = cipher.doFinal(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }
}
