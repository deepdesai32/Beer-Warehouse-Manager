package com.bytecoders.costs.dao;

import java.util.ArrayList;

public interface CostsDAO {
	ArrayList<String> getAllIds();
	public double getBatchCost(int batchID);
}
