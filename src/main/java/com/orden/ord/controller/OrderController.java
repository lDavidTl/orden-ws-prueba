package com.orden.ord.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orden.ord.dto.OrderDto;
import com.orden.ord.service.OrderService;
import com.orden.ord.util.ResponseFormat;

@RestController
@RequestMapping("/")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> main() {
		System.out.println("test");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("createOrder")
	public ResponseEntity<Map<String, Object>> getProducts(
			@RequestHeader(name = "Authorization") String Authorization, @RequestBody OrderDto orderDto) {
		try {
			return ResponseFormat.successEntity(orderService.addOrder(orderDto));
		} catch (Exception e) {
			return ResponseFormat.errorEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
