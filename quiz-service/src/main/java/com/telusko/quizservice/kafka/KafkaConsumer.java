package com.telusko.quizservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.telusko.quizservice.model.PayloadUserForKafka;

@Service
public class KafkaConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
	
	/*@KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "myGroup")
	public void consumeMessage(String message) {
		logger.info("Message recieved {}", message);
	}*/
	
	@KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
	public void consumeMessage(PayloadUserForKafka data) {
		logger.info("Json message recieved {}", data);
		
		//save to db or any other actions based on requirement
	}
}
