
import java.util.Base64;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.crypto.SecretKey;
// import javax.crypto.SecretKey;


public class Decrypt {

    public static String decrypt(SecretKey key, String text64, String encMode) throws Exception {
        Crypt.MODE dec = Crypt.MODE.DECRYPT;
        byte[] cipher_byte = Base64.getDecoder().decode(text64.getBytes() );
        byte[] plain_byte = Crypt.crypt(key, cipher_byte, dec, encMode);
        String plaintext = new String(plain_byte, "UTF-8");
        return plaintext;
}

    public static void main(String[] args) {
        // Decrypt decrypt = new Decrypt();
        try {
            FileInputStream fis = new FileInputStream("AES128.key");
            ObjectInputStream ois = new ObjectInputStream(fis);
            SecretKey key = (SecretKey) ois.readObject();
            ois.close();
            FileInputStream fis2 = new FileInputStream("iv.txt");
            ObjectInputStream ois2 = new ObjectInputStream(fis2);
            // IvParameterSpec iv = (IvParameterSpec) ois2.readObject();
            ois2.close();
            // read txt file
            Path path = Paths.get(args[0]);
            String plaintext = new String(Files.readAllBytes(path), "UTF-8");
            // decrypt
            String ciphertext = decrypt(key, plaintext, "AES/CBC/PKCS5Padding");
            // write ciphertext to file
            FileOutputStream fos = new FileOutputStream(args[1]);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(ciphertext);
            oos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
}
