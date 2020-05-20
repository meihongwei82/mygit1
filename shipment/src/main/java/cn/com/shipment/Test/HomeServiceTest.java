package cn.com.shipment.Test;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.shipment.cache.Cache;
import cn.com.shipment.service.inter.IHomeService;

public class HomeServiceTest {

	@Autowired
    IHomeService service;
	
	@Test
	public void addRootQuantityTest() {
		service.addRootQuantity(100d);
		Map map = Cache.getMap();
		System.out.println(map.toString());
	}
}
