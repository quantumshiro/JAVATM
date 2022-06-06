import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class Encrypt {
    public byte[] encrypt(String plainText, SecretKey key, IvParameterSpec iv, String algorithm)
            throws GeneralSecurityException {
        Cipher encrypter = Cipher.getInstance("AES/" + algorithm + "/noPadding");
        encrypter.init(Cipher.ENCRYPT_MODE, key, iv);

        return encrypter.doFinal(plainText.getBytes());
    }
    
    public static void main(String[] args) {
        // read key and txt file
        try {

            IVGen ivGen = new IVGen();
            IvParameterSpec iv = ivGen.generateIV();

            FileInputStream fis = new FileInputStream(args[0]);
            ObjectInputStream ois = new ObjectInputStream(fis);
            SecretKey key = (SecretKey) ois.readObject();
            ois.close();

            // read plain text file
            Path path = Paths.get(args[1]);
            String plainText = Files.readString(path);

            Encrypt encrypt = new Encrypt();
            byte[] cipherText = encrypt.encrypt(plainText, key, iv, args[2]);
            System.out.println(cipherText);
            
            FileOutputStream fos = new FileOutputStream(args[3]);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(cipherText);
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
