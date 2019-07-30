package com.medlife.deliveries.config.sqs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;


@Configuration
public class StandardSqsConfig extends AbstractSqsConfig {

  @Bean
  @Override
  public ConnectionFactory connectionFactory(SqsProperties sqsProperties) {
    return createStandardSQSConnectionFactory(sqsProperties);
  }

}
