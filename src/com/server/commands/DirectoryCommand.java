package com.server.commands;

import java.io.IOException;
import java.util.List;

import com.utils.FileUtils;
import com.utils.StreamUtils;

public class DirectoryCommand extends ServerCommand {

	public void run() throws IOException {
		try {
			List<String> list = FileUtils.directory();
			sendOK();
			StreamUtils.writeLine(((Integer) list.size()).toString(),
					outputStream);
			for (String fileName : list) {
				StreamUtils.writeLine(fileName, outputStream);
			}
		} catch (Exception e) {
			System.out.println("DirectoryCommand:run: Exception occurred: "
					+ e.getMessage());
			StreamUtils.sendError(e.getMessage(), outputStream);
		} finally {
			StreamUtils.closeSocket(inputStream);
		}
	}

}
