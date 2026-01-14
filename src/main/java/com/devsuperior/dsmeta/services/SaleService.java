package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SellerMinDTO;
import com.devsuperior.dsmeta.projections.SaleProjection;
import com.devsuperior.dsmeta.projections.SellerProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

    // Busca uma venda pelo ID e retorna um DTO simplificado
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

    // Gera o relatório de vendas
    public Page<SaleReportDTO> getReport(String name, LocalDate minDate, LocalDate maxDate, Pageable pageable) {

        Page<SaleProjection> result = repository.getReport(name, minDate, maxDate, pageable);
        return result.map(s -> new SaleReportDTO(s.getId(), s.getDate(), s.getAmount(), s.getName()));
    }

    // Sumário de vendas por vendedor
    public List<SellerMinDTO> getSummary(LocalDate minDate, LocalDate maxDate) {

        List<SellerProjection> list = repository.getSummary(minDate, maxDate);
        return list.stream()
                .map(x -> new SellerMinDTO(x))
                .toList();

    }
}

/*
*    // Converte Projection → DTO
        Page<SaleReportDTO> list = repository.getReport(name, minDate, maxDate, pageable);
        // Usa o construtor que recebe SaleProjection
        return list.map(entity -> new SaleReportDTO(entity));
        *
*/