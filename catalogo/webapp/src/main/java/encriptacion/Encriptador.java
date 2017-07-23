package encriptacion;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Encriptador {

	String Key = "todostusmuertos";
	byte[] KeyData = Key.getBytes();

	public SecretKeySpec KS = new SecretKeySpec(KeyData, "Blowfish");

	public Cipher cipher = Cipher.getInstance("Blowfish");

	public Encriptador() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {

		cipher.init(Cipher.ENCRYPT_MODE, KS);

	}

	public String encriptar(String password) {
		
		
		byte[] encPassword = null;
		
		if (password != null) {
			try {
				encPassword = this.cipher.doFinal(password.getBytes());
			} catch (IllegalBlockSizeException | BadPaddingException e1) {
				e1.printStackTrace();
			}
			password = Base64.getMimeEncoder().encodeToString(encPassword);
		}
		
		return password;
	}
}
