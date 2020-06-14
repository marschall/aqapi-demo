package com.github.marschall.aqapi.demo;

import java.sql.SQLException;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.sql.DataSource;

import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import oracle.jdbc.pool.OracleDataSource;
import oracle.jms.AQjmsFactory;
import oracle.jms.AQjmsSession;
import oracle.jms.AdtMessage;

public class AqDemo {

  private static final String QUEUE_NAME = "Q_FORUM_POST";
  private static final String QUEUE_OWNER = "jdbc";

  public static void main(String[] args) throws JMSException, SQLException {
    QueueConnectionFactory queuConnectionFactory = AQjmsFactory.getQueueConnectionFactory(oracleDataSource());

    try (QueueConnection connection = queuConnectionFactory.createQueueConnection();
         QueueSession session = connection.createQueueSession(true, Session.CLIENT_ACKNOWLEDGE))  {
      Queue queue = ((AQjmsSession) session).getQueue(QUEUE_OWNER, QUEUE_NAME);
      QueueReceiver receiver = ((AQjmsSession) session).createReceiver(queue, new CustomDataFactory());
      connection.start();
      Message message = receiver.receiveNoWait();
      AdtMessage adtMessage = (AdtMessage) message;
      Object payload = adtMessage.getAdtPayload();
      session.commit();
      connection.stop();
    }
  }

  private static DataSource oracleDataSource() throws SQLException {
    oracle.jdbc.OracleDriver.isDebug();
    OracleDataSource dataSource = new OracleDataSource();
//    dataSource.setSuppressClose(true);
    dataSource.setURL("jdbc:oracle:thin:@localhost:1521/ORCLPDB1");
    dataSource.setUser("jdbc");
    dataSource.setPassword("Cent-Quick-Space-Bath-8");
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("oracle.net.disableOob", "true");
    dataSource.setConnectionProperties(connectionProperties);
    return dataSource;
  }

  private static DataSource dataSource() {
    oracle.jdbc.OracleDriver.isDebug();
    SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
    dataSource.setSuppressClose(true);
    dataSource.setUrl("jdbc:oracle:thin:@localhost:1521/ORCLPDB1");
    dataSource.setUsername("jdbc");
    dataSource.setPassword("Cent-Quick-Space-Bath-8");
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("oracle.net.disableOob", "true");
    dataSource.setConnectionProperties(connectionProperties);
    return dataSource;
  }

}
