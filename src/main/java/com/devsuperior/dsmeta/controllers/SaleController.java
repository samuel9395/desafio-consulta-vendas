package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SellerMinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;

    // Endpoint para buscar venda por ID
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

    // Endpoint do relatório
    @GetMapping(value = "/report")
    public ResponseEntity<Page<SaleReportDTO>> getReport(
            @RequestParam(name = "name", defaultValue = "") String name,
            @RequestParam(name = "minDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate minDate,
            @RequestParam(name = "maxDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate maxDate,
            Pageable pageable
    ) {

        // Se a data final não for informada, usa a data atual
        if (maxDate == null) {
            maxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
        }

        // Se a data inicial não for informada, usa 1 ano antes da data final
        if (minDate == null) {
            minDate = maxDate.minusYears(1L);
        }

        Page<SaleReportDTO> dto = service.getReport(name, minDate, maxDate, pageable);
        return ResponseEntity.ok(dto);
    }

    // Endpoint de sumário de vendas por vendedor
    @GetMapping(value = "/summary")
    public ResponseEntity<List<SellerMinDTO>> getSummary(
            @RequestParam(name = "minDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate minDate,
            @RequestParam(name = "maxDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate maxDate) {

        if (maxDate == null) {
            maxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
        }

        if (minDate == null) {
            minDate = maxDate.minusYears(1L);
        }

        List<SellerMinDTO> dto = service.getSummary(minDate, maxDate);
        return ResponseEntity.ok(dto);
    }
}
