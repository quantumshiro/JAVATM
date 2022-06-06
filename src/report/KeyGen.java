import java.io.*;
import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


public class KeyGen {
    public SecretKey generateKey(String algorithm, int size) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
        keyGen.init(size);
        SecretKey secretKey = keyGen.generateKey();

        return secretKey;
    }

    public static void main(String[] args) {
        try {
            KeyGen keyGen = new KeyGen();
            SecretKey secretKey = keyGen.generateKey(args[0], Integer.parseInt(args[1]));
            System.out.println(secretKey.getEncoded());
            // make file
            FileOutputStream fos = new FileOutputStream(args[2]);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(secretKey);
            oos.close();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (EOFException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}