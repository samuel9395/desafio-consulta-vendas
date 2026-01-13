package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SellerMinDTO;
import com.devsuperior.dsmeta.projections.SaleProjection;
import com.devsuperior.dsmeta.projections.SellerProjection;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<SaleReportDTO> getReport(String name, LocalDate minDate, LocalDate maxDate) {

        // Converte Projection → DTO
        List<SaleProjection> list = repository.getReport(name, minDate, maxDate);
        return list.stream()
                .map(x -> new SaleReportDTO(x))  // Usa o construtor que recebe SaleProjection
                .toList();
    }

    // Sumário de vendas por vendedor
    public List<SellerMinDTO> getSummary(LocalDate minDate, LocalDate maxDate) {

        List<SellerProjection> list = repository.getSummary(minDate, maxDate);
        return list.stream()
                .map(x -> new SellerMinDTO(x))
                .toList();

    }
}
