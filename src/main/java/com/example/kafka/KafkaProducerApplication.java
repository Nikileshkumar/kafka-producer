package com.example.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.function.Supplier;

@SpringBootApplication
@EnableScheduling
public class KafkaProducerApplication {

	@Autowired
	StreamBridge streamBridge;

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApplication.class, args);
	}

	//method invoked by framework by default interval of 1 sec
	@Bean
	public Supplier<String> supplier() {
		return () -> " jack from Streams";
	}

	@Scheduled(cron = "*/2 * * * * *")
	public void sendMessage() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValueAsBytes("hello");
		streamBridge.send("supplier-out-0","from StreamBridge");
	}
}
