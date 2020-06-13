package com.github.marschall.aqapi.demo;

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

import oracle.jms.AQjmsFactory;
import oracle.jms.AQjmsSession;
import oracle.jms.AdtMessage;

public class AqDemo {

  public static void main(String[] args) throws JMSException {
    QueueConnectionFactory queuConnectionFactory = AQjmsFactory.getQueueConnectionFactory(dataSource());

    try (QueueConnection connection = queuConnectionFactory.createQueueConnection();
         QueueSession session = connection.createQueueSession(true, Session.CLIENT_ACKNOWLEDGE))  {
      Queue queue = ((AQjmsSession) session).getQueue("jdbc", "forum_post_queue");
      QueueReceiver receiver = session.createReceiver(queue);
      connection.start();
      Message message = receiver.receiveNoWait();
      AdtMessage adtMessage = (AdtMessage) message;
      Object payload = adtMessage.getAdtPayload();
      session.commit();
      connection.stop();
    }
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
