package edu.hunter.modules.persistence.mybatis.pagination;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import edu.hunter.modules.persistence.mybatis.pagination.dialect.Dialect;

@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
		RowBounds.class, ResultHandler.class }) })
public class PageableInterceptor implements Interceptor {

	private static Logger logger = LoggerFactory.getLogger(PageableInterceptor.class);

	static int MAPPED_STATEMENT_INDEX = 0;
	static int PARAMETER_INDEX = 1;
	static int ROWBOUNDS_INDEX = 2;
	static int RESULT_HANDLER_INDEX = 3;

	private Dialect dialect;

	@Override
	public Object intercept(Invocation inv) throws Throwable {

		final Object[] queryArgs = inv.getArgs();

		// 查找方法参数中的 分页请求对象
		Pageable pageRequest = this.findPageableObject(queryArgs[PARAMETER_INDEX]);

		// 如果需要分页
		if (pageRequest != null) {

			final MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
			final Object parameter = queryArgs[PARAMETER_INDEX];

			final BoundSql boundSql = ms.getBoundSql(parameter);

			final StringBuffer bufferSql = new StringBuffer(boundSql.getSql().trim());
			if (bufferSql.lastIndexOf(";") == (bufferSql.length() - 1)) {
				bufferSql.deleteCharAt(bufferSql.length() - 1);
			}

			String sql = bufferSql.toString();

			// 1. 搞定分页 记录总数
			int total = this.queryTotal(sql, ms, boundSql);

			// 2. 搞定limit 查询
			// 2.1 获取分页SQL，并完成参数准备
			String limitSql = dialect.getLimitString(sql, pageRequest.getOffset(), pageRequest.getPageSize());

			queryArgs[ROWBOUNDS_INDEX] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
			queryArgs[MAPPED_STATEMENT_INDEX] = copyFromNewSql(ms, boundSql, limitSql);

			// 2.2 继续执行剩余步骤，获取查询结果
			Object ret = inv.proceed();

			// 3. 组成分页对象
			Page<?> pi = new PageImpl<Object>((List) ret, pageRequest, total);

			// 4. MyBatis 需要返回一个List对象，这里只是满足MyBatis而作的临时包装
			List<Page<?>> tmp = new ArrayList<Page<?>>(1);
			tmp.add(pi);
			return tmp;
		}

		return inv.proceed();

	}

	/**
	 * 在方法参数中查找 分页请求对象
	 * 
	 * @param params Mapper接口方法中的参数对象
	 * @return
	 */
	private Pageable findPageableObject(Object params) {

		if (params == null) {
			return null;
		}

		if (Pageable.class.isAssignableFrom(params.getClass())) {
			return (Pageable) params;
		}

		else if (params instanceof ParamMap) {
			ParamMap<Object> paramMap = (ParamMap<Object>) params;
			for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
				Object paramValue = entry.getValue();

				if (Pageable.class.isAssignableFrom(paramValue.getClass())) {
					return (Pageable) paramValue;
				}
			}
		}

		return null;
	}

	@Override
	public Object plugin(Object target) {
		if (Executor.class.isAssignableFrom(target.getClass())) {
			return Plugin.wrap(target, this);
		}
		return target;
	}

	@Override
	public void setProperties(Properties p) {
		String dialectClass = p.getProperty("dialect");
		try {
			setDialect((Dialect) Class.forName(dialectClass).newInstance());
		} catch (Exception e) {
			throw new RuntimeException("cannot create dialect instance by dialectClass:" + dialectClass, e);
		}
	}

	/**
	 * 查询总记录数
	 * 
	 * @param sql
	 * @param mappedStatement
	 * @param boundSql
	 * @return
	 * @throws SQLException
	 */
	private int queryTotal(String sql, MappedStatement mappedStatement, BoundSql boundSql) throws SQLException {

		Connection connection = null;
		PreparedStatement countStmt = null;
		ResultSet rs = null;
		try {

			connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();

			String countSql = this.dialect.getCountString(sql);

			countStmt = connection.prepareStatement(countSql);
			BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql,
					boundSql.getParameterMappings(), boundSql.getParameterObject());

			setParameters(countStmt, mappedStatement, countBoundSql, boundSql.getParameterObject());

			rs = countStmt.executeQuery();
			int totalCount = 0;
			if (rs.next()) {
				totalCount = rs.getInt(1);
			}

			return totalCount;
		} catch (SQLException e) {
			logger.error("Error occurs when get count", e);
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("Error occurs when close result set", e);
				}
			}

			if (countStmt != null) {
				try {
					countStmt.close();
				} catch (SQLException e) {
					logger.error("Error occurs when close statement set", e);
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Error occurs when close connection", e);
				}
			}
		}

	}

	/**
	 * 对SQL参数(?)设值
	 * 
	 * @param ps
	 * @param mappedStatement
	 * @param boundSql
	 * @param parameterObject
	 * @throws SQLException
	 */
	private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
			Object parameterObject) throws SQLException {
		ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
		parameterHandler.setParameters(ps);
	}

	public Dialect getDialect() {
		return dialect;
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	private MappedStatement copyFromNewSql(MappedStatement ms, BoundSql boundSql, String sql) {
		BoundSql newBoundSql = copyFromBoundSql(ms, boundSql, sql);
		return copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
	}

	public static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;

		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		@Override
		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}

	private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(),
				boundSql.getParameterObject());
		for (ParameterMapping mapping : boundSql.getParameterMappings()) {
			String prop = mapping.getProperty();
			if (boundSql.hasAdditionalParameter(prop)) {
				newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
			}
		}
		return newBoundSql;
	}

	// see: MapperBuilderAssistant
	private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
		Builder builder = new Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());

		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		if ((ms.getKeyProperties() != null) && (ms.getKeyProperties().length != 0)) {
			StringBuffer keyProperties = new StringBuffer();
			for (String keyProperty : ms.getKeyProperties()) {
				keyProperties.append(keyProperty).append(",");
			}
			keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
			builder.keyProperty(keyProperties.toString());
		}

		// setStatementTimeout()
		builder.timeout(ms.getTimeout());

		// setStatementResultMap()
		builder.parameterMap(ms.getParameterMap());

		// setStatementResultMap()
		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());

		// setStatementCache()
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());

		return builder.build();
	}

}
