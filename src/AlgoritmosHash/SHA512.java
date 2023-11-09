package AlgoritmosHash;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class SHA512 {
	
	public static String getSHA512(String input) throws NoSuchAlgorithmException {
	    MessageDigest md = MessageDigest.getInstance("SHA-512");
	    byte[] messageDigest = md.digest(input.getBytes());
	    return bytesToHex(messageDigest); 
	}
	
	public static String bytesToHex(byte[] bytes) {
	    StringBuilder sb = new StringBuilder();
	    for (byte b : bytes) {
	      sb.append(String.format("%02x", b));
	    }
	    return sb.toString();
	  }


	public String HashingSDA512(String input) throws NoSuchAlgorithmException {
		String solucion = getSHA512(input);
		return solucion ;
	}
	public SHA512() {
	}

}
