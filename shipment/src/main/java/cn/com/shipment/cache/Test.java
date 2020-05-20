package cn.com.shipment.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Test {

	public static void main(String[] args) {
		String uuid = UUID.randomUUID().toString();
		Cache.put(uuid, 100d);
		Map newMap = new HashMap();
		newMap.put(UUID.randomUUID().toString(), 50);
		newMap.put(UUID.randomUUID().toString(), 20);
		newMap.put(UUID.randomUUID().toString(), 30);
		Cache.split(uuid, newMap);
		Map map = Cache.getMap();
		System.out.println(map.toString());
		System.out.println(Cache.getlogs());
	}
}
