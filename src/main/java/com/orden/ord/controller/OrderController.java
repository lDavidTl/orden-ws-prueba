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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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
	@Operation(summary = "Create Order")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Create order: List<ProductDto.class>", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = OrderDto.class)) }),
			@ApiResponse(responseCode = "400", description = "The order cannot be created") })
	public ResponseEntity<Map<String, Object>> getProducts(
			@RequestHeader(name = "Authorization") String Authorization, @RequestBody OrderDto orderDto) {
		try {
			return ResponseFormat.successEntity(orderService.addOrder(orderDto));
		} catch (Exception e) {
			return ResponseFormat.errorEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
