package com.client;

import java.util.List;

public interface DataStoreClient {

	void write(String name, byte[] data) throws ClientException;

	byte[] read(String name) throws ClientException;

	void delete(String name) throws ClientException;

	List<String> directory() throws ClientException;

}
