package org.onebusaway.gtfs.impl;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;
import org.onebusaway.gtfs.model.calendar.ServiceDate;

public class ServiceDateUserType implements UserType {

  private static final int[] SQL_TYPES = {Types.VARCHAR};

  @Override
  public Class<?> returnedClass() {
    return ServiceDate.class;
  }

  @Override
  public int[] sqlTypes() {
    return SQL_TYPES;
  }

  @Override
  public boolean equals(Object x, Object y) throws HibernateException {
    return x == y;
  }

  @Override
  public int hashCode(Object x) throws HibernateException {
    return x.hashCode();
  }

  @Override
  public boolean isMutable() {
    return false;
  }

  @Override
  public Object deepCopy(Object value) throws HibernateException {
    return new ServiceDate((ServiceDate) value);
  }

  @Override
  public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
      throws HibernateException, SQLException {

    String value = rs.getString(names[0]);

    if (rs.wasNull())
      return null;

    return ServiceDate.parseString(value);
  }

  @Override
  public void nullSafeSet(PreparedStatement st, Object value, int index)
      throws HibernateException, SQLException {

    if (value == null) {
      st.setNull(index, SQL_TYPES[0]);
    } else {
      ServiceDate serviceDate = (ServiceDate) value;
      st.setString(index, serviceDate.getAsString());
    }
  }

  @Override
  public Object assemble(Serializable cached, Object owner)
      throws HibernateException {
    return deepCopy(cached);
  }

  @Override
  public Serializable disassemble(Object value) throws HibernateException {
    return (Serializable) deepCopy(value);
  }

  @Override
  public Object replace(Object original, Object target, Object owner)
      throws HibernateException {
    if (original == null)
      return null;
    return deepCopy(original);
  }
}
