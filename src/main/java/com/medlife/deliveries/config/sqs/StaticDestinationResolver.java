package com.medlife.deliveries.config.sqs;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import org.springframework.jms.support.destination.DestinationResolver;


public class StaticDestinationResolver implements DestinationResolver {

  private final String queueName;

  public StaticDestinationResolver(String queueName) {
    this.queueName = queueName;
  }

  @Override
  public Destination resolveDestinationName(
      Session session, String destinationName, boolean pubSubDomain) throws JMSException {

    return session.createQueue(queueName);
  }

}
