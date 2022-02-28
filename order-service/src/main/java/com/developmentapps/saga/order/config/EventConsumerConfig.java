package com.developmentapps.saga.order.config;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.developmentapps.saga.event.PaymentEvent;

@Configuration
public class EventConsumerConfig {

	@Autowired
	OrderStatusUpdateHandler orderStatusUpdateHandler;
	
	@Bean
	public Consumer<PaymentEvent> paymentEventConsumer(){
		return (payment) -> orderStatusUpdateHandler.updateOrder(payment.getPaymentRequestDto().getOrderId(), po -> {
			po.setPaymentStatus(payment.getPaymentStatus());
		});
	}
}