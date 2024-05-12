package com.orden.ord.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.orden.ord.dto.OrderDto;
import com.orden.ord.model.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {
	
	@Mappings(value = {
			@Mapping(source = "orderId",target = "orderId"),
			@Mapping(source = "dateOrder",target = "dateOrder"),
			@Mapping(source = "status",target = "status"),
			@Mapping(source = "total",target = "total")
	})
	OrderDto toOrderDto(Order order);
	
	@InheritInverseConfiguration
	Order toOrder(OrderDto orderDto);
}
