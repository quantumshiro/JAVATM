import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;

public class Decrypt {
    public String decrypto(byte[] cryptoText, SecretKey key, IvParameterSpec iv, String algorithm) throws GeneralSecurityException {
        Cipher decrypter = Cipher.getInstance("AES/" + algorithm + "/NoPadding");
        decrypter.init(Cipher.DECRYPT_MODE, key, iv);

        return new String(decrypter.doFinal(cryptoText));
    }

    public static void main(String[] args) {
        try {
            // read iv.txt
            FileInputStream fis3 = new FileInputStream(args[0]);
            ObjectInputStream ois3 = new ObjectInputStream(fis3);
            IvParameterSpec iv = (IvParameterSpec) ois3.readObject();
            ois3.close();

            // read key
            FileInputStream fis = new FileInputStream(args[0]);
            ObjectInputStream ois = new ObjectInputStream(fis);
            SecretKey key = (SecretKey) ois.readObject();
            ois.close();

            // read cipher text
            FileInputStream fis2 = new FileInputStream(args[1]);
            ObjectInputStream ois2 = new ObjectInputStream(fis2);
            byte[] cipherText = (byte[]) ois2.readObject();
            ois2.close();

            Decrypt decrypt = new Decrypt();
            String plainText = decrypt.decrypto(cipherText, key, iv, args[2]);
            System.out.println(plainText);

            FileOutputStream fos = new FileOutputStream(args[3]);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(plainText);
            oos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
