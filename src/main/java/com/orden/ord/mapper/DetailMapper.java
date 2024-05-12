package com.orden.ord.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.orden.ord.dto.OrderDetailDto;
import com.orden.ord.model.OrderDetail;

@Mapper(componentModel = "spring")
public interface DetailMapper {
	
	@Mappings(value = {
			@Mapping(source = "detailId",target = "detailId"),
			@Mapping(source = "order.orderId",target = "orderId"),
			@Mapping(source = "productId",target = "productId"),
			@Mapping(source = "unitPrice",target = "unitPrice"),
			@Mapping(source = "amount",target = "amount"),
			@Mapping(source = "subtotal",target = "subtotal")
	})
	OrderDetailDto toOrderDetailDto(OrderDetail detail);
	
	@InheritInverseConfiguration
	OrderDetail toOrderDetail(OrderDetailDto detailDto);
	
	@Mappings(value = {
			@Mapping(source = "detailId",target = "detailId"),
			@Mapping(source = "order.orderId",target = "orderId"),
			@Mapping(source = "productId",target = "productId"),
			@Mapping(source = "unitPrice",target = "unitPrice"),
			@Mapping(source = "amount",target = "amount"),
			@Mapping(source = "subtotal",target = "subtotal")
	})
	List<OrderDetailDto> toOrderDetailDtoLs(List<OrderDetail> detail);
	
	@InheritInverseConfiguration
	List<OrderDetail> toOrderDetailLs(List<OrderDetailDto> detailDto);
	
}
