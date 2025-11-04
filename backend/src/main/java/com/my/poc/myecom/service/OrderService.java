package com.my.poc.myecom.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.poc.myecom.model.Order;
import com.my.poc.myecom.model.OrderItem;
import com.my.poc.myecom.model.Product;
import com.my.poc.myecom.model.dto.OrderItemRequest;
import com.my.poc.myecom.model.dto.OrderItemResponse;
import com.my.poc.myecom.model.dto.OrderRequest;
import com.my.poc.myecom.model.dto.OrderResponse;
import com.my.poc.myecom.repository.OrderRepository;
import com.my.poc.myecom.repository.ProductRepository;

@Service
public class OrderService {

	@Autowired
	private ProductRepository prodRepo;
	@Autowired
	private OrderRepository orderRepo;
	
	public OrderResponse placeOrder(OrderRequest orderRequest) {
		
		Order order = new Order();
		order.setOrderId(UUID.randomUUID().toString());  // We do not want to get into duplicate issue
		order.setCustomerName(orderRequest.customerName());
		order.setEmail(orderRequest.email());;
		order.setStatus("PLACED");
		order.setOrderDate(LocalDate.now());
		
		List<OrderItem> orderItems = new ArrayList<>();
		
		for(OrderItemRequest orderItemReq : orderRequest.items()) {
			Product prod = prodRepo.findById(orderItemReq.productId())
					.orElseThrow(()-> new RuntimeException("ProductNotFound"));
			prod.setStockQuantity(prod.getStockQuantity() - orderItemReq.quantity());
			prodRepo.save(prod);
			
			OrderItem orderItem = OrderItem.builder()
					.product(prod)
					.quantity(orderItemReq.quantity())
					.totalPrice(prod.getPrice().multiply(BigDecimal.valueOf(orderItemReq.quantity()))) 
					.order(order)
					.build();
			orderItems.add(orderItem);
		}
		
		order.setOrderItems(orderItems);
		Order savedOrder = orderRepo.save(order);
		
		List<OrderItemResponse> itemResponses = new ArrayList<>();
		
		for(OrderItem item : order.getOrderItems()) {
			OrderItemResponse orderItemResponse = new OrderItemResponse(
					item.getProduct().getName(),
					item.getQuantity(),
					item.getTotalPrice());
			
			itemResponses.add(orderItemResponse);
		}
		
		OrderResponse orderResponse = new OrderResponse(
				savedOrder.getOrderId(),
				savedOrder.getCustomerName(),
				savedOrder.getEmail(),
				savedOrder.getStatus(),
				savedOrder.getOrderDate(),
				itemResponses);
				
		
		return orderResponse;
	}

	public List<OrderResponse> getAllOrderResponses() {

		List<Order> orders = orderRepo.findAll();

		List<OrderResponse> orderResps = new ArrayList<>();



		for(Order order : orders){
			List<OrderItemResponse>  orderItemResps = new ArrayList<>();

			for(OrderItem orderItem : order.getOrderItems()){
				OrderItemResponse orderItemResp = new OrderItemResponse(
								orderItem.getProduct().getName(),
								orderItem.getQuantity(),
								orderItem.getTotalPrice()
								);
				orderItemResps.add(orderItemResp);
			}

			OrderResponse orderResp = new OrderResponse(
				order.getOrderId(),
				order.getCustomerName(),
				order.getEmail(),
				order.getStatus(),
				order.getOrderDate(),
				orderItemResps
			);
			orderResps.add(orderResp);
		}

		return orderResps;
	}

}
