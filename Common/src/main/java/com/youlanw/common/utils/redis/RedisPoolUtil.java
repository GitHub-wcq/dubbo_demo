package com.youlanw.common.utils.redis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/*
 * 
 * <p>Title: RedisUtil</p>  
 * Description: <pre>Redis 操作工具类</pre>  只能操作非集群redis
 * @author Sean.Wang 
 * @date 2018-04-26
 * @
 */
public class RedisPoolUtil {
	static JedisPool pool;
	static JedisPoolConfig poolConfig;
	static JedisCluster cluster;
	static final int connectionTimeOut = 3000;
	static RedisPack pack;

	// 开发环境
	static String connectionPassword = "ylredis";
	static String connectionAddress = "10.0.15.225";
	static int connectionPort = 6379;
	static int connectionDb = 0; // 注意：redis 数据库索引从0开始

	// region 构造函数重载

	/*
	 * 构造函数
	 */
	public RedisPoolUtil() {
		CreateJedisPool();
	}

	/*
	 * 构造函数传入配置
	 */
	public RedisPoolUtil(JedisPoolConfig config) {
		poolConfig = config;
		CreateJedisPool();
	}

	/*
	 * 构造函数传入配置和连接密码
	 */
	public RedisPoolUtil(JedisPoolConfig config, String password) {
		connectionPassword = password;
		poolConfig = config;
		CreateJedisPool();
	}

	/*
	 * 构造函数传入配置、连接密码、服务器地址、端口、连接的数据库编号
	 */
	public RedisPoolUtil(JedisPoolConfig config, String password, String addr, int port, int dbIndex) {
		connectionPassword = password;
		poolConfig = config;
		connectionAddress = addr;
		connectionPort = port;
		connectionDb = dbIndex;

		CreateJedisPool();
	}

	// end

	/*
	 * 创建连接池
	 */
	static synchronized void CreateJedisPool() {
		if (pool != null) {
			pack = new RedisPack(pool);
			return;
		}

		// 添加默认配置
		if (poolConfig == null) {
			poolConfig = new JedisPoolConfig();

			// 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
			poolConfig.setBlockWhenExhausted(true);
			// 最大连接数, 默认8个
			poolConfig.setMaxTotal(100);
			// 最大空闲连接数, 默认8个
			poolConfig.setMaxIdle(8);
			// 最小空闲连接数, 默认0
			poolConfig.setMinIdle(0);
			// 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
			poolConfig.setMinEvictableIdleTimeMillis(1800000 / 6);

			// (如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间, 默认-1
			// 等待可用连接的最大时间,单位毫秒,默认值为-1,表示永不超时/如果超过等待时间,则直接抛出异常
			poolConfig.setMaxWaitMillis(-1);
			// 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
			poolConfig.setNumTestsPerEvictionRun(3);

			// 对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断
			// (默认逐出策略)
			poolConfig.setSoftMinEvictableIdleTimeMillis(1800000);

			// 在获取连接的时候检查有效性, 默认false
			poolConfig.setTestOnBorrow(false);
			// 在空闲时检查有效性, 默认false
			poolConfig.setTestWhileIdle(false);

			// 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
			poolConfig.setTimeBetweenEvictionRunsMillis(-1);

		}

		pool = new JedisPool(poolConfig, connectionAddress, connectionPort, connectionTimeOut, connectionPassword,
				connectionDb);

		pack = new RedisPack(pool);
	}

	// region 内部类，包装 Redis操作，避免忘记释放连接池资源
	interface RedisCallback<T> {
		public T handle(Jedis jedis);
	}

	/*
	 * 包装Redis操作，避免忘记归还连接池资源
	 */
	static class RedisPack {

		JedisPool jedisPool;

		public RedisPack(JedisPool jedisPool) {
			this.jedisPool = jedisPool;
		}

		public <T> T execute(RedisCallback<T> callback) {
			Jedis jedis = jedisPool.getResource();

			try {
				return callback.handle(jedis);
			} catch (Exception e) {
				// throw your exception
				throw e;
			} finally {
				returnResource(jedis);
			}
		}

		void returnResource(Jedis jedis) {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	// end

	// region 设置过期时间

	/*
	 * 设置 key 的过期时间。key 过期后将不再可用。
	 * 
	 * @param key: 待操作的键值
	 * 
	 * @param seconds: 从当前开始多少秒后过期
	 * 
	 * @return Boolean: 设置成功返回 1 。 当 key 不存在或者不能为 key 设置过期时间时返回0
	 */
	public Boolean Expire(String key, int seconds) {
		return pack.execute(new RedisCallback<Boolean>() {
			public Boolean handle(Jedis jedis) {
				return jedis.expire(key, seconds) > 0;
			}
		});
	}

	/*
	 * 以 UNIX 时间戳(unix timestamp)格式设置 key 的过期时间。key 过期后将不再可用。
	 * 
	 * @param key: 待操作的键值
	 * 
	 * @param date: 从当前开始到某个时间过期
	 * 
	 * @return Boolean: 设置成功返回 1 。 当 key 不存在或者不能为 key 设置过期时间时返回0
	 */
	public Boolean ExpireAt(String key, Date date) {
		return pack.execute(new RedisCallback<Boolean>() {
			public Boolean handle(Jedis jedis) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String t = sdf.format(date);
				Long epoch;
				try {
					epoch = sdf.parse(t).getTime() / 1000;
					return jedis.expireAt(key, epoch) > 0;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			}
		});
	}
	// end

	// region Item 操作

	/*
	 * 根据key查询value
	 * 
	 * @param key: 待查询的键值
	 * 
	 * @return String
	 */
	public String ItemGet(String key) {
		return pack.execute(new RedisCallback<String>() {
			public String handle(Jedis jedis) {
				return jedis.get(key);
			}
		});
	}

	/*
	 * 设置 key-value
	 * 
	 * @param key: 待操作的键值
	 * 
	 * @param value: 待设置的值
	 * 
	 * @return Boolean: 成功返回 true
	 */
	public Boolean ItemSet(String key, String value) {
		return pack.execute(new RedisCallback<Boolean>() {
			public Boolean handle(Jedis jedis) {
				String result = jedis.set(key, value);
				return result != null && result.equals("OK");
			}
		});
	}

	/*
	 * 为指定的 key 设置值及其过期时间。如果 key 已经存在， SETEX 命令将会替换旧的值。
	 * 
	 * @param key: 待操作的键值
	 * 
	 * @param value: 从当前开始多少秒过期
	 * 
	 * @param value: 待设置的值
	 * 
	 * @return Boolean: 成功返回 true
	 */
	public Boolean ItemSetEx(String key, int seconds, String value) {
		return pack.execute(new RedisCallback<Boolean>() {
			public Boolean handle(Jedis jedis) {
				String result = jedis.setex(key, seconds, value);
				return result != null && result.equals("OK");
			}
		});
	}

	/*
	 * 给指定key的value尾部追加字符串。如果key不存在，则会自动创建
	 * 
	 * @param key: 待操作的键值
	 * 
	 * @param value: 待追加的值
	 * 
	 * @return Boolean: 成功返回 true
	 */
	public Boolean ItemAppendAutoCreate(String key, String value) {
		return pack.execute(new RedisCallback<Boolean>() {
			public Boolean handle(Jedis jedis) {
				// append 成功返回结果字符串的长度
				Long result = jedis.append(key, value);
				return result != null && result > 0;
			}
		});
	}

	/*
	 * 给指定key的value尾部追加字符串。如果key不存在，返回失败
	 * 
	 * @param key: 待操作的键值
	 * 
	 * @param value: 待追加的值
	 * 
	 * @return Boolean: 成功返回 true
	 */
	public Boolean ItemAppend(String key, String value) {
		return pack.execute(new RedisCallback<Boolean>() {
			public Boolean handle(Jedis jedis) {
				if (!jedis.exists(key))
					return false;

				// append 成功返回结果字符串的长度
				Long result = jedis.append(key, value);
				return result != null && result > 0;
			}
		});
	}

	/*
	 * 删除一个或多个key
	 * 
	 * @param keys: 待删除的键值数组
	 * 
	 * @return Long: 返回成功被删除的键的数量
	 */
	public Long ItemDelete(String[] keys) {
		return pack.execute(new RedisCallback<Long>() {
			public Long handle(Jedis jedis) {
				return jedis.del(keys);
			}
		});
	}

	public Boolean ItemMSet(String... map) {
		return pack.execute(new RedisCallback<Boolean>() {
			public Boolean handle(Jedis jedis) {
				return jedis.mset(map).equals("OK");
			}
		});
	}

	// end

}
