package edu.hunter.modules.cache.hibernate.memcached.region;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.Region;

import edu.hunter.modules.cache.hibernate.memcached.MemcachedCache;

public abstract class MemcachedRegion implements Region {

	private static final String CACHE_LOCK_TIMEOUT_PROPERTY = "hibernate.memcached.cache_lock_timeout";
	private static final String CACHE_DURATION_PROPERTY = "hibernate.memcached.cache_duration_secs";
	private static final int DEFAULT_CACHE_LOCK_TIMEOUT = 1000;
	private static final int DEFAULT_CACHE_DURATION_SECS = 60;

	protected final MemcachedCache cache;

	private final int cacheLockTimeout;
	protected final int cacheDurationInSecs;

	public MemcachedRegion(MemcachedCache cache, Properties properties) {
		this.cache = cache;
		String timeout = properties.getProperty(CACHE_LOCK_TIMEOUT_PROPERTY);
		cacheLockTimeout = timeout == null ? DEFAULT_CACHE_LOCK_TIMEOUT : Integer.parseInt(timeout);
		String cacheDuration = properties.getProperty(CACHE_DURATION_PROPERTY);
		cacheDurationInSecs = cacheDuration == null ? DEFAULT_CACHE_DURATION_SECS : Integer.parseInt(cacheDuration);
	}

	@Override
	public String getName() {
		return cache.getName();
	}

	@Override
	public boolean contains(Object key) {
		return cache.exists(key.toString());
	}

	@Override
	public void destroy() throws CacheException {
		cache.destroy();
	}

	@Override
	public long getSizeInMemory() {
		return -1;
	}

	@Override
	public long getElementCountInMemory() {
		return -1;
	}

	@Override
	public long getElementCountOnDisk() {
		return -1;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Map toMap() {
		return new HashMap();
	}

	@Override
	public long nextTimestamp() {
		// return Timestamper.next();
		return 1L;
	}

	@Override
	public int getTimeout() {
		return cacheLockTimeout;
	}

	public MemcachedCache getMemcachedCache() {
		return cache;
	}
}
