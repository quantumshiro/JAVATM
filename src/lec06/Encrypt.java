import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Base64;
import javax.crypto.SecretKey;
// import javax.crypto.spec.IvParameterSpec;

public class Encrypt {

    public static String encrypt(SecretKey key, String plaintext, String encMode) throws Exception {
        Crypt.MODE mode = Crypt.MODE.ENCRYPT;
        byte[] cipher_byte = Crypt.crypt(key, plaintext.getBytes(), mode, encMode);
        String b64 = Base64.getEncoder().encodeToString(cipher_byte);
        return b64;
}

    public static void main(String[] args) throws Exception {
        // read key and txt file
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
            // encrypt
            String ciphertext = encrypt(key, plaintext, "AES/CBC/PKCS5Padding");
            // write ciphertext to file
            FileOutputStream fos = new FileOutputStream(args[1]);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(ciphertext);
            oos.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}
