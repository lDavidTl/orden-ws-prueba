package com.orden.ord.dto;

import java.math.BigDecimal;

public class RatingDto {
	private BigDecimal rate;
	private Long count;
	
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	
	
}
