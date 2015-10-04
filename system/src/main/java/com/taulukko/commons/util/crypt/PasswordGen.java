package com.taulukko.commons.util.crypt;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;

import com.taulukko.commons.util.lang.EString;

public class PasswordGen {
	
	public static final String VERSION_2_0_0 = "0200";
	public static final String VERSION_1_0_0 = "0100";
	
	private static final String SHA = "SHA";

	private static final String MD5 = "MD5";

	private String salt = "FLUX CAPACITOR";

	private String version;

	public static void main(String argsv[]) {

		PasswordGen genpass2 = new PasswordGen(VERSION_2_0_0,"FLUX CAPACITOR");
		PasswordGen genpass = new PasswordGen("0100","FLUX CAPACITOR");

		System.out.println("Senha default(aoors$dqjq55q)"
				+ genpass2.subCrypt("aoors$dqjq55q"));
		System.out.println("Senha MD5(aoors$dqjq55q)"
				+ genpass2.cryptMD5("aoors$dqjq55q"));
		System.out.println("Senha SHA(aoors$dqjq55q)"
				+ genpass2.cryptSHA("aoors$dqjq55q"));

		System.out.println("Senha crypt(aoors$dqjq55q)"
				+ genpass2.subCrypt("aoors$dqjq55q"));

		try {
			System.out.println("Senha crypt(aoors$dqjq55q,0100)"
					+ genpass.crypt("aoors$dqjq55q"));
			System.out.println("Senha crypt(aoors$dqjq55q,0200)"
					+ genpass2.crypt("aoors$dqjq55q"));

			// boiada
			String encrypted = "4a4a7a6a54596f6b71324e634a776e76744446646e6b5174706453696679596f4177362f63624158356f616c596d574c6c65345341676d4230647568507947445979324565613030765767343453783841537977514348434d5375362b6d677461566b514f6e674a624638344c7530527147323169497730427645595679614530527a5a64714672787454503932324a426d334b756d536b477350547a72457137694b6f2f4432613935303d";
			String privateKey64 = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJBQClByqvwaHTxV0RxLOSVDdbwq1ocb6xJCS39xNqgAfjPavU02GBr5cM9dCT6l/IfMY+pv0c/ttHaajTrlB0y2wT6oQY9pukWCdkAH1wyOhZAssJNnnRoOEe8L8td/txWAYp+/PRlUA8LjbyvQu4BA4ay1eBk87d+Wwkt4QaozAgMBAAECgYBjfjtOMXA+tMZZNZUgm5//V94Q57vVlGLhyMECUQxskUJw/6qpU9UUq8qhvDPGdGuBOGHeXE9bujdD2dPqucHokF/kSlBMULKiw9bigQOvVDGbvyb+CiRcPzpeVxlFlzAnjnCol1dedTEvpyuyD9mLxpADMz7Wt7Pgshsug2py+QJBANTnGtEZkmQu0BioEPAnuP4ksLj8INZwC9UheTTuGmk+LCgxtZeOt56Hezz4X/Y/nY30SFXG2lx6T0qgpe8Nj90CQQCthoIhXTiajoyxC0nGCIQpH9W99/dimESoE7uPqj7WuL0dD6IgBWvr2HspgHX+6uSYrEgQ1bpsVQioNqbumYlPAkByir+fvc1HvvPQQCOUwRpDCQRXEbatB+0lxnOKaYumEX/6Q/w5xNu/G47PF6WxOzqN095S38A9PsSIFvQPL9G1AkB+lg5eSoQdc/o8uXBjEBlhasDhaYBFo7EKMAL2xgo7V99V0O7Dj1DwZydJ2lW51h9XJO8ZoODzYIEFPYE/tC/PAkABUlklYjLDjD4Z5RBI6sbQyiDxzAVkAiKMHFwN89pse0xXKK7bUfbLhvRX9qMMyFMAujAmjG1iHCQq0YTIuK5k";

			System.out
					.println("Senha decript(hexbase64cryptedPassword,privateKey64)"
							+ genpass2
									.decryptHexFromRSA(encrypted, privateKey64));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public PasswordGen() {
		this(VERSION_2_0_0, "FLUX CAPACITOR");
	}

	public PasswordGen(String version, String salt) {
		this.version = version;
		this.salt = salt;
	}

	public String decryptHexFromRSA(String passwordEncryptedHex64,
			String privateKey64) throws Exception {
		PrivateKey privateKey = convertToPrivateKey(privateKey64);

		String passwordEncrypted64 = hexToString(passwordEncryptedHex64);
		byte passwordEncrypted[] = Base64.decode(passwordEncrypted64);

		Cipher cipher = Cipher.getInstance("RSA");

		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		byte[] decryptedPasswordBytes = cipher.doFinal(passwordEncrypted);

		String password = new String(decryptedPasswordBytes);

		return password;
	}

	public EKeyPair createKeys() throws NoSuchAlgorithmException {
		KeyPairGenerator geradorDeChave = KeyPairGenerator.getInstance("RSA");
		geradorDeChave.initialize(1024);

		KeyPair keypar = geradorDeChave.generateKeyPair();

		Key pubkey = keypar.getPublic();
		Key privkey = keypar.getPrivate();
		String publicKey64 = Base64.encode(pubkey.getEncoded());
		String privateKey64 = Base64.encode(privkey.getEncoded());

		EKeyPair ekeypar = new EKeyPair();
		ekeypar.setPrivateKey64(privateKey64);
		ekeypar.setPublicKey64(publicKey64);

		return ekeypar;
	}

	private PrivateKey convertToPrivateKey(String base64Key) throws Exception {
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
				Base64.decode(base64Key));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	public String hexToString(String hex) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < hex.length() - 1; i += 2) {
			String output = hex.substring(i, (i + 2)); // grab the hex in pairs
			int decimal = Integer.parseInt(output, 16); // convert hex to
														// decimal
			sb.append((char) decimal); // convert the decimal to character
		}

		return sb.toString();
	}

	public String crypt(String text) throws Exception {
		if (version.equals(VERSION_1_0_0)) {
			return subCrypt(text);
		} else if (version.equals(VERSION_2_0_0)) {
			return VERSION_2_0_0 + "==" + subCrypt(text + salt);
		} else {
			throw new Exception("Encrypt version not suported");
		}

	}

	private String subCrypt(String sPassword) {
		try {

			// cria a conta no l2-login
			MessageDigest md = MessageDigest.getInstance(MD5);
			byte[] raw = sPassword.getBytes("UTF-8");
			byte[] hash = md.digest(raw);
			// sPassword = EString.toHexString(hash).toString();
			sPassword = Base64.encode(hash, 0);
			return sPassword;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private String cryptBase(String sPassword, String sMethod) {
		try {

			// cria a conta no l2-login
			MessageDigest md = MessageDigest.getInstance(sMethod);
			byte[] raw = sPassword.getBytes("UTF-8");
			byte[] hash = md.digest(raw);
			// sPassword = EString.toHexString(hash).toString();
			sPassword = Base64.encode(hash, 0);
			return sPassword;
		} catch (Exception e) {
			return null;
		}

	}

	public String cryptMD5(String sPassword) {
		return cryptBase(sPassword, MD5);

	}

	public String cryptSHA(String sPassword) {
		return cryptBase(sPassword, SHA);

	}

	public String clearPass(String pass) {
		if (pass.startsWith(VERSION_2_0_0 + "==")) {
			String salt = "FLUX CAPACITOR";
			return clearPass(subCrypt(pass + salt));
		}

		EString empty = new EString("");
		String valid = "abcdefghijklmnopqrstuvxzwyABCDEFGHIJKLMNOPQRSTUVXYZW0123456789";
		for (int cont = 0; cont < pass.length(); cont++) {
			if (valid.indexOf(String.valueOf(pass.charAt(cont))) < 0) {
				pass = new EString(pass).replace(
						new EString(String.valueOf(pass.charAt(cont))), empty)
						.toString();
				cont--;
			}
		}
		return pass;
	}

}
