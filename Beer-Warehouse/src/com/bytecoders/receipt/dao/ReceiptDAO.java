package com.bytecoders.receipt.dao;

import java.util.List;

import com.bytecoders.receipt.Receipt;

public interface ReceiptDAO {
	public List<String> getAllIds();
	public Receipt generateReceipt(int orderID);	
}
