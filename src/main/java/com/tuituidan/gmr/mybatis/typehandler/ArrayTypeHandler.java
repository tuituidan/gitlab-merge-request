package com.tuituidan.gmr.mybatis.typehandler;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * ArrayTypeHandler.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/21
 */
public class ArrayTypeHandler extends BaseTypeHandler<Object> {

    private static final Map<String, String> TYPE_MAP = new HashMap<>();

    static {
        TYPE_MAP.put("java.lang.String[]", "varchar");
        TYPE_MAP.put("java.lang.Integer[]", "integer");
        TYPE_MAP.put("java.lang.Boolean[]", "boolean");
        TYPE_MAP.put("java.lang.Number[]", "numeric");
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType)
            throws SQLException {
        Connection conn = ps.getConnection();
        Array array = conn.createArrayOf(TYPE_MAP.get(parameter.getClass().getTypeName()),
                (Object[]) parameter);
        ps.setArray(i, array);

    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getArray(rs.getArray(columnName));
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getArray(rs.getArray(columnIndex));
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getArray(cs.getArray(columnIndex));
    }

    private Object getArray(Array array) throws SQLException {
        if (array == null) {
            return null;
        }
        Object result = array.getArray();
        array.free();
        return result;
    }
}
