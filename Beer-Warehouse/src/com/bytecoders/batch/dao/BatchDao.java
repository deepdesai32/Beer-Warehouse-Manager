package com.bytecoders.batch.dao;

import java.util.List;

import com.bytecoders.batch.Batch;
import com.bytecoders.item.ItemRegistration;


//This is an interface for acessing the data base
public interface BatchDao {
	
	//Insert a provided batch into the database
	public boolean insertBatch(Batch batch);

	//Updates based on a given batch, using its ID
	public boolean updateBatch(Batch batch);

	//Deletes a given batch based on the ID 
	public boolean deleteBatch(Batch batch);
	
	//Select a list of batches, it has different choices to select the data
	public List<Batch> selectAllBatches(int choice);
	
	//Select only one batch
	public Batch selectBatch(Batch targetBatch);
	
	//Used to select a list of Items from the Items table
	public List<ItemRegistration> selectAllItems();
		
}
