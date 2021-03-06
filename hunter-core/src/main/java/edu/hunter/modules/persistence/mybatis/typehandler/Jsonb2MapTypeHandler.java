/*
The MIT License (MIT)

Copyright (c) 2014 Manni Wood

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/
package edu.hunter.modules.persistence.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.type.Alias;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

import edu.hunter.modules.mapper.JsonMapper;

/**
 * @author Manni Wood
 */
@Alias("Jsonb2Map")
@MappedJdbcTypes(JdbcType.OTHER)
@MappedTypes(Map.class)
public class Jsonb2MapTypeHandler extends BaseTypeHandler<Map<String, Object>> {

	/* (non-Javadoc)
	 * @see org.apache.ibatis.type.BaseTypeHandler#setNonNullParameter(java.sql.PreparedStatement, int, java.lang.Object, org.apache.ibatis.type.JdbcType)
	 */
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Map<String, Object> parameter, JdbcType jdbcType)
			throws SQLException {
		PGobject jsonObject = new PGobject();
		jsonObject.setType("jsonb");
		jsonObject.setValue(JsonMapper.nonDefaultMapper().toJson(parameter));
		ps.setObject(i, jsonObject);
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.type.BaseTypeHandler#getNullableResult(java.sql.ResultSet, java.lang.String)
	 */
	@Override
	public Map<String, Object> getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return JsonMapper.nonDefaultMapper().getMapBean(rs.getString(columnName), String.class, Object.class);
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.type.BaseTypeHandler#getNullableResult(java.sql.ResultSet, int)
	 */
	@Override
	public Map<String, Object> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return JsonMapper.nonDefaultMapper().getMapBean(rs.getString(columnIndex), String.class, Object.class);
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.type.BaseTypeHandler#getNullableResult(java.sql.CallableStatement, int)
	 */
	@Override
	public Map<String, Object> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return JsonMapper.nonDefaultMapper().getMapBean(cs.getString(columnIndex), String.class, Object.class);
	}

}