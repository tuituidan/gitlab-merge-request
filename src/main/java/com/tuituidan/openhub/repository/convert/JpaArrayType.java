package com.tuituidan.openhub.repository.convert;

import java.io.Serializable;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

import org.apache.commons.lang3.SerializationUtils;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

/**
 * JpaArrayType.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/12/26
 */
public class JpaArrayType implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.ARRAY};
    }

    @Override
    public Class returnedClass() {
        return String[].class;
    }

    @Override
    public boolean equals(Object o1, Object o2) {
        return Objects.equals(o1, o2);
    }

    @Override
    public int hashCode(Object o) {
        return Objects.hashCode(o);
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SharedSessionContractImplementor sci, Object o)
            throws SQLException {
        Array array = resultSet.getArray(strings[0]);
        if (array == null) {
            return null;
        }
        Object result = array.getArray();
        array.free();
        return result;
    }

    @Override
    public void nullSafeSet(PreparedStatement ps, Object parameter, int i, SharedSessionContractImplementor sci)
            throws SQLException {
        if (parameter == null) {
            ps.setArray(i, null);
            return;
        }
        Connection conn = ps.getConnection();
        Array array = conn.createArrayOf("varchar", (Object[]) parameter);
        ps.setArray(i, array);
    }

    @Override
    public Object deepCopy(Object o) {
        return SerializationUtils.clone((Serializable)o);
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object o) {
        return (Serializable) deepCopy(o);
    }

    @Override
    public Object assemble(Serializable serializable, Object o) {
        return deepCopy(o);
    }

    @Override
    public Object replace(Object o, Object o1, Object o2) {
        return o;
    }
}
