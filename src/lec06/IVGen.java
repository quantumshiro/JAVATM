import java.security.SecureRandom;
import java.io.*;

public class IVGen {
    public static String generateIV() throws Exception {
        SecureRandom random = new SecureRandom();
        byte[] ENCRYPT_IV = new byte[16];
        random.nextBytes(ENCRYPT_IV);
        return new String(ENCRYPT_IV);
    }

    public static void main(String[] args) {
        try {
            String iv = generateIV();
            System.out.println(iv);
            // make file
            FileOutputStream fos = new FileOutputStream(args[0]);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(iv);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
