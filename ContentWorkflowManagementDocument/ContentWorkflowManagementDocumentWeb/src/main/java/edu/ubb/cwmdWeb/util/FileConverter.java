package edu.ubb.cwmdWeb.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileConverter {

	public static byte[] convertFileContentToByteArray(InputStream inputStream) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[16384];
		try {
			while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}
			buffer.flush();
			return buffer.toByteArray();
		} finally {
			inputStream.close();
			buffer.close();
		}
	}

	// http://stackoverflow.com/questions/1802123/can-we-convert-a-byte-array-into-an-inputstream-in-java
	public static InputStream convertByteArrayToInputStream(byte[] content) {
		InputStream is = new ByteArrayInputStream(content);
		return is;
	}

}
