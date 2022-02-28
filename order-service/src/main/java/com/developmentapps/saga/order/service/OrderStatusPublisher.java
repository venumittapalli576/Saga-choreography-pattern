package com.developmentapps.saga.order.service;

import org.springframework.stereotype.Service;

import com.developmentapps.saga.dto.OrderRequestDto;
import com.developmentapps.saga.event.OrderEvent;
import com.developmentapps.saga.event.OrderStatus;

import reactor.core.publisher.Sinks;

@Service
public class OrderStatusPublisher {

	private Sinks.Many<OrderEvent> orderSinks;
	
	public void publishOrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus) {
		OrderEvent orderEvent = new OrderEvent(orderRequestDto,orderStatus);
		orderSinks.tryEmitNext(orderEvent);
	}
}
