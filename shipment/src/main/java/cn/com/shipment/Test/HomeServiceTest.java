package cn.com.shipment.Test;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.com.shipment.cache.Cache;
import cn.com.shipment.service.inter.IHomeService;
import cn.hutool.core.lang.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HomeServiceTest {

	@Autowired
    IHomeService service;
	
	@Before
	public void addRootQuantityTest() {
		service.addRootQuantity(200d);
		Map map = Cache.getMap();
		System.out.println(map.toString());
	}
	
	@Before
	public void splitNumber() {
		service.splitNumber(UUID.randomUUID().toString(), "100,60,40");
		Map map = Cache.getMap();
		System.out.println(map.toString());
	}
	
	@Before
	public void mergeNumber() {
		Map<String, Double> map =  Cache.getMap();
		StringBuffer sb = new StringBuffer();
		int count = 1;
		for(String key : map.keySet()) {
			if(count == 2) {
				break;
			}
			sb.append(key).append(",");
			count ++ ;
		}
		String str = sb.toString();
		service.mergeNumber(str.substring(0, str.length()-1));
		
		Map<String, Double> map2 =  Cache.getMap();
		System.out.println(map2.toString());
	}
	
	@Before
	public void changeRootQuantity() {
		service.changeRootQuantity(400d);
		service.changeRootQuantity(100d);
		Map<String, Double> map2 =  Cache.getMap();
		System.out.println(map2.toString());
	}
	
	
	@After
	public void queryAllNumberInfo() {
		Map map = Cache.getMap();
		System.out.println(map.toString());
		System.out.println(Cache.getlogs().toString());
	}
}
