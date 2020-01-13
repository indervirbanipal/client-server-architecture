package com.client.impl.test;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import com.client.ObjectStoreClient;
import com.client.impl.ObjectStoreClientImpl;

public class ObjectStoreClientTestCase {

	byte[] addr = { 127, 0, 0, 1 };
	int port = 10024;

	public List<String> generateData(int size) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < size; i++) {
			list.add("Now isthe time for all good men " + i);
		}
		return list;
	}

	public void testWrite() throws Exception {
		InetAddress inetAddress = InetAddress.getByAddress(addr);
		ObjectStoreClient objectStoreClient = new ObjectStoreClientImpl(
				inetAddress, port);
		List<String> data = generateData(100);
		objectStoreClient.writeObject("listData", data);
	}

	public void testRead() throws Exception {
		InetAddress inetAddress = InetAddress.getByAddress(addr);
		ObjectStoreClientImpl objectStoreClientImpl = new ObjectStoreClientImpl(
				inetAddress, port);
		List<String> data1 = generateData(100);
		objectStoreClientImpl.writeObject("listData", data1);
		@SuppressWarnings("unchecked")
		List<String> data2 = (List<String>) objectStoreClientImpl
				.readObject("listData");
		if (data2.size() != 100)
			throw new Exception("Incomplete data has been read!");
	}

}
