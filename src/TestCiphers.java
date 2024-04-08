import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class TestCiphers {
	public static String runTest(Cipher ci, String answer, boolean useCharByChar){
		final String testString = "abcdefghijklmnopqrstuvwxyzABCDLMNOXYZ.$\t() {+}012389";
		assert(testString.length() == answer.length());

		// Ensure that newCopy makes a deep copy
		//  by encrypting 'x' with the deep copy
		//  (but not the original)
		Cipher deepCopy = ci.newCopy();
		deepCopy.encrypt('x');
		
		
		if(useCharByChar){
			// Iterate through the test string, encrypting each character
			//   and checking against the corresponding answer character
			for(int i=0; i<testString.length(); i++){
				char res = ci.encrypt(testString.charAt(i));
				if(res != answer.charAt(i)){
					return "Encrypt failed for input character '" +
							testString.charAt(i) + "'.\n The result was '" + res + 
							"'\n but it should have been '" + answer.charAt(i) + "'\n";
				}
			}
	
			for(int i=0; i<answer.length(); i++){
				char res = ci.decrypt(answer.charAt(i));
				if(res != testString.charAt(i)){
					return "Decrypt failed for input character '" +
							answer.charAt(i) + "'.\n The result was '" + res + 
							"'\n but it should have been '" + testString.charAt(i) + "'\n";
				}
			}
		}
		else{
			String secret = ci.encrypt(testString);
			if(! secret.equals(answer)){
				return "String encrypt failed.\n" +
				"Result:  " + secret + "\n" +
				"Correct: " + answer + "\n";
			}
			
			String res = ci.decrypt(secret);
			if(! res.equals(testString)){
				return "String decrypt failed.\n" +
				"Result:  " + res + "\n" +
				"Correct: " + testString + "\n";
			}
		}

		return "";
	}

	@Test
	public void caesarOnly(){
		CaesarCipher cc = new CaesarCipher(17);
		String answer = "rstuvwxyzabcdefghijklmnopqRSTUCDEFOPQ.$\t() {+}012389";
		assertEquals("", runTest(cc, answer, true));
	}
	
	@Test
	public void vigenereOnly(){
		Integer[] shifts = {5, 3, 16};
		VigenereCipher vc = new VigenereCipher( Arrays.asList(shifts) );
		String answer = "fesihvlkyonbrqeuthxwkazndcQGFTQPDTAOE.$\t() {+}012389";
		
		// Check for deep copy by changing state with an "encrypt" call
		VigenereCipher copy = new VigenereCipher(vc);
		copy.encrypt('x');
		
		assertEquals("", runTest(vc, answer, true));
	}
	
	
	@Test
	public void compositionWithCaesar(){
		CompositionCipher comp_cc = new CompositionCipher();
		CaesarCipher temp_cc = new CaesarCipher(18);
		comp_cc.add(temp_cc);
		
		String answer = "stuvwxyzabcdefghijklmnopqrSTUVDEFGPQR.$\t() {+}012389";
		
		assertEquals("", runTest(comp_cc, answer, true));	
	}
	
	
	@Test
	public void caesarOnly_StringMethods(){
		CaesarCipher cc = new CaesarCipher(17);
		String answer = "rstuvwxyzabcdefghijklmnopqRSTUCDEFOPQ.$\t() {+}012389";
		assertEquals("", runTest(cc, answer, false));
	}
	
	@Test
	public void vigenereOnly_StringMethods(){
		Integer[] shifts = {5, 3, 16};
		VigenereCipher vc = new VigenereCipher( Arrays.asList(shifts) );
		String answer = "fesihvlkyonbrqeuthxwkazndcQGFTQPDTAOE.$\t() {+}012389";
		
		// Check for deep copy by changing state with an "encrypt" call
		VigenereCipher copy = new VigenereCipher(vc);
		copy.encrypt('x');
		
		assertEquals("", runTest(vc, answer, false));
	}
	
	
	@Test
	public void compositionWithCaesar_StringMethods(){
		CompositionCipher comp_cc = new CompositionCipher();
		CaesarCipher temp_cc = new CaesarCipher(18);
		comp_cc.add(temp_cc);
		
		String answer = "stuvwxyzabcdefghijklmnopqrSTUVDEFGPQR.$\t() {+}012389";
		
		assertEquals("", runTest(comp_cc, answer, false));	
	}
	
	
	@Test
	public void compositionWithVigenere(){
		CompositionCipher comp_vc = new CompositionCipher();
		Integer[] shifts = {7, 6, 9};
		VigenereCipher temp_vc = new VigenereCipher( Arrays.asList(shifts) );
		comp_vc.add(temp_vc);
		String answer = "hhlkkonnrqquttxwwazzdccgffJIIMSSWVDHG.$\t() {+}012389";
			
		// Since VigenereCipher is mutable (i.e., it changes which shift amount
		//   to use next each time a character is encrypted/decrypted),
		// we check for a deep copy by changing state with an "encrypt" call
		CompositionCipher copy = new CompositionCipher(comp_vc);
		copy.encrypt('x');
			
		assertEquals("", runTest(comp_vc, answer, true));
	}

	@Test
	public void compWithCompWithCaesar(){
		CompositionCipher comp_comp_cc = new CompositionCipher();
		CompositionCipher temp_comp = new CompositionCipher();
		CaesarCipher temp_cc = new CaesarCipher(4);
		String answer = "efghijklmnopqrstuvwxyzabcdEFGHPQRSBCD.$\t() {+}012389";
		
		temp_comp.add(temp_cc);
		comp_comp_cc.add(temp_comp);
		
		assertEquals("", runTest(comp_comp_cc, answer, true));
	}

	@Test
	public void compWithTwoCaesars(){
		CompositionCipher comp_cc_cc = new CompositionCipher();
		CaesarCipher temp_cc1 = new CaesarCipher(19);
		CaesarCipher temp_cc2 = new CaesarCipher(12);
		String answer = "fghijklmnopqrstuvwxyzabcdeFGHIQRSTCDE.$\t() {+}012389";

		comp_cc_cc.add(temp_cc1);
		comp_cc_cc.add(temp_cc2);

		assertEquals("", runTest(comp_cc_cc, answer, true));
	}
	
	
	@Test
	public void compWithTwoVigeneres(){
		CompositionCipher comp_vc_vc = new CompositionCipher();
		{
			Integer[] v1 = {2, 6};
			Integer[] v2 = {12, 13, 14};
			
			VigenereCipher temp_vc1 = new VigenereCipher(Arrays.asList(v1));
			VigenereCipher temp_vc2 = new VigenereCipher(Arrays.asList(v2));
			
			comp_vc_vc.add(temp_vc1);
			comp_vc_vc.add(temp_vc2);
		}
		String answer = "ousvtzuaybzfagehflgmknlrmsQTRXZFDGMSN.$\t() {+}012389";
		
		assertEquals("", runTest(comp_vc_vc, answer, true));
	}
	
	@Test
	public void compWithCaesarAndVigenere(){
		CompositionCipher comp_vc_cc = new CompositionCipher();
		Integer[] v = {12, 13, 14};

		VigenereCipher temp_vc = new VigenereCipher(Arrays.asList(v));
		CaesarCipher temp_cc = new CaesarCipher(5);
		comp_vc_cc.add(temp_vc);
		comp_vc_cc.add(temp_cc);
			
		String answer = "rtvuwyxzbacedfhgikjlnmoqprTSUWCEGFPRQ.$\t() {+}012389";

		assertEquals("", runTest(comp_vc_cc, answer, true));
	}

	@Test
	public void compWithEverything(){
		CompositionCipher comp_mix = new CompositionCipher();

		Integer[] v1 = {2, 6};
		Integer[] v2 = {12, 13, 14};

		CompositionCipher temp_comp = new CompositionCipher();
		VigenereCipher temp_vc1 = new VigenereCipher(Arrays.asList(v1));
		VigenereCipher temp_vc2 = new VigenereCipher(Arrays.asList(v2));
		CaesarCipher temp_cc1 = new CaesarCipher(19);
		CaesarCipher temp_cc2 = new CaesarCipher(8);
		
		temp_comp.add(temp_vc1);
		temp_comp.add(temp_cc1);
		
		comp_mix.add(temp_cc2);
		comp_mix.add(temp_comp);
		comp_mix.add(temp_vc2);

		String answer = "pvtwuavbzcagbhfigmhnlomsntRUSYAGEHNTO.$\t() {+}012389";
			
		assertEquals("", runTest(comp_mix, answer, true));
	}
	

}
