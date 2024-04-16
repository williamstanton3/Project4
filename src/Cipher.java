abstract class Cipher {

    /**
     * Default constructor for Cipher.
     */
    public Cipher() {}

    /**
     * Abstract method to encrypt a single character.
     * @param c the character to encrypt
     * @return the encrypted character
     */
    abstract char encrypt(char c);

    /**
     * Abstract method to decrypt a single character.
     * @param c the character to decrypt
     * @return the decrypted character
     */
    abstract char decrypt(char c);

    /**
     * Encrypts a string using the cipher.
     * @param s the string to encrypt
     * @return the encrypted string
     */
    public String encrypt(String s) {
        //create new stringBuilder
        StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0; i < s.length(); i++) {
            //iterate through each character of the sb and encrypt charAt i
            c = encrypt(s.charAt(i));
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Decrypts a string using the cipher.
     * @param s the string to decrypt
     * @return the decrypted string
     */
    public String decrypt(String s) {
        //create a String Builder
        StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0; i < s.length(); i++) {
            //iterate through each character of the sb and decrypt charAt i
            c = decrypt(s.charAt(i));
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Creates a new copy of the cipher.
     * @return a new copy of the cipher
     */
    abstract Cipher newCopy();

}

