package AlgoritmosHash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {
	
	public static String getSHA256(String input) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] messageDigest = md.digest(input.getBytes());
		return bytesToHex(messageDigest);
		
	}
	
	public static String bytesToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for(byte b: bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}
	
	public SHA256() {
		
	}

}
