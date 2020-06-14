package com.github.marschall.aqapi.demo;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.sql.Datum;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;

final class CustomDataFactory implements ORADataFactory {

  public CustomDataFactory() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public ORAData create(Datum datum, int sqlType) throws SQLException {
    return new ORADataImpl(datum);
  }

  static final class ORADataImpl implements ORAData {

    private final Datum datum;

    ORADataImpl(Datum datum) {
      this.datum = datum;
    }

    @Override
    public Datum toDatum(Connection connection) throws SQLException {
      return this.datum;
    }

  }

}
