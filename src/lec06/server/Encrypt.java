import java.security.*;
import java.io.*;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
// import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt {

    public IvParameterSpec generaterIV() {
        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[16];
        random.nextBytes(iv);

        return new IvParameterSpec(iv);
    }

    public byte[] encrypt(String plainText, SecretKey key, IvParameterSpec iv) throws GeneralSecurityException {
        Cipher encrypter = Cipher.getInstance("AES/CBC/PKCS5Padding");
        encrypter.init(Cipher.ENCRYPT_MODE, key, iv);

        return encrypter.doFinal(plainText.getBytes());
    }

    public static void main(String[] args) {
        Encrypt encrypt = new Encrypt();
        try {
            IvParameterSpec iv = encrypt.generaterIV();
            // key is AES128.key
            // read AES128.key from file
            FileInputStream fis = new FileInputStream(args[1]);
            byte[] keyBytes = new byte[fis.available()];
            SecretKey key = new SecretKeySpec(keyBytes, args[2]);
            // read plain text
            byte[] cipherText = encrypt.encrypt(args[0], key, iv);
            System.out.println(new String(cipherText));
            // output to file
            FileOutputStream fos = new FileOutputStream(args[3]);
            fos.write(cipherText);
            fos.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MODE {
    }    
}
