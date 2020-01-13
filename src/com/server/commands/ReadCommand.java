package com.server.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.utils.FileUtils;
import com.utils.ServerException;
import com.utils.StreamUtils;

public class ReadCommand extends ServerCommand {

	public void run() throws IOException {
		String fileName;
		InputStream inputStream = this.inputStream;
		OutputStream outputStream = this.outputStream;
		try {
			fileName = StreamUtils.readLine(inputStream);
			byte[] data = FileUtils.readData(fileName);
			sendOK();
			String length = new Integer(data.length).toString();
			StreamUtils.writeLine(length, outputStream);
			StreamUtils.writeData(data, outputStream);
		} catch (IOException e) {
			System.out.println("ReadCommand:run: Exception occurred: "
					+ e.getMessage());
			StreamUtils.sendError(e.getMessage(), outputStream);
		} catch (ServerException e) {
			System.out.println("ReadCommand:run: Exception occurred: "
					+ e.getMessage());
			StreamUtils.sendError(e.getMessage(), outputStream);
		} finally {
			StreamUtils.closeSocket(inputStream);
		}
	}

}
