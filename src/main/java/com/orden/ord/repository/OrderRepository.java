package com.orden.ord.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orden.ord.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
