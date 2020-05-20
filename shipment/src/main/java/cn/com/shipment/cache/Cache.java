package cn.com.shipment.cache;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Cache {
	
	private static Map<String, String> logs = new HashMap<String, String>();
	private static Map<String, Double> map = new HashMap<String, Double>();
	private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private static Lock r = rwl.readLock();
	private static Lock w = rwl.writeLock();
	
	/**
	 * 通过批次获取装货量
	 * @param key
	 * @return
	 */
	public static final Double get(String key) {
		r.lock();
		try {
			return map.get(key);
		} finally {
			r.unlock();
		}
	}
	
	/**
	 * 获取所有批次及其装载量
	 * @param key
	 * @return
	 */
	public static final Map<String, Double> getMap(){
		r.lock();
		try {
			return map;
		} finally {
			r.unlock();
		}
	}
	
	/**
	 * 获取货物总量
	 * @return
	 */
	public static final Double getRootQuantity(){
		r.lock();
		try {
			double all = 0d;
			for(Double d : map.values()) {
				all += d;
			}
			return all;
		} finally {
			r.unlock();
		}
	}
	
	/**
	 * 设置某批次的装货量
	 * @param key
	 * @param value
	 */
	public static final void put(String key, Double value) {
		w.lock();
		try {
			map.put(key, value);
			addLogs(key, "add "+ key + "," + value +" metric tons");
		} finally {
			w.unlock();
		}
	}
	
	/**
	 * 拆分时移除旧批次并增加新批次
	 * @param oldKey
	 * @param newMap
	 */
	public static final boolean split(String oldKey, Map<String, Double> newMap) {
		w.lock();
		try {
			if(map.containsKey(oldKey)) {
				Double d1 = map.get(oldKey);
				Double d2 = 0d;
				for(String newKey : newMap.keySet()) {
					d2 += newMap.get(newKey);
				}
				//拆分的货物量不等于拆分前的货物量
				if(d1 != d2) {
					return false;
				}
				map.remove(oldKey);
				map.putAll(newMap);
				for(String newKey : newMap.keySet()) {
					addLogs(newKey, "split from "+ oldKey + "," + newMap.get(newKey) +" metric tons");
				}
			}else {
				return false;
			}
			return true;
		} finally {
			w.unlock();
		}
	}
	
	/**
	 * 合并时移除旧批次并增加新批次
	 * @param oldMap
	 * @param newKey
	 */
	public static final boolean merge(Map<String, Double> oldMap, String newKey) {
		w.lock();
		try {
			StringBuffer sb = new StringBuffer();
			Double d = 0d;
			for(String oldKey : oldMap.keySet()) {
				if(!map.containsKey(oldKey)) {
					return false;
				}
				sb.append(oldKey + "/"); 
				d += oldMap.get(oldKey);
			}
			map.put(newKey, d);
			for(String key : oldMap.keySet()) {
				map.remove(key);
			}
			
			String str = sb.toString();
			str = str.substring(0,str.length()-1);
			addLogs(newKey, "merge from "+ str + "," + d +" metric tons");
			return true;
		} finally {
			w.unlock();
		}
	}
	
	/**
	 * 更改总货量
	 * @param d
	 */
	public static final void changeRootQuantity(Double d) {
		w.lock();
		try {
			double all = 0d;
			for(Double v : map.values()) {
				all += v;
			}
			BigDecimal point = new BigDecimal(d).divide(new BigDecimal(all), 4, BigDecimal.ROUND_HALF_UP);
			for(String key : map.keySet()) {
				String newKey = UUID.randomUUID().toString();
				Double newVaule = new BigDecimal(map.get(key)).multiply(point).doubleValue();
				map.put(newKey, newVaule);
				map.remove(key);
				addLogs(newKey, "change root quantity from "+ key + "," + newVaule +" metric tons");
			}
		} finally {
			w.unlock();
		}
	}
	
	/**
	 * write logs
	 * @param key
	 * @param log
	 */
	public static final void addLogs(String key, String log) {
		logs.put(key, log);
	}
	
	public static final Map<String, String> getlogs(){
		return logs;
	}
	
	public static final void cleanLogs() {
		logs.clear();
	}

}
