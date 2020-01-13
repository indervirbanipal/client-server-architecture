package com.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtils {

	public static String readLine(InputStream inputStream) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int ch;
		while (((ch = inputStream.read()) != '\n')) {
			if (ch == -1)
				break;
			baos.write(ch);
		}
		String result = baos.toString();
		if (result != null && result.trim().equals(""))
			result = "0";
		return result;
	}

	public static byte[] readData(int length, InputStream inputStream)
			throws IOException {
		byte[] data = new byte[length];
		for (int i = 0; i < length; i++) {
			data[i] = (byte) inputStream.read();
		}
		return data;
	}

	public static void writeData(byte[] data, OutputStream outputStream)
			throws IOException {
		outputStream.write(data);
		outputStream.flush();
	}

	public static void writeLine(String line, OutputStream outputStream)
			throws IOException {
		if (!line.endsWith("\n"))
			line = line + "\n";
		outputStream.write(line.getBytes());
		outputStream.flush();
	}

	public static void closeSocket(InputStream inputStream) throws IOException {
		if (inputStream != null)
			inputStream.close();
	}

	public static void closeSocket(OutputStream outputStream)
			throws IOException {
		if (outputStream != null)
			outputStream.close();
	}

	public static void sendError(String error, OutputStream outputStream)
			throws IOException {
		if (!error.endsWith("\n"))
			error = error + "\n";
		outputStream.write(error.getBytes());
		outputStream.flush();
	}

}
