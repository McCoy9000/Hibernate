package recursos;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

public class EncriptadorFactory {
	
	public static IEncriptador getEncriptador() throws EncriptadorException {
		try {
			return new Encriptador();
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException e) {
			throw new EncriptadorException("Error al generar el encriptador", e);
		}
	}
}
