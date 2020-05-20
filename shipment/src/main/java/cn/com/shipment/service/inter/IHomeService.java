package cn.com.shipment.service.inter;

import java.util.Map;


public interface IHomeService {
	 

	public void addRootQuantity(Double rootQuantity);

	public Map<String, Double> queryAllNumberInfo();

	public boolean splitNumber(String oldKey, String newValues);

	public boolean mergeNumber(String oldKeys);

	public void changeRootQuantity(Double rootQuantity);


	
}