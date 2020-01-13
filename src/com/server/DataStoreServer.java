package com.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.ServerException;

import com.server.commands.DeleteCommand;
import com.server.commands.DirectoryCommand;
import com.server.commands.ReadCommand;
import com.server.commands.ServerCommand;
import com.server.commands.WriteCommand;
import com.utils.StreamUtils;

public class DataStoreServer {

	public static final int port = 10024;

	public void startUp() throws IOException {
		System.out.println("DataStoreServer:startUp: Starting server at "
				+ port);
		InputStream inputStream = null;
		OutputStream outputStream = null;
		@SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(port);
		while (true) {
			try {
				System.out
						.println("DataStoreServer:startUp: Waiting for request...");
				Socket clientSocket = serverSocket.accept();
				inputStream = clientSocket.getInputStream();
				outputStream = clientSocket.getOutputStream();
				ServerCommand serverCommand = dispatchCommand(inputStream);
				System.out
						.println("DataStoreServer:startUp: Processing request: "
								+ serverCommand);
				serverCommand.setInputStream(inputStream);
				serverCommand.setOutputStream(outputStream);
				serverCommand.run();
				StreamUtils.closeSocket(inputStream);
				System.out
						.println("DataStoreServer:startUp: Request executed, Response sent!");
			} catch (Exception e) {
				System.out
						.println("DataStoreServer:startUp: Exception occurred: "
								+ e.getMessage());
				StreamUtils.sendError(e.getMessage(), outputStream);
				StreamUtils.closeSocket(inputStream);
			}
		}
	}

	private ServerCommand dispatchCommand(InputStream inputStream)
			throws ServerException {
		try {
			ServerCommand serverCommand = null;
			String operation = StreamUtils.readLine(inputStream);
			if (operation != null) {
				if (operation.equals("write")) {
					serverCommand = new WriteCommand();
				} else if (operation.equals("read")) {
					serverCommand = new ReadCommand();
				} else if (operation.equals("delete")) {
					serverCommand = new DeleteCommand();
				} else if (operation.equals("directory")) {
					serverCommand = new DirectoryCommand();
				} else {
					throw new ServerException("Command not found!");
				}
			}
			return serverCommand;
		} catch (IOException e) {
			System.out
					.println("DataStoreServer:dispatchCommand: Exception occurred: "
							+ e.getMessage());
			throw new ServerException(e.getMessage());
		}
	}

	public static void main(String[] args) {
		DataStoreServer dataStoreServer = new DataStoreServer();
		try {
			System.out
					.println("DataStoreServer:main: DataStoreServer is up and running!");
			dataStoreServer.startUp();
		} catch (IOException e) {
			System.out.println("DataStoreServer:main: Exception occurred: "
					+ e.getMessage());
		}
	}

}
