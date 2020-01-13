package com.server.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.ServerException;

import com.utils.StreamUtils;

public abstract class ServerCommand {

	protected InputStream inputStream;
	protected OutputStream outputStream;

	abstract public void run() throws IOException, ServerException;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	protected void sendOK() throws IOException {
		outputStream.write("OK\n".getBytes());
		// outputStream.close();
	}

	protected void sendError(String message) throws IOException {
		StreamUtils.sendError(message, outputStream);
	}

}
