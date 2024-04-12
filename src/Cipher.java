abstract class Cipher {

    public Cipher() {}

    // encrypt char
    abstract char encrypt(char c);

    // decrypt char
    abstract char decrypt(char c);

    // encrypt string
    public String encrypt(String s) {
        StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0; i < s.length(); i++) {
            c = encrypt(s.charAt(i));
            sb.append(c);
        }
        // return encrypted String
        return sb.toString();
    }

    // decrypt string
    public String decrypt(String s) {
        StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0; i < s.length(); i++) {
            c = decrypt(s.charAt(i));
            sb.append(c);
        }
        // return encrypted String
        return sb.toString();
    }

    // newCopy
    abstract Cipher newCopy();

}
