package com.telusko.quizservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.telusko.quizservice.model.PayloadUserForKafka;

@Service
public class KafkaProducer {
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
	
	@Value("${spring.kafka.topic.name}")
	private String topicName;

	/*@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public void sendMessage(String message) {
		logger.info("Message sent {}", message);
		kafkaTemplate.send(topicName, message);
	}*/
	
	@Autowired
	private KafkaTemplate<String, PayloadUserForKafka> kafkaTemplate;
	
	public void sendMessage(PayloadUserForKafka data) {
		logger.info("Message sent data {}", data);
		Message<PayloadUserForKafka> message = MessageBuilder.withPayload(data).setHeader(KafkaHeaders.TOPIC, topicName).build();
		logger.info("Message sent Json {}", message);
		kafkaTemplate.send(message);
	}
}
