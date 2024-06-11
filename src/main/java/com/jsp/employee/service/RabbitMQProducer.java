package com.jsp.employee.service;

import com.jsp.employee.dto.EmployeeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing-key.key}")
    private String routingKey;

    @Value("${rabbitmq.routing-key.json.key}")
    private String jsonRoutingKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String message) {
        try {
            log.info(String.format("Message sent -> %s", message));
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
        } catch (Exception e) {
            log.info(String.valueOf(e));
        }
    }

    public void sendJsonMessage(EmployeeDto orders) {
        try {
            log.info(String.format("JsonMessage sent -> %s", orders.toString()));
            rabbitTemplate.convertAndSend(exchange, jsonRoutingKey, orders);
        } catch (Exception e) {
            log.info(String.valueOf(e));
        }
    }
}