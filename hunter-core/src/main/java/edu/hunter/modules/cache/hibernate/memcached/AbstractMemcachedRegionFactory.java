package edu.hunter.modules.cache.hibernate.memcached;

import java.util.Properties;

import net.sf.ehcache.util.Timestamper;
import net.spy.memcached.MemcachedClient;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.CollectionRegion;
import org.hibernate.cache.spi.EntityRegion;
import org.hibernate.cache.spi.QueryResultsRegion;
import org.hibernate.cache.spi.RegionFactory;
import org.hibernate.cache.spi.TimestampsRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cfg.Settings;

import edu.hunter.modules.cache.hibernate.memcached.region.MemcachedCollectionRegion;
import edu.hunter.modules.cache.hibernate.memcached.region.MemcachedEntityRegion;
import edu.hunter.modules.cache.hibernate.memcached.region.MemcachedQueryResultRegion;
import edu.hunter.modules.cache.hibernate.memcached.region.MemcachedTimestampsRegion;
import edu.hunter.modules.cache.hibernate.memcached.spymemcached.MemcachedCacheImpl;

abstract class AbstractMemcachedRegionFactory implements RegionFactory {

	private static final long serialVersionUID = 7064032381768312504L;

	protected MemcachedClient client;
	protected Settings settings;
	protected Properties properties;

	@Override
	public boolean isMinimalPutsEnabledByDefault() {
		return true;
	}

	@Override
	public AccessType getDefaultAccessType() {
		return AccessType.READ_WRITE;
	}

	@Override
	public long nextTimestamp() {
		return Timestamper.next();
	}

	@Override
	public EntityRegion buildEntityRegion(String regionName, Properties properties, CacheDataDescription metadata)
			throws CacheException {
		return new MemcachedEntityRegion(getCache(regionName), settings, metadata, properties);
	}

	@Override
	public CollectionRegion buildCollectionRegion(String regionName, Properties properties,
			CacheDataDescription metadata) throws CacheException {
		return new MemcachedCollectionRegion(getCache(regionName), settings, metadata, properties);
	}

	@Override
	public QueryResultsRegion buildQueryResultsRegion(String regionName, Properties properties) throws CacheException {
		return new MemcachedQueryResultRegion(getCache(regionName), properties);
	}

	@Override
	public TimestampsRegion buildTimestampsRegion(String regionName, Properties properties) throws CacheException {
		return new MemcachedTimestampsRegion(getCache(regionName), properties);
	}

	private MemcachedCache getCache(String regionName) {
		return new MemcachedCacheImpl(regionName, client);
	}

}
