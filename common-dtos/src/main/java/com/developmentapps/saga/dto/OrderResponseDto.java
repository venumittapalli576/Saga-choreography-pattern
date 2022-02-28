package com.developmentapps.saga.dto;

import com.developmentapps.saga.event.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

	private Integer userId;
	private Integer ProductId;
	private Integer amount;
	private Integer orderId;
	private OrderStatus orderStatus;
}
