package com.grpcDemoApp;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.Instant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.grpcDemoApp.grpc.HelloWorldClient;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringGrpcApplicationTests {

	@Autowired
	private HelloWorldClient helloWorldClient;

	@Test
	public void testSayHello() {
		Instant start = Instant.now();

		for (int i = 0; i <= 50; i++) {
			assertThat(helloWorldClient.sayHello("Sachin", "Tendulkar")).isEqualTo("Hello Sachin Tendulkar!");
		}

		Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish).toMillis();
		System.out.println("Total time take im Millis " + timeElapsed);

	}
}
