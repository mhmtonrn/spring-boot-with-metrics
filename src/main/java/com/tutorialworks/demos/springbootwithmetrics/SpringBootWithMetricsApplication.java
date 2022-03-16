package com.tutorialworks.demos.springbootwithmetrics;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class SpringBootWithMetricsApplication implements CommandLineRunner {

	private final MeterRegistry registry;

	public SpringBootWithMetricsApplication(MeterRegistry registry) {
		this.registry = registry;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWithMetricsApplication.class, args);
	}

	@Bean
	public TimedAspect timedAspect(MeterRegistry registry) {
		return new TimedAspect(registry);
	}

	@Override
	public void run(String... args) throws Exception {
		for (int i = 0; i < 10000; i++) {
			System.out.println(i);

			Counter counter = Counter.builder("custom.metric")
					.tag("date", LocalDateTime.now().toString())
					.tag("counter", i+"")
					.description("You can use custom tags")
					.register(registry);
			counter.increment();
		}
	}
}
