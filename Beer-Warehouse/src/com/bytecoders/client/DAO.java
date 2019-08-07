package com.bytecoders.client;



//DAP needs to be implemented in the ClientDB Class
public interface DAO<T> {
	//add client
    boolean add(T t);
    //update client
    boolean update(T t);
    
    //delete client
    boolean delete(T t);
   
}