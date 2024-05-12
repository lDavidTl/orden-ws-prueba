package com.orden.ord.feingService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.orden.ord.config.FeignClientConfig;
import com.orden.ord.dto.ProductDto;
import com.orden.ord.dto.ResponseFeingDto;

@FeignClient(name = "productService", url = "${order.url.service.product}", configuration = FeignClientConfig.class)

public interface FeingProductService {
	@GetMapping("getProduct/{id}")
	 public ResponseFeingDto<ProductDto> getProductById(@RequestHeader("Authorization") String token,  @PathVariable Long id);
}
	