import java.security.*;
import java.io.*;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
// import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Decrypt {

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
            // read AES128.key from file
            FileInputStream fis = new FileInputStream(args[0]);
            byte[] keyBytes = new byte[fis.available()];
            SecretKey key = new SecretKeySpec(keyBytes, args[1]);
            // read cipher text
            FileInputStream fis2 = new FileInputStream(args[2]);
            byte[] cipherText = new byte[fis2.available()];
            fis2.read(cipherText);
            fis2.close();

            // decrypt cipher text
            IvParameterSpec iv = encrypt.generaterIV();
            byte[] plainText = encrypt.encrypt(new String(cipherText), key, iv);
            System.out.println(new String(plainText));

            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
}
