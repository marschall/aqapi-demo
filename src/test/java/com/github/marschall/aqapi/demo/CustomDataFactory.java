package com.github.marschall.aqapi.demo;

import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.LinkedHashMap;
import java.util.Map;

import oracle.jdbc.OracleStruct;
import oracle.jdbc.OracleTypeMetaData;
import oracle.sql.Datum;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;

final class CustomDataFactory implements ORADataFactory {

  public CustomDataFactory() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public ORAData create(Datum datum, int sqlType) throws SQLException {
    if (datum instanceof Struct) {
      Struct struct = (Struct) datum;
      String sqlTypeName = struct.getSQLTypeName();
      Object[] attributes = struct.getAttributes();
      Map<String, Class<?>> typeMap = new LinkedHashMap<>();
      typeMap.put("title", String.class);
      typeMap.put("text", String.class);
      typeMap.put("id", Long.class); // BigDecimal
      Object[] orderedAttributes = struct.getAttributes(typeMap);
      System.out.println(sqlTypeName);
    }

    if (datum instanceof OracleStruct) {
      OracleStruct oracleStruct = (OracleStruct) datum;
      OracleTypeMetaData oracleMetaData = oracleStruct.getOracleMetaData();
      if (oracleMetaData instanceof oracle.jdbc.OracleTypeMetaData.Struct) {
        oracle.jdbc.OracleTypeMetaData.Struct structMetadata = (oracle.jdbc.OracleTypeMetaData.Struct) oracleMetaData;
        String[] subtypeNames = structMetadata.getSubtypeNames();
        System.out.println(subtypeNames);
        ResultSetMetaData metaData = structMetadata.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
          System.out.println(metaData.getColumnName(i + 1));
        }
      }
    }
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
