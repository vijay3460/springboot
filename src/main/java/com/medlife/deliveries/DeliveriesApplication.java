package com.medlife.deliveries;

import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;
import com.medlife.deliveries.config.sqs.AbstractSqsConfig;
import com.medlife.deliveries.config.sqs.StandardSqsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Import;

@ComponentScan(
		basePackages = "com.medlife.deliveries",
		excludeFilters = @Filter(type = ASSIGNABLE_TYPE, value = AbstractSqsConfig.class)
)
@Import(StandardSqsConfig.class)
@Slf4j
@SpringBootApplication
public class DeliveriesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveriesApplication.class, args);
	}
	
}
