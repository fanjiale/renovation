/**
 * 
 */
package org.arrow.common.redis;

import org.testng.annotations.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 * @author root
 */
public class RedisTest {
	private static JedisPool masterPool = null;
	private static JedisPool slavePool = null;  
	
    private static void initPool() {  
        try{    
        	masterPool = new JedisPool("192.168.74.15", 6379); 
        	slavePool = new JedisPool("192.168.74.16", 6379);
        } catch(Exception e) {  
            e.printStackTrace();  
        }  
    }  
	
  //  @Test
	public void test() {
		initPool();
		Jedis masterRedis = masterPool.getResource();
		masterRedis.auth("redis123");
		Jedis slaveRedis = slavePool.getResource();
//		slaveRedis.auth("redis123");
		masterRedis.set("hhh", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		masterRedis.set("hhh", "aaa");
		System.out.println(masterRedis.get("hhh"));
		System.out.println(slaveRedis.get("hhh"));
		
		Student student = new Student();
		student.setId("1111");
		student.setName("hHHH");
	}
	
}
