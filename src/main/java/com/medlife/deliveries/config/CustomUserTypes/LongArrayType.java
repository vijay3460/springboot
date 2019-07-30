package com.medlife.deliveries.config.CustomUserTypes;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class LongArrayType implements UserType {

    private final int[] arrayTypes = new int[] {Types.ARRAY};

    @Override
    public int[] sqlTypes() {
        return arrayTypes;
    }

    @Override
    public Class returnedClass() {
        return List.class;
    }

    @Override
    public boolean equals(Object a, Object b) throws HibernateException {
        return a==null ? b==null : a.equals(b);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o==null ? 0 : o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        if(strings!=null && strings.length>0 && resultSet!=null && resultSet.getArray(strings[0]) !=null){
            Object array = resultSet.getArray(strings[0]).getArray();
            if(array instanceof Long[]){
                return Arrays.asList((Long[]) array);
            }
        }
        return null;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object object, int index, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if(object!=null && preparedStatement!=null){
            List<Long> longList = (List<Long>) object;
            Long[] castObject = longList.toArray(new Long[longList.size()]);
            Array array = sharedSessionContractImplementor.connection().createArrayOf("bigint",castObject);
            preparedStatement.setArray(index,array);
        }
        else{
            if(preparedStatement!=null){
                preparedStatement.setNull(index,arrayTypes[0]);
            }
        }
    }

    @Override
    public Object deepCopy(Object object) throws HibernateException {
        if(object!=null){
            List<Long> list = (List<Long>) object;
            ArrayList<Long> clone = new ArrayList<>();
            for(Object objectFromOriginalList : list){
                clone.add((Long) objectFromOriginalList);
            }
            return clone;
        }
        return null;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return (Serializable) o;
    }

    @Override
    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        return serializable;
    }

    @Override
    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        return o;
    }
}
