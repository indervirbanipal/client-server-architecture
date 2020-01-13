package com.server.commands;

import java.io.IOException;

import com.utils.FileUtils;
import com.utils.StreamUtils;

public class DeleteCommand extends ServerCommand {

	public void run() throws IOException {
		try {
			String fileName = StreamUtils.readLine(inputStream);
			FileUtils.delete(fileName);
			sendOK();
		} catch (Exception e) {
			System.out.println("DeleteCommand:run: Exception occurred: "
					+ e.getMessage());
			StreamUtils.sendError(e.getMessage(), outputStream);
		}
	}

}
