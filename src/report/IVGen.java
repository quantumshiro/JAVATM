import java.security.SecureRandom;

import javax.crypto.spec.IvParameterSpec;

public class IVGen {
    public IvParameterSpec generateIV() {
        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[16];
        random.nextBytes(iv);

        return new IvParameterSpec(iv);
    }
}
