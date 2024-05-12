package com.orden.ord.serviceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orden.ord.config.SecurityContext;
import com.orden.ord.dto.OrderDetailDto;
import com.orden.ord.dto.ProductDto;
import com.orden.ord.dto.ResponseFeingDto;
import com.orden.ord.exception.BadRequestException;
import com.orden.ord.feingService.FeingProductService;
import com.orden.ord.mapper.DetailMapper;
import com.orden.ord.model.OrderDetail;
import com.orden.ord.repository.OrderDetailRepository;
import com.orden.ord.service.OrderDetailService;

import feign.FeignException;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	private Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrderDetailRepository detailRepository;

	@Autowired
	private DetailMapper detailMapper;

	@Autowired
	private FeingProductService feingProductService;

	@Override
	public List<OrderDetail> addDetail(List<OrderDetail> details) {
		try {
			return detailRepository.saveAll(details);
		} catch (Exception e) {
			log.error("An error occurred while processing the detail of order: " + e.getMessage());
			throw new BadRequestException("An error occurred while processing the detail of order, Not processed...");
		}
	}

	@Override
	public List<OrderDetail> formatDetails(List<OrderDetailDto> details) {

		details.forEach(det -> {
			ProductDto product = returnModelProductoOfRequest(det.getProductId());
			BigDecimal price = product.getPrice();
			det.setUnitPrice(price);
			det.setSubtotal(calculateSubTotal(price, det.getAmount()));
		});
		return detailMapper.toOrderDetailLs(details);
	}

	private BigDecimal calculateSubTotal(BigDecimal price, BigDecimal amount) {
		return price.multiply(amount);
	}
	
	@Override
	public List<OrderDetailDto> orderDetailToDto(List<OrderDetail> details) {
		try {
			return details.stream().map(det -> { return detailMapper.toOrderDetailDto(det);}).collect(Collectors.toList());
		} catch (Exception e) {
			log.error("error when parsing the information " + e.getMessage());
			throw new BadRequestException(
					"An error occurred while processing the detail of order, Not processed(error when parsing the information)...");
		}
	}

	private ProductDto returnModelProductoOfRequest(Long idProduc) {
		try {
			ResponseFeingDto<ProductDto> productResponse = feingProductService
					.getProductById(SecurityContext.getToken(), idProduc);

			if (productResponse.getStatusCode().contentEquals("200") && productResponse.getData() != null) {
				return productResponse.getData();
			} else {
				throw new BadRequestException("Product not found!");
			}
		} catch (FeignException e) {
			log.error("An error occurred while processing the detail of order in method returnModelProductoOfRequest : "
					+ e.getMessage());
			throw new BadRequestException(
					"An error occurred while processing the detail of order, Not processed(Error in service of product)...");
		}

	}

}
