package com.devsuperior.dsmeta.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSumaryDTO;
import com.devsuperior.dsmeta.services.SaleService;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService salesService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = salesService.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public Page<SaleMinDTO> getSalesReport(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate initialDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@RequestParam(required = false) String sellerName,
			Pageable pageable) {
		return salesService.getSalesReport(initialDate, endDate, sellerName, pageable);
	}

	@GetMapping(value = "/sumary")
	public ResponseEntity<List<SaleSumaryDTO>> getSalesSumaryBySeller(
			@RequestParam(required = false, value = "initialDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String initialDateStr,
			@RequestParam(required = false, value = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String endDateStr) {

		LocalDate initialDate = initialDateStr != null ? LocalDate.parse(initialDateStr)
				: LocalDate.now().minusYears(1);
		LocalDate endDate = endDateStr != null ? LocalDate.parse(endDateStr) : LocalDate.now();

		List<SaleSumaryDTO> summary = salesService.getSalesSumary(initialDate, endDate);
		return ResponseEntity.ok(summary);
	}
}

