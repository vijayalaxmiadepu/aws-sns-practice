package com.aws.sns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
        org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration.class,
        org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration.class,
        org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration.class
})
public class SpringAwsSnsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAwsSnsApplication.class, args);
	}

}
