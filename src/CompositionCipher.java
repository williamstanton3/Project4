import java.util.ArrayList;

public class CompositionCipher extends Cipher {
    private final static int NUM_LETTERS = 26;
    private ArrayList<Cipher> ciphers;

    public CompositionCipher() {
        ciphers = new ArrayList<>();
    }
    public CompositionCipher (CompositionCipher other) {
        this.ciphers = other.ciphers;
    }

    @Override
    public char encrypt (char c) {
        char myChar = c;
        for (int i = 0; i < ciphers.size(); i++) {
            myChar = ciphers.get(i).encrypt(myChar);
        }
        return myChar;
    }

    @Override
    public char decrypt (char c) {
        char myChar = c;
        for (int i = ciphers.size() -1; i >= 0; i--) {
            myChar = ciphers.get(i).decrypt(myChar);
        }
        return myChar;
    }

    @Override
    public Cipher newCopy() {
        return new CompositionCipher(this);
    }

    public void add (Cipher theCipher) {
        ciphers.add(theCipher);
    }

}
