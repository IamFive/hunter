package edu.hunter.modules.test.redis;

import java.security.SecureRandom;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.google.common.collect.Maps;

import edu.hunter.modules.mapper.JsonMapper;
import edu.hunter.modules.nosql.redis.JedisTemplate;
import edu.hunter.modules.nosql.redis.JedisTemplate.JedisActionNoResult;
import edu.hunter.modules.nosql.redis.JedisUtils;
import edu.hunter.modules.test.spring.benchmark.BenchmarkTask;
import edu.hunter.modules.test.spring.benchmark.ConcurrentBenchmark;

/**
 * 测试Redis用于Session管理的setEx()与get()方法性能, 使用JSON格式存储数据.
 * 
 * 可用系统参数重置相关变量，@see RedisCounterBenchmark
 * 
 * @author calvin
 */
public class RedisSessionBenchmark extends ConcurrentBenchmark {
	private static final int DEFAULT_THREAD_COUNT = 50;
	private static final long DEFAULT_LOOP_COUNT = 20000;

	private String keyPrefix = "ss.session:";
	private JsonMapper jsonMapper = new JsonMapper();
	private JedisPool pool;
	private JedisTemplate jedisTemplate;

	public static void main(String[] args) throws Exception {
		RedisSessionBenchmark benchmark = new RedisSessionBenchmark();
		benchmark.execute();
	}

	public RedisSessionBenchmark() {
		super(DEFAULT_THREAD_COUNT, DEFAULT_LOOP_COUNT);
	}

	@Override
	protected void setUp() {
		pool = JedisUtils.createJedisPool("192.168.105.198", JedisUtils.DEFAULT_PORT, JedisUtils.DEFAULT_TIMEOUT, threadCount);
		jedisTemplate = new JedisTemplate(pool);
		jedisTemplate.flushDB();
	}

	@Override
	protected void tearDown() {
		pool.destroy();
	}

	@Override
	protected BenchmarkTask createTask() {
		return new SessionTask();
	}

	public static class Session {

		private String id;

		private Map<String, Object> attributes = Maps.newHashMap();

		public Session() {
		}

		public Session(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Map<String, Object> getAttributes() {
			return attributes;
		}

		public void setAttributes(Map<String, Object> attributes) {
			this.attributes = attributes;
		}

		public Session setAttrbute(String key, Object value) {
			attributes.put(key, value);
			return this;
		}

		public Session removeAttrbute(String key) {
			attributes.remove(key);
			return this;
		}
	}

	public class SessionTask extends BenchmarkTask {
		private SecureRandom random = new SecureRandom();

		@Override
		protected void execute(final int requestSequnce) {

			int randomIndex = random.nextInt((int) loopCount);
			final String key = new StringBuilder().append(keyPrefix).append(taskSequence).append(":").append(randomIndex).toString();

			jedisTemplate.execute(new JedisActionNoResult() {
				@Override
				public void action(Jedis jedis) {
					Session session = new Session(key);
					session.setAttrbute("name", key);
					session.setAttrbute("seq", requestSequnce);
					session.setAttrbute("address", "address:" + requestSequnce);
					session.setAttrbute("tel", "tel:" + requestSequnce);

					// 设置session，超时时间为300秒
					jedis.setex(session.getId(), 300, jsonMapper.toJson(session));

					// ByteArrayOutputStream bos = new ByteArrayOutputStream();
					// ObjectOutputStream oos = null;
					// try {
					// oos = new ObjectOutputStream(new
					// BufferedOutputStream(bos));
					// oos.writeObject(session);
					// oos.flush();
					// } catch (IOException e) {
					//
					// } finally {
					// IOUtils.closeQuietly(oos);
					// }
					//
					// byte[] byteArray = bos.toByteArray();
					// jedis.setex(key.getBytes(), 300, byteArray);
					//
					// // 再重新从Redis中取出并反序列化
					String sessionBackString = jedis.get(session.getId());
					Session sessionBack = jsonMapper.getBean(sessionBackString, Session.class);
				}
			});
		}
	}
}
