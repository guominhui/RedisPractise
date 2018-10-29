package com.common;

import java.util.ArrayList;
import java.util.List;

import com.utils.PropertiesUtil;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

public class RedisShardedPool {
	private static ShardedJedisPool pool;//jedis连接池
	private static Integer maxTotal=Integer.parseInt(PropertiesUtil.getProperty("redis.max.total", "20"));//最大连接数
	private static Integer maxIdle=Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle", "10"));//在jedispool中最大的idle状态的jedis实例的个数
	private static Integer minIdle=Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle", "2"));//在jedispool中最小的idle状态的jedis实例的个数
	private static Boolean testOnBorrow=Boolean.parseBoolean((PropertiesUtil.getProperty("redis.test.borrow", "true")));
	//在return一个jedis实例的时候，是否要进行验证操作，
	private static Boolean testOnReturn=Boolean.parseBoolean((PropertiesUtil.getProperty("redis.test.borrow", "true")));
	//从redis连接池放回连接时，校验并返回可用的连接

	private static String redis1Ip=PropertiesUtil.getProperty("redis1.ip");
	private static Integer port1=Integer.parseInt(PropertiesUtil.getProperty("redis1.port"));//在jedispool中最小的idle状态的jedis实例的个数

	private static String redis2Ip=PropertiesUtil.getProperty("redis2.ip");
	private static Integer port2=Integer.parseInt(PropertiesUtil.getProperty("redis2.port"));//在jedispool中最小的idle状态的jedis实例的个数


	private static void initPool(){
		JedisPoolConfig config=new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMinIdle(minIdle);
		config.setTestOnBorrow(testOnBorrow);
		config.setTestOnReturn(testOnReturn);

		config.setBlockWhenExhausted(true);//连接耗尽时是否阻塞，false会抛出异常，true,阻塞到超时，超时异常

		JedisShardInfo jedisShardInfo1=new JedisShardInfo(redis1Ip, port1,1000*2);
		//jedisShardInfo1.setPassword(auth);
		JedisShardInfo jedisShardInfo2=new JedisShardInfo(redis2Ip, port2,1000*2);
		List<JedisShardInfo> jedisShardedInfoList=new ArrayList<JedisShardInfo>();
		jedisShardedInfoList.add(jedisShardInfo1);
		jedisShardedInfoList.add(jedisShardInfo2);
		pool=new ShardedJedisPool(config, jedisShardedInfoList,Hashing.MURMUR_HASH,Sharded.DEFAULT_KEY_TAG_PATTERN);

	}

	static{
		initPool();
	}

	public static ShardedJedis getJedis(){
		return pool.getResource();
	}

	public static void returnBrokenResource(ShardedJedis jedis){
		pool.returnBrokenResource(jedis);
	}

	public static void returnResource(ShardedJedis jedis){
		pool.returnResource(jedis);
	}


	public static void main(String[] args) {
		
		ShardedJedis jedis=RedisShardedPool.getJedis();
		for (int i = 0; i < 10; i++) {
			jedis.set("key"+i, "value"+i);
		}
		System.out.println("======");
		jedis.set("getkey","helloword");
		returnBrokenResource(jedis);

		pool.destroy();
	}



}
