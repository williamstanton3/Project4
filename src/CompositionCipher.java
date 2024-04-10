import java.util.ArrayList;

public class CompositionCipher extends Cipher {
    private final static int NUM_LETTERS = 26;
    private ArrayList<Cipher> cList;

    public CompositionCipher() {
        cList = new ArrayList<>();
    }
    public CompositionCipher (CompositionCipher other) {
        this.cList = other.cList;
    }

    @Override
    public char encrypt (char c) {
        // step 1
        CaesarCipher step1 = new CaesarCipher(3);
        char myChar = step1.encrypt(c);
        // step 2
        CaesarCipher step2 = new CaesarCipher(5);
        myChar = step2.encrypt(myChar);
        // step 3
        CaesarCipher step3 = new CaesarCipher(20);
        myChar = step3.encrypt(myChar);

        return myChar;

    }

    @Override
    public char decrypt (char c) {
        // step 1
        CaesarCipher step1 = new CaesarCipher(20);
        char myChar = step1.decrypt(c);
        // step 2
        CaesarCipher step2 = new CaesarCipher(5);
        myChar = step2.decrypt(myChar);
        // step 3
        CaesarCipher step3 = new CaesarCipher(3);
        myChar = step3.decrypt(myChar);

        return myChar;
    }

    @Override
    public Cipher newCopy() {
        return new CompositionCipher(this);
    }

    public void add (Cipher theCipher) {
        cList.add(theCipher);
    }

}
