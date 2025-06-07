import java.io.*;
import java.math.*;
import java.nio.file.*;
import java.security.SecureRandom;
import java.util.*;

public class RSA {
    private BigInteger n; // modulus
    private BigInteger e; // public exponent
    private BigInteger d; // private exponent
    private BigInteger p; // first large prime
    private BigInteger q; // second large prime
    private BigInteger phi; // Euler's totient function: phi(n)=(p-1)*(q-1)
    private int bitLength = 256; // default bit length for prime numbers


    public RSA() {
        generateKeyPair();
    }

    public RSA(int bitLength) {
        this.bitLength = bitLength;
        generateKeyPair();
    }

    private void generateKeyPair() {
        // Generate two random prime numbers p and q
        SecureRandom random = new SecureRandom();
        p = BigInteger.probablePrime(bitLength/2, random);

        // Ensure p and q are different
        do {
            q = BigInteger.probablePrime(bitLength/2, random);
        } while (p.equals(q));

        // Calculate n = p * q
        n = p.multiply(q);

        // Calculate phi(n) = (p-1) * (q-1)
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        // Choose e such that 1 < e < phi(n) and gcd(e, phi(n)) = 1
        e = new BigInteger("65537"); // Common public exponent (Fermat number F4)

        //Ensure that Phi and e are coprime (gcd(e, phi(n)) = 1)
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0) {
            e = e.add(BigInteger.TWO);
        }

        // Calculate d such that (d * e) % phi(n) = 1
        d = e.modInverse(phi);
    }

  
    public BigInteger encrypt(BigInteger message) {
        return message.modPow(e, n);
    }

    public BigInteger decrypt(BigInteger ciphertext) {
        return ciphertext.modPow(d, n);
    }

    public static BigInteger fromStringToBigInteger(String message) {
        byte[] bytes = message.getBytes();
        return new BigInteger(1, bytes);
    }

    public static String fromBigIntegerToString(BigInteger bigInt) {
        byte[] bytes = bigInt.toByteArray();
        // Remove the sign bit if present
        if (bytes[0] == 0) {
            byte[] temp = new byte[bytes.length - 1];
            System.arraycopy(bytes, 1, temp, 0, temp.length);
            bytes = temp;
        }
        return new String(bytes);
    }

    public static String readFile(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)));
    }

    public static void writeFile(String filename, String content) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(content.getBytes());
        }
    }

    // Main method to demonstrate RSA encryption and decryption
    
    public static void main(String[] args) throws IOException {
        // Create an RSA instance
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Size");
        int size = scanner.nextInt();
        scanner.nextLine(); // consume the newline

        if (size < 256) {
            System.out.println("n must be greater than or equals 256");
            size = 256;
        }

        RSA rsa = new RSA(size);

        // Read the message from a file
        String filename = "message.txt";
        String plaintext = readFile(filename);

        // Convert plaintext to BigInteger
        BigInteger bplaintext = fromStringToBigInteger(plaintext);

        // Encrypt the message
        BigInteger bciphertext = rsa.encrypt(bplaintext);

        // Convert the encrypted message back to a string for display
        // Note: The encrypted message is binary, so it will have special characters
        String encryptedPlaintext = fromBigIntegerToString(bciphertext);

        // Decrypt the message
        BigInteger decryptedBigInt = rsa.decrypt(bciphertext);
        String decryptedPlaintext = fromBigIntegerToString(decryptedBigInt);

        // Print the results
        System.out.println("The generated public key in plaintext: " + fromBigIntegerToString(rsa.e));
        System.out.println("The generated public key in big integer: " + rsa.e);
        System.out.println("The generated private key in plaintext: " + fromBigIntegerToString(rsa.d));
        System.out.println("The generated private key in big integer: " + rsa.d);
        System.out.println("Message in plaintext: " + plaintext);
        System.out.println("Message in big integer: " + bplaintext);
        System.out.println("Encrypted Cipher in plaintext: " + encryptedPlaintext);
        System.out.println("Encrypted Cipher in big integer: " + bciphertext);
        System.out.println("Decrypted Message in plaintext: " + decryptedPlaintext);
        System.out.println("Decrypted Message in big integer: " + decryptedBigInt);

        // Save the encrypted and decrypted results to files
        writeFile("encyptedRSA.txt", "Encrypted Cipher in plaintext: \"" + encryptedPlaintext + "\"\n" +
                "Encrypted Cipher in big integer: " + bciphertext);

        writeFile("decryptedRSA.txt", "Decrypted Message in plaintext: " + decryptedPlaintext + "\n" +
                "Decrypted Message in big integer: " + decryptedBigInt);
    }
}