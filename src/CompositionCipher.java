import java.util.ArrayList;
public class CompositionCipher extends Cipher {
    private final static int NUM_LETTERS = 26;
    private ArrayList<Cipher> ciphers;

    /**
     * Constructs a new CompositionCipher with an empty list of ciphers.
     */
    public CompositionCipher() {
        ciphers = new ArrayList<>();
    }

    /**
     * Copy constructor for CompositionCipher.
     * @param other the CompositionCipher to copy
     */
    public CompositionCipher(CompositionCipher other) {
        // Create a new ArrayList for this composition cipher
        this.ciphers = new ArrayList<>();

        // Copy the ciphers from the other composition cipher as new objects using deep copy
        for (Cipher cipher : other.ciphers) {
            this.ciphers.add(cipher.newCopy());
        }
    }

    /**
     * Encrypts a character using all ciphers in the composition.
     * @param c the character to encrypt
     * @return the encrypted character
     */
    @Override
    public char encrypt(char c) {
        char myChar = c;
        // Iterate through the ciphers in order
        for (Cipher cipher : ciphers) {
            // encrypt the character using the current cipher
            myChar = cipher.encrypt(myChar);
        }
        return myChar;
    }

    /**
     * Decrypts a character using all ciphers in reverse order.
     * @param c the character to decrypt
     * @return the decrypted character
     */
    @Override
    public char decrypt(char c) {
        char myChar = c;
        // Iterate through the ciphers in reverse order
        for (int i = ciphers.size() - 1; i >= 0; i--) {
            // Decrypt the character using the current cipher
            myChar = ciphers.get(i).decrypt(myChar);
        }
        // Return the final decrypted character
        return myChar;
    }

    /**
     * Creates a new copy of the CompositionCipher.
     * @return a new copy of the CompositionCipher
     */
    @Override
    public Cipher newCopy() {
        return new CompositionCipher(this);
    }

    /**
     * Adds a cipher to the composition.
     * @param theCipher the cipher to add
     */
    public void add(Cipher theCipher) {
        ciphers.add(theCipher);
    }
}

