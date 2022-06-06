import java.io.*;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
// import java.security.SecureRandom;

import javax.crypto.*;

public class KeyGen {
    public static void main(String[] args) throws Exception {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(args[0]);
            keyGen.init(Integer.parseInt(args[1]));
            Key key = keyGen.generateKey();

            // 鍵を書き出す
            FileOutputStream fos = new FileOutputStream(args[2]);
            fos.write(key.getEncoded());
            fos.close();
            
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}