package com.devsuperior.dsmeta.dto;

import java.time.LocalDate;

public interface SaleMinDTOProjection {

	Long getSaleId();

	Double getAmount();

	LocalDate getDate();

	String getSellerName();
}
