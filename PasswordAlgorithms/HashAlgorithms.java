import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HashAlgorithms {
    private static final String salt = "samplesalt34";

    private final Map<String, HashFunction> algorithms = new HashMap<>();

    public HashAlgorithms() {
        algorithms.put("SHA1", password -> SHA1(password));
        algorithms.put("SHA512", password -> SHA512(password));
        algorithms.put("SHA2", password -> SHA2(password));
        algorithms.put("MD5", password -> MD5(password));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter password:");
        String str = sc.nextLine();
        HashAlgorithms a = new HashAlgorithms();
        System.out.println("Select hash algorithm from SHA1, SHA512, SHA2,  MD5:");
        String select = sc.nextLine();

        HashFunction hashFunction = a.algorithms.getOrDefault(select, a::wrong);
        hashFunction.hash(str);
    }

    private void SHA1(String password) {
        String encrypted = salt + password;
        String hashed = hash(encrypted, "SHA-1");
        System.out.println("SHA-1 hash: " + hashed);
    }

    private void SHA2(String password) {
        String encrypted = salt + password;
        String hashed = hash(encrypted, "SHA-256");
        System.out.println("SHA-2 hash: " + hashed);
    }

    private void SHA512(String password) {
        String encrypted = salt + password;
        String hashed = hash(encrypted, "SHA-512");
        System.out.println("SHA-512 hash: " + hashed);
    }

    private void MD5(String password) {
        String encrypted = salt + password;
        String hashed = hash(encrypted, "MD5");
        System.out.println("MD5 hash: " + hashed);
    }

    private void wrong(String password) {
        System.out.println("Please select a correct hashing algorithm.");
    }

    private String hash(String input, String algorithm) {
        
try {
    MessageDigest md = MessageDigest.getInstance(algorithm);
    byte[] hashBytes = md.digest(input.getBytes());
    StringBuilder result = new StringBuilder();
    for (byte b : hashBytes) {
        result.append(String.format("%02x", b));
    }
    return result.toString();
} catch (NoSuchAlgorithmException e) {
    e.printStackTrace();
    return null;
}
    }

    private interface HashFunction {
        void hash(String password);
    }
}

