package com.server.commands;

import java.io.IOException;

import com.utils.FileUtils;
import com.utils.StreamUtils;

public class WriteCommand extends ServerCommand {

	public void run() throws IOException {
		try {
			String fileName = StreamUtils.readLine(inputStream);
			Integer length = Integer
					.parseInt(StreamUtils.readLine(inputStream));
			byte[] data = StreamUtils.readData(length, inputStream);
			FileUtils.writeData(fileName, data);
			sendOK();
		} catch (IOException e) {
			System.out.println("WriteCommand:run: Exception occurred - "
					+ e.getMessage());
			StreamUtils.sendError(e.getMessage(), outputStream);
		} finally {
			StreamUtils.closeSocket(inputStream);
		}
	}
}
