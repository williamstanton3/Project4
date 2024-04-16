
public class CaesarCipher extends Cipher {
	private final static int NUM_LETTERS = 26;
	private final int shiftAmount;
	
	/**
	 * @param amt - the distance to shift letters when encrypting
	 */
	public CaesarCipher(int amt) {
		shiftAmount = amt;
	}

	/**
	 * Creates a new CaesarCipher object that is a copy of the given CaesarCipher.
	 * @param other the CaesarCipher to copy
	 */
	public CaesarCipher (CaesarCipher other) {
		this.shiftAmount = other.shiftAmount;
	}

	/**
	 * Encrypts a character by shifting the letter type by the shift amount
	 * @param c the character to encrypt
	 * @return c the encrypted character
	 */
	@Override
	public char encrypt(char c) {
		if(Character.isAlphabetic(c)){
			final char base = (Character.isLowerCase(c) ? 'a' : 'A');		
			// c - base is the index in the alphabet: 'a' becomes 0, 'b' becomes 1, etc.
			return (char)(base + ((c - base + shiftAmount) % NUM_LETTERS));
		}
		else {
			return c;
		}
	}

	/**
	 * Reverse the encryption process by taking a letter and shifting the letter type by the opposite of the shiftAmount
	 * @param c the character to decrypt
	 * @return c the decrypted character
	 */
	@Override
	public char decrypt(char c) {
		if(Character.isAlphabetic(c)){
			final char base = (Character.isLowerCase(c) ? 'a' : 'A');			
			return (char)(base + ((c - base - shiftAmount + NUM_LETTERS) % NUM_LETTERS));
		}
		else{
			return c;
		}
	}

	/**
	 * @return a new object, a deep copy of the current object
	 */
	@Override
	public Cipher newCopy() {
		return new CaesarCipher(this);
	}

}
