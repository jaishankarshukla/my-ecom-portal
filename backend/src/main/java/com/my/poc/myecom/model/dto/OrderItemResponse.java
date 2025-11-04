package com.my.poc.myecom.model.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
		String productName,
		int quantity,
		BigDecimal totalPrice) 
{
	
}
