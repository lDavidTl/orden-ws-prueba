package com.orden.ord.service;

import java.util.List;

import com.orden.ord.dto.OrderDetailDto;
import com.orden.ord.model.OrderDetail;

public interface OrderDetailService {
	public List<OrderDetail> addDetail(List<OrderDetail> details);
	public List<OrderDetail> formatDetails(List<OrderDetailDto> details);
	public List<OrderDetailDto> orderDetailToDto(List<OrderDetail> details);
}
