package com.frxs.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5Util {

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	public static String getFileMD5(File file) {
		MessageDigest digest = null;
		FileInputStream fis = null;
		byte[] buffer = new byte[1024];

		try {
			if (!file.isFile()) {
				return "";
			}

			digest = MessageDigest.getInstance("MD5");
			fis = new FileInputStream(file);

			while (true) {
				int len;
				if ((len = fis.read(buffer, 0, 1024)) == -1) {
					fis.close();
					break;
				}

				digest.update(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		BigInteger var5 = new BigInteger(1, digest.digest());
		return String.format("%1$032x", new Object[]{var5});
	}

}
