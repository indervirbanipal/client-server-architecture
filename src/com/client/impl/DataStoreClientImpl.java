package com.client.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import com.client.ClientException;
import com.client.DataStoreClient;
import com.utils.StreamUtils;

public class DataStoreClientImpl implements DataStoreClient {

	private InetAddress inetAddress;
	private int port;

	public DataStoreClientImpl(InetAddress inetAddress, int port) {
		this.inetAddress = inetAddress;
		this.port = port;
	}

	public Socket connectSocket() throws ClientException {
		SocketAddress socketAddress = new InetSocketAddress(inetAddress, port);
		Socket socket = new Socket();
		try {
			socket.connect(socketAddress);
		} catch (IOException e) {
			System.out
					.println("DataStoreClientImpl:connectSocket: Error while connecting the socket.");
			throw new ClientException(e.getMessage());
		}
		return socket;
	}

	public void disconnectSocket(Socket socket) throws ClientException {
		if (socket != null)
			try {
				StreamUtils.closeSocket(socket.getInputStream());
			} catch (IOException e) {
				System.out
						.println("DataStoreClientImpl:disconnectSocket: Error while closing the socket.");
				throw new ClientException(
						"DataStoreClientImpl:disconnectSocket: Error while closing the socket.");
			}
	}

	public void write(String name, byte[] data) throws ClientException {
		System.out
				.println("DataStoreClientImpl:write: Executing write operation.");
		Socket socket = null;
		try {
			socket = connectSocket();
			StreamUtils.writeLine("write", socket.getOutputStream());
			StreamUtils.writeLine(name, socket.getOutputStream());
			StreamUtils.writeLine("" + ((Integer) data.length).toString(),
					socket.getOutputStream());
			StreamUtils.writeData(data, socket.getOutputStream());
			String responseServer = StreamUtils.readLine(socket
					.getInputStream());
			if (responseServer != null && responseServer.equals("OK"))
				System.out
						.println("DataStoreClientImpl:write: Write operation successfull.");
			else
				throw new ClientException(responseServer);
		} catch (IOException e) {
			System.out
					.println("DataStoreClientImpl:write: Error while writing.");
			throw new ClientException(e.getMessage());
		} finally {
			disconnectSocket(socket);
		}
	}

	public byte[] read(String name) throws ClientException {
		System.out
				.println("DataStoreClientImpl:read: Executing read operation.");
		byte[] data = null;
		Socket socket = null;
		try {
			socket = connectSocket();
			StreamUtils.writeLine("read", socket.getOutputStream());
			StreamUtils.writeLine(name, socket.getOutputStream());
			String responseServer = StreamUtils.readLine(socket
					.getInputStream());
			if (responseServer != null && responseServer.equals("OK")) {
				System.out
						.println("DataStoreClientImpl:read: Read operation successfull.");
				Integer length = Integer.parseInt(StreamUtils.readLine(socket
						.getInputStream()));
				data = StreamUtils.readData(length, socket.getInputStream());
				System.out
						.println("DataStoreClientImpl:read: Read operation successfully done.");
			} else {
				throw new ClientException(responseServer);
			}
		} catch (IOException e) {
			System.out
					.println("DataStoreClientImpl:read: Error while reading.");
			throw new ClientException(e.getMessage());
		} finally {
			disconnectSocket(socket);
		}
		return data;
	}

	public void delete(String name) throws ClientException {
		System.out
				.println("DataStoreClientImpl:delete: Executing delete operation.");
		Socket socket = null;
		try {
			socket = connectSocket();
			StreamUtils.writeLine("delete", socket.getOutputStream());
			StreamUtils.writeLine(name, socket.getOutputStream());
			String responseServer = StreamUtils.readLine(socket
					.getInputStream());
			if (responseServer != null && responseServer.equals("OK"))
				System.out
						.println("DataStoreClientImpl:delete: Delete operation successfull.");
			else
				throw new ClientException(responseServer);
		} catch (IOException e) {
			System.out
					.println("DataStoreClientImpl:delete: Delete operation error.");
			throw new ClientException(e.getMessage());
		} finally {
			disconnectSocket(socket);
		}
	}

	public List<String> directory() throws ClientException {
		System.out
				.println("DataStoreClientImpl:directory: Executing directory operation.");
		List<String> list = null;
		Socket socket = null;
		try {
			socket = connectSocket();
			StreamUtils.writeLine("directory\n", socket.getOutputStream());
			String responseServer = StreamUtils.readLine(socket
					.getInputStream());
			if (responseServer != null && responseServer.equals("OK")) {
				System.out
						.println("DataStoreClientImpl:directory: Delete operation successfull.");
				Integer length = Integer.parseInt(StreamUtils.readLine(socket
						.getInputStream()));
				String fileName = "";
				list = new ArrayList<String>();
				for (int i = 0; i < length; i++) {
					fileName = StreamUtils.readLine(socket.getInputStream());
					list.add(fileName);
				}
				System.out
						.println("DataStoreClientImpl:directory: Directory operation successfull.");
			}
		} catch (IOException e) {
			System.out
					.println("DataStoreClientImpl:directory: Directory operation error.");
			throw new ClientException(e.getMessage());
		} finally {
			disconnectSocket(socket);
		}
		return list;
	}

}
