package com.my.poc.myecom.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.my.poc.myecom.model.Order;

@Repository

public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	Optional<Order> findByOrderId(String orderId);

}
