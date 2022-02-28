package com.developmentapps.saga.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

	private Integer userId;
	private Integer ProductId;
	private Integer amount;
	private Integer orderId;	
	
}
