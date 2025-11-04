package com.my.poc.myecom.model.dto;

import java.math.BigDecimal;

public record OrderItemRequest(
	int productId,
	int quantity) {

}
