package com.client;

public interface ObjectStoreClient extends DataStoreClient {

	void writeObject(String name, Object object) throws ClientException;

	Object readObject(String name) throws ClientException;

}
