package com.orden.ord.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orden.ord.config.SecurityContext;
import com.orden.ord.dto.OrderDto;
import com.orden.ord.exception.BadRequestException;
import com.orden.ord.mapper.OrderMapper;
import com.orden.ord.model.Order;
import com.orden.ord.model.OrderDetail;
import com.orden.ord.repository.OrderRepository;
import com.orden.ord.service.OrderDetailService;
import com.orden.ord.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	private Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderDetailService detailService; 
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Override
	public OrderDto addOrder(OrderDto model) {
		try {
			Order order = orderMapper.toOrder(model);
			List<OrderDetail> details = detailService.formatDetails(model.getDetails());
			order.setTotal(calculateTotal(details));
			Order savedOrder =	orderRepository.save(order);
			details.forEach(dtl -> dtl.setOrder(savedOrder));
			details = detailService.addDetail(details);
			return formatOrderDto(savedOrder,details);
		} catch (Exception e) {
			log.error("An error occurred while processing the order: " + e.getMessage());
			throw new BadRequestException("An error occurred while processing the order, Not processed...");
			
		}
	}
	
	private BigDecimal calculateTotal(List<OrderDetail> details) {
	    return details.stream()
	                  .map(OrderDetail::getSubtotal)  
	                  .reduce(BigDecimal.ZERO, BigDecimal::add); 
	}
	
	private OrderDto formatOrderDto(Order order, List<OrderDetail> deails) {
		
		OrderDto dto = orderMapper.toOrderDto(order);
		dto.setDetails(detailService.orderDetailToDto(deails));
	
		return dto;
	}
	


}
