package com.devsuperior.dsmeta.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleMinDTOProjection;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository saleRepository;

	@Transactional
	public SaleMinDTO findById(Long id) {
		return saleRepository.findById(id)
				.map(SaleMinDTO::new)
				.orElseThrow(() -> new RuntimeException("Sale not found"));
	}

	@Transactional
	public Page<SaleMinDTO> getSalesReport(LocalDate initialDate, LocalDate endDate, String sellerName,
			Pageable pageable) {
		Page<SaleMinDTOProjection> projectionPage = saleRepository.getSalesReport(initialDate, endDate, sellerName,
				pageable);

		Page<SaleMinDTO> dtoPage = projectionPage.map(projection -> new SaleMinDTO(
				projection.getSaleId(),
				projection.getAmount(),
				projection.getDate(),
				projection.getSellerName()));

		return dtoPage;
	}
}
