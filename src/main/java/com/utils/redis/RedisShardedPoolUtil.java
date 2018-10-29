package com.utils.redis;


import com.common.RedisShardedPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.RedisPool;
import com.pojo.MainProxy;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

public class RedisShardedPoolUtil {

	static Logger log=LoggerFactory.getLogger(RedisShardedPoolUtil.class);
	
	public static String set(String key,String value){
		ShardedJedis jedis=null;
		String result=null;
		
		jedis= RedisShardedPool.getJedis();
		
		try {
			result=jedis.set(key, value);
		} catch (Exception e) {
			log.error("set ket:{} value:{} error",key,value,e);
			RedisShardedPool.returnBrokenResource(jedis);
			return result;
		}
		RedisShardedPool.returnResource(jedis);
		return result;
	}
	
	//exTime的单位是秒
	public static String setEx(String key,String value,int exTime){
		ShardedJedis jedis=null;
		String result=null;
		
		jedis=RedisShardedPool.getJedis();
		
		try {
			result=jedis.setex(key,exTime, value);
		} catch (Exception e) {
			log.error("setex ket:{} value:{} error",key,value,e);
			RedisShardedPool.returnBrokenResource(jedis);
			return result;
		}
		RedisShardedPool.returnResource(jedis);
		return result;
	}
	/**
	 * 设置KEY的有效期，单位秒
	 * @param key
	 * @param exTime
	 * @return
	 */
	public static long expire(String key,int exTime){
		ShardedJedis jedis=null;
		long result=0;
		
		jedis=RedisShardedPool.getJedis();
		
		try {
			result=jedis.expire(key,exTime);
		} catch (Exception e) {
			log.error("setex ket:{} exTime:{} error",key,exTime,e);
			RedisShardedPool.returnBrokenResource(jedis);
			return result;
		}
		RedisShardedPool.returnResource(jedis);
		return result;
	}
	
	public static String get(String key){
		ShardedJedis jedis=null;
		String result=null;
		
		jedis=RedisShardedPool.getJedis();
		
		try {
			result=jedis.get(key);
		} catch (Exception e) {
			log.error("get ket:{} error",key,e);
			RedisShardedPool.returnBrokenResource(jedis);
			return result;
		}
		RedisShardedPool.returnResource(jedis);
		return result;
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	public static long del(String key){
		ShardedJedis jedis=null;
		long result=0;
		
		jedis=RedisShardedPool.getJedis();
		
		try {
			result=jedis.del(key);
		} catch (Exception e) {
			log.error("del ket:{} error",key,e);
			RedisShardedPool.returnBrokenResource(jedis);
			return result;
		}
		RedisShardedPool.returnResource(jedis);
		return result;
	}
	public static void main(String[] args) {
		RedisShardedPoolUtil.set("ketTest", "value");
		String value=RedisShardedPoolUtil.get("keyTest");
		
		RedisShardedPoolUtil.setEx("keyEx", "valueex", 60*10);
		
		RedisShardedPoolUtil.expire("keyTest", 60*20);
	     
		RedisShardedPoolUtil.del("keyTest");
	}
}
