package cn.com.shipment.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.runner.RunWith;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cn.com.shipment.cache.Cache;
import cn.com.shipment.service.inter.IHomeService;

@Service
public class HomeService implements IHomeService {
	
	@Override
	public void addRootQuantity(Double rootQuantity) {
		String uuid = UUID.randomUUID().toString();
		Cache.put(uuid, rootQuantity);
		
	}

	@Override
	public Map<String, Double> queryAllNumberInfo() {
		return Cache.getMap();
	}

	@Override
	public boolean splitNumber(String oldKey, String newValues) {
		Map<String, Double> newMap = new HashMap<String, Double>();
		String[] arr = newValues.split(",");
		for(String str : arr) {
			newMap.put(UUID.randomUUID().toString(), Double.valueOf(str));
		}
		return Cache.split(oldKey, newMap);
	}

	@Override
	public boolean mergeNumber(String oldKeys) {
		Map<String, Double> cacheMap = Cache.getMap();
		Map<String, Double> oldMap = new HashMap<String, Double>();
		String[] arr = oldKeys.split(",");
		for(String oldKey : arr) {
			oldMap.put(oldKey, cacheMap.get(oldKey));
		}
		return Cache.merge(oldMap, UUID.randomUUID().toString());
	}

	@Override
	public void changeRootQuantity(Double rootQuantity) {
		Cache.changeRootQuantity(rootQuantity);
	}
	

}
