package com.client.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import com.client.ClientException;
import com.client.ObjectStoreClient;
import com.utils.StreamUtils;

public class ObjectStoreClientImpl extends DataStoreClientImpl implements
		ObjectStoreClient {

	public ObjectStoreClientImpl(InetAddress inetAddress, int port) {
		super(inetAddress, port);
	}

	public void writeObject(String name, Object object) throws ClientException {
		System.out
				.println("ObjectStoreClientImpl:writeObject: Executing write object operation.");
		Socket socket = null;

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream ooas = new ObjectOutputStream(baos);
			ooas.writeObject(object);
			ooas.close();
			byte[] data = baos.toByteArray();
			socket = connectSocket();
			StreamUtils.writeLine("write", socket.getOutputStream());
			StreamUtils.writeLine(name, socket.getOutputStream());
			StreamUtils.writeLine(((Integer) data.length).toString(),
					socket.getOutputStream());
			StreamUtils.writeData(data, socket.getOutputStream());
			String responseServer = StreamUtils.readLine(socket
					.getInputStream());
			if (responseServer != null && responseServer.equals("OK"))
				System.out
						.println("ObjectStoreClientImpl:writeObject: Write object operation successfull.");
			else
				throw new ClientException(responseServer);
		} catch (ClientException e) {
			System.out
					.println("ObjectStoreClientImpl:writeObject: Write object operation error.");
			throw new ClientException(e.getMessage());
		} catch (IOException e) {
			System.out
					.println("ObjectStoreClientImpl:writeObject: Input output operation error.");
			throw new ClientException(e.getMessage());
		} finally {
			disconnectSocket(socket);
		}
	}

	public Object readObject(String name) throws ClientException {
		System.out
				.println("ObjectStoreClientImpl:readObject: Executing read object operation.");
		Object object = null;
		Socket socket = null;

		try {
			socket = connectSocket();
			StreamUtils.writeLine("read", socket.getOutputStream());
			StreamUtils.writeLine(name, socket.getOutputStream());
			String serverResponse = StreamUtils.readLine(socket
					.getInputStream());
			if (serverResponse != null && serverResponse.equals("OK")) {
				int length = Integer.parseInt(StreamUtils.readLine(socket
						.getInputStream()));
				byte[] data = StreamUtils.readData(length,
						socket.getInputStream());
				ByteArrayInputStream baos = new ByteArrayInputStream(data);
				ObjectInputStream ois = new ObjectInputStream(baos);
				object = ois.readObject();
				System.out
						.println("ObjectStoreClientImpl:readObject: Read operation successfull.");
			} else
				throw new ClientException(serverResponse);
		} catch (NumberFormatException e) {
			System.out
					.println("ObjectStoreClientImpl:readObject: Read operation not successfull.");
			throw new ClientException(e.getMessage());
		} catch (SecurityException e) {
			System.out
					.println("ObjectStoreClientImpl:readObject: Read operation not successfull.");
			throw new ClientException(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out
					.println("ObjectStoreClientImpl:readObject: Read operation not successfull.");
			throw new ClientException(e.getMessage());
		} catch (IOException e) {
			System.out
					.println("ObjectStoreClientImpl:readObject: Read operation not successfull.");
			throw new ClientException(e.getMessage());
		}
		return object;
	}
}
