package edu.hunter.modules.persistence.mybatis.pagination.dialect;

public class OracleDialect extends Dialect {

	@Override
	public boolean supportsLimit() {
		return true;
	}

	@Override
	public boolean supportsLimitOffset() {
		return true;
	}

	@Override
	public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
		String original = sql.trim();
		boolean isForUpdate = false;
		if (original.toLowerCase().endsWith(" for update")) {
			original = original.substring(0, original.length() - 11);
			isForUpdate = true;
		}

		StringBuffer pagingSelect = new StringBuffer(original.length() + 100);
		if (offset > 0) {
			pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		} else {
			pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(original);
		if (offset > 0) {
			// int end = offset+limit;
			String endString = offsetPlaceholder + "+" + limitPlaceholder;
			pagingSelect.append(" ) row_ ) where rownum_ <= " + endString + " and rownum_ > " + offsetPlaceholder);
		} else {
			pagingSelect.append(" ) where rownum <= " + limitPlaceholder);
		}

		if (isForUpdate) {
			pagingSelect.append(" for update");
		}

		return pagingSelect.toString();
	}

}
