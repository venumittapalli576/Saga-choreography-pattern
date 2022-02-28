package com.developmentapps.saga.event;

import java.util.Date;
import java.util.UUID;

import com.developmentapps.saga.dto.OrderRequestDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrderEvent implements Event {

	private UUID eventId = UUID.randomUUID();
	private Date date = new Date();
	private OrderRequestDto orderRequestDto;
	private OrderStatus orderStatus;

	@Override
	public UUID getEventId() {
		// TODO Auto-generated method stub
		return eventId;
	}

	@Override
	public Date getDate() {
		// TODO Auto-generated method stub
		return date;
	}

	public OrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus) {
		super();
		this.orderRequestDto = orderRequestDto;
		this.orderStatus = orderStatus;
	}
}
