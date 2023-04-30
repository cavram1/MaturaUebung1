package matura.maturauebung.service;


import matura.maturauebung.model.Admin;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class HashGenerator {

    public static String generateHash(Admin amdin) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update((amdin.getAdminID() + amdin.getEmail() + System.currentTimeMillis()).getBytes());
        byte[] digiest = messageDigest.digest();

        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < digiest.length; i++) {
            hexString.append(Integer.toHexString(0xFF & digiest[i]));
        }

        return hexString.toString();
    }
}
