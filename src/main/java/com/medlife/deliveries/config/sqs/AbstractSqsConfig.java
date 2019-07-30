package com.medlife.deliveries.config.sqs;


import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.*;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.util.StringUtils;

import javax.jms.ConnectionFactory;
import javax.jms.Session;


@Configuration
@EnableConfigurationProperties(SqsProperties.class)
@EnableJms
public abstract class AbstractSqsConfig {

    @Bean
    public abstract ConnectionFactory connectionFactory(SqsProperties sqsProperties);

    @Bean
    public DestinationResolver destinationResolver(SqsProperties sqsProperties) {
        return new StaticDestinationResolver(sqsProperties.getQueueName());
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(
            ConnectionFactory connectionFactory, DestinationResolver destinationResolver) {

        DefaultJmsListenerContainerFactory jmsListenerContainerFactory =
                new DefaultJmsListenerContainerFactory();
        jmsListenerContainerFactory.setConnectionFactory(connectionFactory);
        jmsListenerContainerFactory.setDestinationResolver(destinationResolver);
        jmsListenerContainerFactory.setConcurrency("3-10");
        jmsListenerContainerFactory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        jmsListenerContainerFactory.setSessionTransacted(false);

        return jmsListenerContainerFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(
            SqsProperties sqsProperties, ConnectionFactory connectionFactory) {

        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setDefaultDestinationName(sqsProperties.getQueueName());
        return jmsTemplate;
    }

    protected SQSConnectionFactory createStandardSQSConnectionFactory(SqsProperties sqsProperties) {
        AmazonSQS sqsClient = createAmazonSQSClient(sqsProperties);

        ProviderConfiguration providerConfiguration = new ProviderConfiguration();
        sqsProperties.getNumberOfMessagesToPrefetch()
                .ifPresent(providerConfiguration::setNumberOfMessagesToPrefetch);

        return new SQSConnectionFactory(providerConfiguration, sqsClient);
    }

    private AmazonSQS createAmazonSQSClient(SqsProperties sqsProperties) {
        Regions region = Regions.fromName(sqsProperties.getRegion());

        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(
                sqsProperties.getEndpoint(), region.getName());

        AWSCredentialsProvider awsCredentialsProvider = createAwsCredentialsProvider(
                sqsProperties.getAccessKey(),
                sqsProperties.getSecretKey()
        );

        return AmazonSQSClientBuilder
                .standard()
                .withCredentials(awsCredentialsProvider)
                .withEndpointConfiguration(endpointConfiguration)
                .build();
    }

    private AWSCredentialsProvider createAwsCredentialsProvider(
            String localAccessKey, String localSecretKey) {

        AWSCredentialsProvider ec2ContainerCredentialsProvider =
                new EC2ContainerCredentialsProviderWrapper();

        if (StringUtils.isEmpty(localAccessKey) || StringUtils.isEmpty(localSecretKey)) {
            return ec2ContainerCredentialsProvider;
        }

        AWSCredentialsProvider localAwsCredentialsProvider =
                new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(localAccessKey, localSecretKey));

        return new AWSCredentialsProviderChain(
                localAwsCredentialsProvider, ec2ContainerCredentialsProvider);
    }

}