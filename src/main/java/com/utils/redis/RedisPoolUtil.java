package com.utils.redis;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.RedisPool;
import com.pojo.MainProxy;

import redis.clients.jedis.Jedis;

public class RedisPoolUtil {

	static Logger log=LoggerFactory.getLogger(RedisPoolUtil.class);
	
	public static String set(String key,String value){
		Jedis jedis=null;
		String result=null;
		
		jedis=RedisPool.getJedis();
		
		try {
			result=jedis.set(key, value);
		} catch (Exception e) {
			log.error("set ket:{} value:{} error",key,value,e);
			RedisPool.returnBrokenResource(jedis);
			return result;
		}
		RedisPool.returnResource(jedis);
		return result;
	}
	
	//exTime的单位是秒
	public static String setEx(String key,String value,int exTime){
		Jedis jedis=null;
		String result=null;
		
		jedis=RedisPool.getJedis();
		
		try {
			result=jedis.setex(key,exTime, value);
		} catch (Exception e) {
			log.error("setex ket:{} value:{} error",key,value,e);
			RedisPool.returnBrokenResource(jedis);
			return result;
		}
		RedisPool.returnResource(jedis);
		return result;
	}
	/**
	 * 设置KEY的有效期，单位秒
	 * @param key
	 * @param exTime
	 * @return
	 */
	public static long expire(String key,int exTime){
		Jedis jedis=null;
		long result=0;
		
		jedis=RedisPool.getJedis();
		
		try {
			result=jedis.expire(key,exTime);
		} catch (Exception e) {
			log.error("setex ket:{} exTime:{} error",key,exTime,e);
			RedisPool.returnBrokenResource(jedis);
			return result;
		}
		RedisPool.returnResource(jedis);
		return result;
	}
	
	public static String get(String key){
		Jedis jedis=null;
		String result=null;
		
		jedis=RedisPool.getJedis();
		
		try {
			result=jedis.get(key);
		} catch (Exception e) {
			log.error("get ket:{} error",key,e);
			RedisPool.returnBrokenResource(jedis);
			return result;
		}
		RedisPool.returnResource(jedis);
		return result;
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	public static long del(String key){
		Jedis jedis=null;
		long result=0;
		
		jedis=RedisPool.getJedis();
		
		try {
			result=jedis.del(key);
		} catch (Exception e) {
			log.error("del ket:{} error",key,e);
			RedisPool.returnBrokenResource(jedis);
			return result;
		}
		RedisPool.returnResource(jedis);
		return result;
	}
	public static void main(String[] args) {
		RedisPoolUtil.set("ketTest", "value");
		String value=RedisPoolUtil.get("keyTest");
		
		RedisPoolUtil.setEx("keyEx", "valueex", 60*10);
		
		RedisPoolUtil.expire("keyTest", 60*20);
	     
		RedisPoolUtil.del("keyTest");
	}
}
