package com.client.impl.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

//import org.apache.log4j.Logger;

import com.client.ClientException;
import com.client.DataStoreClient;
import com.client.impl.DataStoreClientImpl;

public class DataStoreClientTest {

	byte[] addr = { 127, 0, 0, 1 };
	int port = 10024;

	private byte[] generateData(int size) {
		byte[] data = new byte[size];
		Random random = new Random();
		random.nextBytes(data);
		return data;
	}

	public void testWrite() throws UnknownHostException, ClientException {
		InetAddress inetAddress = InetAddress.getByAddress(addr);
		DataStoreClient dataStoreClient = new DataStoreClientImpl(inetAddress,
				port);
		byte[] data = generateData(100);
		dataStoreClient.write("testWrite1", data);
	}

	public void testRead() throws Exception {
		InetAddress inetAddress = InetAddress.getByAddress(addr);
		DataStoreClient dataStoreClient = new DataStoreClientImpl(inetAddress,
				port);
		byte[] data1 = generateData(100);
		dataStoreClient.write("testData", data1);
		byte[] data2 = dataStoreClient.read("testData");
		Checksum checkSum1 = new CRC32();
		checkSum1.update(data1, 0, data1.length);
		long long1 = checkSum1.getValue();
		Checksum checkSum2 = new CRC32();
		checkSum2.update(data2, 0, data2.length);
		long long2 = checkSum2.getValue();
		if (long1 != long2)
			throw new Exception("Input data is not equal to output data!");
	}

	public void testReadBroken() throws Exception {
		InetAddress inetAddress = InetAddress.getByAddress(addr);
		DataStoreClient dataStoreClient = new DataStoreClientImpl(inetAddress,
				port);
		dataStoreClient.read("missingData");
	}

	public void testDelete() throws Exception {
		InetAddress inetAddress = InetAddress.getByAddress(addr);
		DataStoreClient dataStoreClient = new DataStoreClientImpl(inetAddress,
				port);
		dataStoreClient.delete("testData");
	}

	public void testDeleteBroken() throws Exception {
		InetAddress inetAddress = InetAddress.getByAddress(addr);
		DataStoreClient dataStoreClient = new DataStoreClientImpl(inetAddress,
				port);
		dataStoreClient.delete("missingData");
	}

	public void testDirectory() throws Exception {
		InetAddress inetAddress = InetAddress.getByAddress(addr);
		DataStoreClient dataStoreClient = new DataStoreClientImpl(inetAddress,
				port);
		byte[] data = generateData(100);
		dataStoreClient.write("testData", data);
		List<String> list = dataStoreClient.directory();
		if (list.size() <= 0)
			throw new Exception("No of files in the folder is not positive.");
	}
}
