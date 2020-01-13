package com.client.impl.test;

import java.net.UnknownHostException;

import com.client.ClientException;

public class Main {

	public static void main(String[] args) {
		DataStoreClientTest dataStoreClientTest = new DataStoreClientTest();
		ObjectStoreClientTestCase objectStoreClientTestCase = new ObjectStoreClientTestCase();
		try {
			dataStoreClientTest.testWrite();
			dataStoreClientTest.testRead();
			dataStoreClientTest.testDelete();
			dataStoreClientTest.testDirectory();
			objectStoreClientTestCase.testWrite();
			objectStoreClientTestCase.testRead();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
