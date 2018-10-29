package com.common;

import com.utils.PropertiesUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
	private static JedisPool pool;//jedis连接池
	private static Integer maxTotal=Integer.parseInt(PropertiesUtil.getProperty("redis.max.total", "20"));//最大连接数
	private static Integer maxIdle=Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle", "10"));//在jedispool中最大的idle状态的jedis实例的个数
	private static Integer minIdle=Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle", "2"));//在jedispool中最小的idle状态的jedis实例的个数
	private static Boolean testOnBorrow=Boolean.parseBoolean((PropertiesUtil.getProperty("redis.test.borrow", "true")));
	//在return一个jedis实例的时候，是否要进行验证操作，
	private static Boolean testOnReturn=Boolean.parseBoolean((PropertiesUtil.getProperty("redis.test.borrow", "true")));
	//从redis连接池放回连接时，校验并返回可用的连接

	private static String redisIp=PropertiesUtil.getProperty("redis.ip");
	private static Integer port=Integer.parseInt(PropertiesUtil.getProperty("redis.port"));//在jedispool中最小的idle状态的jedis实例的个数

	private static void initPool(){
		JedisPoolConfig config=new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMinIdle(minIdle);
		config.setTestOnBorrow(testOnBorrow);
		config.setTestOnReturn(testOnReturn);

		config.setBlockWhenExhausted(true);//连接耗尽时是否阻塞，false会抛出异常，true,阻塞到超时，超时异常
		pool=new JedisPool(config,redisIp,port,1000*2);
	}

	static{
		initPool();
	}

	public static Jedis getJedis(){
		return pool.getResource();
	}

	public static void returnBrokenResource(Jedis jedis){
		pool.returnBrokenResource(jedis);
	}

	public static void returnResource(Jedis jedis){
		pool.returnResource(jedis);
	}

	
	public static void main(String[] args) {
		Jedis jedis=RedisPool.getJedis();
		System.out.println("======");
		jedis.set("getkey","helloword");
		returnBrokenResource(jedis);
		
		pool.destroy();
	}

}
