package com.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

	static File directory;

	static {
		directory = new File("data");
		if (!directory.exists())
			directory.mkdir();
	}

	public static void writeData(String name, byte[] data) throws IOException {
		File file = new File(directory, name);
		file.createNewFile();
		OutputStream outputStream = new FileOutputStream(file);
		for (int i = 0; i < data.length; i++) {
			outputStream.write(data[i]);
		}
		outputStream.close();
	}

	public static byte[] readData(String name) throws ServerException,
			IOException {
		File file = new File(directory, name);
		if (!file.exists())
			throw new ServerException("File not found: " + name);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream inputStream = new FileInputStream(file);
		int ch;
		while (true) {
			ch = inputStream.read();
			if (ch == -1)
				break;
			baos.write(ch);
		}
		inputStream.close();
		return baos.toByteArray();
	}

	public static boolean delete(String name) throws ServerException {
		File file = new File(directory, name);
		if (!file.exists())
			throw new ServerException("File not found: " + name);
		return file.delete();
	}

	public static List<String> directory() {
		List<String> list = new ArrayList<String>();
		if (directory.exists()) {
			File[] files = directory.listFiles();
			for (File file : files) {
				list.add(file.getName());
			}
		}
		return list;
	}

}
